package org.self.tree.node;

import org.self.list.OrderlyArrayList;

/**
 * 
 * BalanceMultiwayNode 平衡多叉树(B 树) 的节点
 * 
 * @author TungWang
 *
 * @param <K> 关键字
 * @param <V> 关键字代表的数据
 */
public class BMNode<K extends Comparable<K>, V> implements Node<K, V>, Comparable<BMNode<K, V>> {

	/**
	 * 将关键字和关键字包含的数据组合成一个实体
	 * <p>
	 * 实体间的比较依赖于关键字的比较
	 * 
	 * @author TungWang
	 */
	private class Entry implements Comparable<Entry> {
		private K key;
		private V value;

		// 关键字是必须的元素，但数据可以不存在
		private Entry(K key) {
			this(key, null);
		}

		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(Entry e) {
			return this.key.compareTo(e.key);
		}

		@Override
		public String toString() {
			return "[K=" + key + ", V=" + value + "]";
		}
	}

	// B-树子节点的最大值，也是B-树的阶
	private int rank;
	// 父节点
	private BMNode<K, V> parent;
	// 用访问优先队列管理关键字数组
	private OrderlyArrayList<Entry> entryList;
	// 用访问优先队列管理孩子节点数组
	private OrderlyArrayList<BMNode<K, V>> childList;

	public BMNode(int rank) {
		this.rank = rank;
		// 多开辟一个缓冲空间
		entryList = new OrderlyArrayList<>(rank);
		childList = new OrderlyArrayList<>(rank + 1);
	}

	public void insert(K key, V value) {
		insert(new Entry(key, value));
	}

	/**
	*  添加关键字
	*  <p>
	*  如果当前节点的关键字个数等于 rank-1，就需要分裂
	*  
	* @param key
	*/
	private void insert(Entry entry) {
		for (BMNode<K, V> node = this; node != null;)
			if (hasPlace4PutEntry(node)) {
				node.addEntry(entry);
				return;
			} else {
				// 先插入，再节点分裂
				node.addEntry(entry);
				// entry是node的中间关键字
				entry = split(node);
				// 将 node 节点指向父节点
				node = node.parent;
			}
	}

	/**
	 * @return 当前节点是否还有实体放置的空间
	 */
	private boolean hasPlace4PutEntry(BMNode<K, V> node) {
		if (node.size4Entry() < rank - 1)
			return true;
		else if (node.size4Entry() == rank - 1)
			return false;
		else
			throw new IllegalStateException("the place is noway arrived");
	}

	// 添加关键字实体
	private void addEntry(Entry entry) {
		entryList.add(entry);
	}

	/**
	* 节点分裂的前提条件，关键字个数=rank，子节点个数=rank
	* <p>
	* 1，关键字，[0，......，mid)，(mid，......，rank] <p>
	* 2，子节点，[0，......，mid}，[mid，......，rank] <p>
	* 3，中间关键字 mid 放在双亲节点中
	* <p>
	*   
	* @param node 将要分裂的节点
	* @return 返回节点的中间关键字
	*/
	private Entry split(BMNode<K, V> node) {
		BMNode<K, V> parent = node.getParent();
		BMNode<K, V> newNode = new BMNode<>(rank);
		// 当前节点 rank/2 以右部分加入新节点
		int index = node.size4Entry() - 1;
		if (node.isLeaf())
			for (int mid = rank / 2; index > mid; index--)
				newNode.addEntry(node.removeEntry(index));
		else {
			for (int mid = rank / 2; index > mid; index--) {
				newNode.addEntry(node.removeEntry(index));
				newNode.addChild(node.removeChild(index + 1));
			}
			// 由于新节点还需要添加一个子节点
			newNode.addChild(node.removeChild(index + 1));
		}
		// 为新节点添加父节点
		newNode.addParent(parent, node);
		// 返回当前节点的中间关键字
		return node.removeEntry(rank / 2);
	}

	private void addParent(BMNode<K, V> parent, BMNode<K, V> brother) {
		// 如果当前节点没有双亲节点
		if (parent == null) {
			parent = new BMNode<>(rank);
			parent.addChild(this);
			parent.addChild(brother);
		} else
			parent.addChild(this);
	}

	// 添加子节点
	private void addChild(BMNode<K, V> child) {
		childList.add(child);
		child.setParent(this);
	}

	/**
	* 在当前节点内，newKey 替换 oldKey
	* 
	* @param oldKey
	* @param newKey
	*/
	public void replace(K oldKey, BMNode<K, V> replace, int index) {
		Entry oldEntry = getEntry(oldKey);
		Entry newEntry = replace.getEntry(index);
		entryList.reset(oldEntry, newEntry);
	}

