package org.self.tree.node;

/**
 * 孩子链存储结构
 * <p>
 * 查询孩子节点比较容易，查询父节点要遍历整棵树
 * 
 * @author TungWang
 */
public class ChildrenNode<K, V> implements Node<K, V> {

	private K key;
	private V value;
	// 指向一个孩子链表
	private ChildrenNode<K, V> sons[];

	public ChildrenNode() {
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public ChildrenNode<K, V>[] getSons() {
		return sons;
	}

	public void setSons(ChildrenNode<K, V> sons[]) {
		this.sons = sons;
	}

}
