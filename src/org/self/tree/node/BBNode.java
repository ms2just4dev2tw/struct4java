package org.self.tree.node;

/**
 * BinaryBalanceNode 平衡二叉树的节点
 * 
 * @author TungWang
 */
public class BBNode<K extends Comparable<K>, V> extends BinaryNode<K, V> implements Comparable<BBNode<K, V>> {

	// 当前节点的高度
	private int height;
	// 当前节点的平衡因子
	private int balance;
	// 当前节点的父节点
	private BBNode<K, V> parent;

	public BBNode() {
	}

	public BBNode(K key, V value) {
		super(key, value);
		// 新节点的高度默认为1
		this.height = 1;
		// 新节点的平衡因子默认为0
		this.balance = 0;
	}

	public BBNode(BBNode<K, V> node) {
		super(node);
		this.parent = node.getParent();
		this.height = node.getHeight();
		this.balance = node.getBalance();
	}

	public void setBalanceAndHeighr() {
		int leftHeight = getLeft() == null ? 0 : getLeft().getHeight();
		int rightHeight = getRight() == null ? 0 : getRight().getHeight();
		// 设置node的平衡因子
		setBalance(leftHeight - rightHeight);
		// 设置node的高度
		int maxHeight = leftHeight > rightHeight ? leftHeight : rightHeight;
		setHeight(maxHeight + 1);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	// 清楚该节点的关系
	public void clearRelation() {
		this.parent = null;
		this.setLeft(null);
		this.setRight(null);
	}

	public void addParent(BBNode<K, V> parent) {
		this.parent = parent;
		if (parent != null) {
			int compare = this.compareTo(parent);
			if (compare < 0)
				parent.setLeft(this);
			else if (compare > 0)
				parent.setRight(this);
			else
				throw new IllegalArgumentException(this + "==" + parent);
		}
	}

	public void addLeft(BinaryNode<K, V> left) {
		super.setLeft(left);
		if (left != null)
			((BBNode<K, V>) left).setParent(this);
	}

	public void addRight(BinaryNode<K, V> right) {
		super.setRight(right);
		if (right != null)
			((BBNode<K, V>) right).setParent(this);
	}

	public BBNode<K, V> getParent() {
		return parent;
	}

	// 控制该方法的访问权限
	private void setParent(BBNode<K, V> parent) {
		this.parent = parent;
	}

	@Override
	public BBNode<K, V> getLeft() {
		return (BBNode<K, V>) super.getLeft();
	}

	@Override
	public BBNode<K, V> getRight() {
		return (BBNode<K, V>) super.getRight();
	}

	@Override
	public int compareTo(BBNode<K, V> node) {
		return this.getKey().compareTo(node.getKey());
	}

}
