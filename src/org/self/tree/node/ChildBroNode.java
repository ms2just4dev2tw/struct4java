package org.self.tree.node;

/**
 * 孩子兄弟链存储结构
 * <p>
 * 这种结构的最大优点是将树转换成二叉树，同孩子链一样查询父节点比较麻烦
 * 
 * @author TungWang
 */
public class ChildBroNode<K, V> implements Node<K, V> {

	private K key;
	private V value;
	// 指向孩子
	private ChildBroNode<K, V> child;
	// 指向兄弟
	private ChildBroNode<K, V> brother;

	public ChildBroNode() {
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

	public ChildBroNode<K, V> getChild() {
		return child;
	}

	public void setChild(ChildBroNode<K, V> child) {
		this.child = child;
	}

	public ChildBroNode<K, V> getBrother() {
		return brother;
	}

	public void setBrother(ChildBroNode<K, V> brother) {
		this.brother = brother;
	}

}
