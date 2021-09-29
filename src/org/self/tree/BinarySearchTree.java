package org.self.tree;

import java.util.Objects;

import org.self.tree.node.BinaryNode;
import org.self.tree.node.Node;

/**
 * 二叉搜索树(BST)
 * <p>
 * 左节点小于父节点，右节点大于父节点
 * <p>
 * 优点是查找效率跟高度成线性比，缺点是插入或删除会影响高度
 * 
 * @author TungWang
 */
public class BinarySearchTree<K extends Comparable<K>, V> extends BinaryTree<K, V> {

	private BinaryNode<K, V> top;

	@Override
	public BinaryNode<K, V> search(Node<K, V> top, K key) {
		Objects.requireNonNull(top);
		BinaryNode<K, V> next = (BinaryNode<K, V>) top, tmp = null;
		for (; next != null; next = key.compareTo(next.getKey()) < 0 ? next.getLeft() : next.getRight())
			if (key == next.getKey())
				return next;
			else
				tmp = next;
		return tmp; // 如果最后找不到节点，就返回最接近key的节点
	}

	@Override
	public boolean put(K key, V value) {
		if (top == null)
			top = new BinaryNode<>(key, value);
		else {
			BinaryNode<K, V> tmp = search(top, key);
			// 插入节点
			if (tmp.getKey() == key)
				return false;
			else if (key.compareTo(tmp.getKey()) < 0)
				tmp.setLeft(new BinaryNode<>(key, value));
			else
				tmp.setRight(new BinaryNode<>(key, value));
		}
		return true;
	}

	@Override
	public boolean delete(K key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BinaryNode<K, V> min(Node<K, V> top) {
		Objects.requireNonNull(top);
		for (BinaryNode<K, V> node = (BinaryNode<K, V>) top;; node = node.getLeft())
			if (node.getLeft() == null)
				return node;
	}

	@Override
	public BinaryNode<K, V> max(Node<K, V> top) {
		Objects.requireNonNull(top);
		for (BinaryNode<K, V> node = (BinaryNode<K, V>) top;; node = node.getRight())
			if (node.getRight() == null)
				return node;
	}

	@Override
	public BinaryNode<K, V> successor(BinaryNode<K, V> node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BinaryNode<K, V> predecessor(BinaryNode<K, V> node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void display() {
		throw new UnsupportedOperationException();
	}

}
