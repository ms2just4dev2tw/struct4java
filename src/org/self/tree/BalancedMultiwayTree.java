package org.self.tree;

import java.util.Objects;

import org.self.tree.node.BMNode;
import org.self.tree.node.Node;

/**
 * 平衡多叉树，B树，也称为B-树
 * <p>
 * 1，所有子节点在同一层，并且不带有信息
 * <p>
 * 2，树中至多有m棵子树，即至多有m-1个关键字
 * <p>
 * 3，根节点或者没有子树，或者至少有两棵子树
 * <p>
 * 4，除了根节点外，其他每个分支节点至少有m/2棵子树，即至少有m/2 - 1个关键字
 * <p>
 * 5，非叶子节点
 * 
 * @author TungWang
 * @param <K>
 * @param <V>
 */
public class BalancedMultiwayTree<K extends Comparable<K>, V> implements Tree<K, V> {

	// B树的阶，代表最多有 rank 棵子树，最多有 rank-1 个关键字
	private int rank;
	// 根节点
	private BMNode<K, V> top;

	public BalancedMultiwayTree(int rank) {
		this.rank = rank;
	}

	@Override
	public BMNode<K, V> search(Node<K, V> top, K key) {
		Objects.requireNonNull(top);
		//
		BMNode<K, V> next = (BMNode<K, V>) top, tmp = null;
		// 用折半查找的方法来搜索关键字区间
		int low = 0, high = next.size4Child() - 1, mid = (low + high) / 2;
		for (; next != null && next.isBranch(); mid = (low + high) / 2) {
			int sign = inSection(key, next, mid);
			if (sign == -1 || sign == 1) { // 如果关键字在边界上，就返回当前节点
				return next;
			} else if (sign == -2) { // 关键字在mid区间的左边的区间
				high = mid - 1;
			} else if (sign == 2) { // 关键字在mid区间的右边的区间
				low = mid + 1;
			} else if (sign == 0) { // 关键字在mid区间内
				tmp = next;
				next = tmp.getChild(mid);
				low = 0;
				high = next.size4Child() - 1;
			}
		}
		// 如果最后找不到节点，就返回最接近key的叶子节点
		return next != null ? next : tmp;
	}

	/**
	 * 判断关键字是否在node节点的第sectionIndex区间内
	 * 
	 * @param key 关键字
	 * @param node 当前节点
	 * @param sectionIndex 区间索引
	 * @return -2:小于整个区间，-1:等于左边界，0:在区间内，1:等于右边界，2:大于整个区间
	 */
	private int inSection(K key, BMNode<K, V> node, int sectionIndex) {
		if (sectionIndex == 0) { // 如果是第一个区间
			int rightSign = key.compareTo(node.getKey(0));
			if (rightSign < 0)
				return 0;
			else if (rightSign == 0)
				return 1;
			else
				return 2;
		} else if (sectionIndex == node.size4Entry()) { // 如果是最后一个区间
			int leftSign = key.compareTo(node.getKey(sectionIndex - 1));
			if (leftSign < 0)
				return -2;
			else if (leftSign == 0)
				return -1;
			else
				return 0;
		} else {
			int leftSign = key.compareTo(node.getKey(sectionIndex - 1));
			int rightSign = key.compareTo(node.getKey(sectionIndex));
			if (leftSign < 0)
				return -2;
			else if (leftSign == 0)
				return -1;
			else {
				if (rightSign < 0)
					return 0;
				else if (rightSign == 0)
					return 1;
				else
					return 2;
			}
		}
	}

	/**
	 * 1，利用{@link BalancedMultiwayTree#search(Node, int) 查找方法} 找到该关键字的插入位置
	 * <p>
	 * 插入节点一定是最低非叶子层
	 * <p>
	 * 2.1，若 n < m-1，说明当前节点还有位置，直接插入就行
	 * <p>
	 * 2.2，若 n = m-1，说明当前节点没有多余的位置，需要进行分裂操作	<p>
	 * 		   	把当前节点的关键字和要插入的key按照升序排序，从中间位置（m/2）处分裂	<p>
	 * 			[low,m/2)保持旧节点不动，(m/2,high]放入一个新的节点中
	 * <p>
	 * 2.3，对于把m/2处的关键字插入父节点再进行判断
	 */
	@Override
	public boolean put(K key, V value) {
		if (top == null)
			top = new BMNode<K, V>(rank);
		// 根节点是分支节点或者是叶子节点有不同的处理方式
		if (top.isBranch()) {
			// 根据 key 找到要插入的节点
			BMNode<K, V> node = search(top, key);
			node.insert(key, value);
		} else
			top.insert(key, value);
		// 由于会发生节点分裂，所以对于这种情况要重设根节点
		resetRootForPut();
		return true;
	}

