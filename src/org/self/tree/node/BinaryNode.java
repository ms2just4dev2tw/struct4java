package org.self.tree.node;

/**
 * 二叉树的节点
 * 
 * @author TungWang
 */
public class BinaryNode<K, V> implements Node<K, V> {

	private K key;
	private V value;
	// 左子节点
	private BinaryNode<K, V> left;
	// 右子节点
	private BinaryNode<K, V> right;

	public BinaryNode() {
	}

	public BinaryNode(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public BinaryNode(BinaryNode<K, V> node) {
		this.key = node.getKey();
		this.value = node.getValue();
		this.left = node.getLeft();
		this.right = node.getRight();
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

	public BinaryNode<K, V> getLeft() {
		return left;
	}

	public void setLeft(BinaryNode<K, V> left) {
		this.left = left;
	}

	public BinaryNode<K, V> getRight() {
		return right;
	}

	public void setRight(BinaryNode<K, V> right) {
		this.right = right;
	}

}