	/**
	* 删除关键字
	* <p>
	* 在内部节点的删除操作转换成叶子节点的删除操作
	* <p>
	* 1，如果节点当前关键字个数大于等于 (min=rank/2) ，直接删除 <p>
	* 2，否则从当前节点的左右兄弟节点找出满足条件1的节点，从兄弟节点借一个关键字 <p>
	* 3，左右兄弟节点皆不满足条件1，就选择一个兄弟节点合并
	* <p>
	* 
	* @param index
	*/
	public void delete(int index) {
		// 实体的删除一定是从叶子节点开始
		BMNode<K, V> node = this, parent = node.getParent();
		for (; parent != null; node = parent, parent = node.getParent()) {
			// 先直接删除，在进行节点的判断条件时要 + 1
			node.removeEntry(index);
			// 当前节点不大于树的一半阶，在尝试从兄弟节点借关键字失败后，节点合并
			int index4Node = parent.indexOf(node);
			if (node.size4Entry() + 1 <= rank / 2 && !borrowFromBrother(parent, index4Node))
				index = merge(parent, index4Node);
			else
				return;
		}
		// 节点合并到了根节点，删除在 index 的实体
		node.removeEntry(index);
	}

	// 移除关键字实体
	private Entry removeEntry(int index) {
		return this.entryList.remove(index);
	}

	/**
	* 如果 index 是指向双亲节点的最后一个子节点
	* <p>
	* 从 index-1 处的兄弟节点拿一个关键字实体
	* <p>
	* 否则从 index+1 处的兄弟节点拿一个关键字实体
	* 
	* @param parent 当前节点的双亲节点
	* @param index 当前节点在双亲节点的索引
	* @return 从当前节点的兄弟节点拿一个关键字实体
	*/
	private boolean borrowFromBrother(BMNode<K, V> parent, int index) {
		BMNode<K, V> node = parent.getChild(index), brother = null;
		// 如果 index 指向 parent 的最后一个子节点
		if (index == parent.size4Entry()) {
			brother = parent.getChild(index - 1);
			// 如果兄弟节点有多余的关键字实体
			if (brother.size4Entry() > rank / 2) {
				node.addEntry(parent.removeEntry(index - 1));
				parent.addEntry(brother.entryList.removeTail());
				return true;
			}
		} else {
			brother = parent.getChild(index + 1);
			// 如果兄弟节点有多余的关键字实体
			if (brother.size4Entry() > rank / 2) {
				node.addEntry(parent.removeEntry(index));
				parent.addEntry(brother.entryList.removeHead());
				return true;
			}
		}
		return false;
	}

	// 合并节点 node 下的子节点 node[index] 和 node[index+1]
	private int merge(BMNode<K, V> parent, int index) {
		BMNode<K, V> left, right;
		// 根据 index 选择左右节点，并删除一个右节点
		if (index == parent.size4Entry()) {
			left = parent.getChild(index - 1);
			right = parent.removeChild(index);
		} else {
			left = parent.getChild(index);
			right = parent.removeChild(index + 1);
		}
		// 将节点 index 处的关键字加入左节点
		left.addEntry(parent.getEntry(index));
		// 将右节点的关键字加入到左节点
		for (int i = 0; i < right.size4Entry(); i++)
			left.addEntry(right.getEntry(i));
		// 如果右节点是分支节点，将右节点的子节点加入到左节点
		if (right.isBranch())
			for (int times = 0; times < right.size4Child();)
				left.addChild(right.removeChild(0));
		// 如果是最后一个子节点返回 index-1，否则返回 index
		return index == parent.size4Entry() ? index - 1 : index;
	}

	// 移除子节点
	public BMNode<K, V> removeChild(int index) {
		BMNode<K, V> node = this.childList.remove(index);
		node.setParent(null);
		return node;
	}

	/**
	* @param index
	* @return  根据 index 获取关键字
	*/
	public K getKey(int index) {
		return getEntry(index).key;
	}

	public V getValue(int index) {
		return getEntry(index).value;
	}

	public V getValue(K key) {
		return getValue(indexOf(key));
	}

	private Entry getEntry(int index) {
		return entryList.valueOf(index);
	}

	private Entry getEntry(K key) {
		return entryList.valueOf(indexOf(key));
	}

	/**
	* @param index 
	* @return 根据 index 获得孩子节点
	*/
	public BMNode<K, V> getChild(int index) {
		if (index < 0 || index >= size4Child())
			return null;
		return childList.valueOf(index);
	}

	/**
	* 找出 key 位于节点的索引
	* 
	* @param key
	* @return 
	*/
	public int indexOf(K key) {
		return entryList.indexOf(new Entry(key));
	}

	/**
	* 找出子节点 node 在当前节点的索引
	* 
	* @param node
	* @return
	*/
	public int indexOf(BMNode<K, V> node) {
		return childList.indexOf(node);
	}

	// 是否是分支节点
	public boolean isBranch() {
		return !isLeaf();
	}

	// 是否是叶子节点
	public boolean isLeaf() {
		return size4Child() == 0;
	}

	// 当前节点的关键字个数
	public int size4Entry() {
		return entryList.size();
	}

	// 当前节点的子节点个数
	public int size4Child() {
		return childList.size();
	}

	public BMNode<K, V> getParent() {
		return parent;
	}

	// 控制 setParent 的访问权限
	private void setParent(BMNode<K, V> parent) {
		this.parent = parent;
	}

	/**
	* 这个方法是用于排序
	*/
	@Override
	public int compareTo(BMNode<K, V> node2) {
		K maxKey4node1 = this.getKey(this.size4Entry() - 1);
		K maxKey4node2 = node2.getKey(node2.size4Entry() - 1);
		return maxKey4node1.compareTo(maxKey4node2);
	}

}