	private void resetRootForPut() {
		if (top.getParent() != null)
			top = top.getParent();
	}

	public V getValue(K key) {
		// 找到 key 所在的叶子节点
		BMNode<K, V> node = search(top, key);
		return node.getValue(key);
	}

	/**
	 * 1，利用{@link BalancedMultiwayTree#search(Node, int) 查找方法} 找到该关键字所在节点
	 * <p>
	 * 2，在节点上删除关键字分为两种：1，在最低非叶子层删除；2，在其他非叶子层删除	<p>
	 * 		 如果是其他非叶子层，我们需要用该关键字下的最小关键字或最大关键字来替换		<p>
	 * 		 我们就把其他叶子层的删除转换成最低叶子层的删除操作
	 * <p>
	 * 3.1，假如被删节点的关键字个数大于 m/2，直接删除
	 * <p>
	 * 3.2，假如被删节点的关键字个数等于 m/2，若该节点的(左或右)兄弟节点有关键字大于 m/2	<p>
	 * 			则把该节点的(左兄弟节点的最大关键字或右兄弟节点的最小关键字)上移到父节点中		<p>
	 * 			同时把双亲节点中(大于或小于)上移关键字的关键字下移到要删除关键字的节点中
	 * <p>
	 * 3.2，假如被删节点的关键字个数等于 m/2，该节点的(左或右)兄弟节点关键字个数均等于 m/2	<p>
	 * 			把删除关键字的节点与其(左或右兄弟节点)以及双亲节点中分割二者的关键字合并成一个	<p>
	 * 			如果这使得双亲节点的关键字会小于 m/2，则进行同样的操作
	 * 
	 * @param key 要被删除的关键字
	 * @return 
	 */
	@Override
	public boolean delete(K key) {
		Objects.requireNonNull(top);
		// 根据 key 找到要删除的节点位置
		BMNode<K, V> node = search(top, key);
		int index = node.indexOf(key);
		//
		if (node.isBranch()) {
			int index4Replace = -1;
			// index 的左右两边的子节点
			BMNode<K, V> left = node.getChild(index), right = node.getChild(index + 1);
			// 根据 index 左右两边的 size 大小选择替换节点
			BMNode<K, V> replace = null;
			if (left.size4Entry() >= right.size4Entry()) {
				replace = max(left);
				index4Replace = replace.size4Entry() - 1;
			} else {
				replace = min(right);
				index4Replace = 0;
			}
			// 用 replace 在 index4Replace 处的实体替换 node 的实体
			node.replace(key, replace, index4Replace);
			// 删除 replace 在 index4Replace 处的实体
			replace.delete(index4Replace);
		} else
			node.delete(index);
		// 由于会发生节点合并，所以对于这种情况要重设根节点
		resetRootForDelete();
		return true;
	}

	/**
	 * 如果根节点没有关键字，根节点重设为它最后一个子节点
	 */
	private void resetRootForDelete() {
		if (top.size4Entry() == 0)
			top = top.removeChild(0);
	}

	@Override
	public BMNode<K, V> min(Node<K, V> top) {
		Objects.requireNonNull(top);
		// 一直左下
		BMNode<K, V> next = (BMNode<K, V>) top, tmp = null;
		for (; next != null; next = tmp.getChild(0))
			tmp = next;
		return tmp;
	}

	@Override
	public BMNode<K, V> max(Node<K, V> top) {
		Objects.requireNonNull(top);
		// 一直右下
		BMNode<K, V> next = (BMNode<K, V>) top, tmp = null;
		for (; next != null; next = tmp.getChild(tmp.size4Entry()))
			tmp = next;
		return tmp;
	}

	@Override
	public void display() {
		TreeDisplay.display(top, 0);
		System.out.println();
	}

}
