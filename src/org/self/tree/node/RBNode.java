package org.self.tree.node;

/**
 * RedBlackNode 红黑树的节点
 * 
 * @author TungWang
 */
public class RBNode<K extends Comparable<K>, V> extends BinaryNode<K, V> implements Comparable<RBNode<K, V>> {

	// 节点颜色标记，true：black；false：red
	private boolean color;
	public final static boolean BLACK = true, RED = false;
	// 父节点
	private RBNode<K, V> parent;

	public RBNode() {
	}

	public RBNode(K key, V value) {
		super(key, value);
		this.color = RED;
	}

	public RBNode(K key, V value, boolean color) {
		super(key, value);
		this.color = color;
	}

	public RBNode(RBNode<K, V> node) {
		super(node);
		this.color = node.getColor();
		this.parent = node.getParent();
	}

	public boolean isRed() {
		return color == RED;
	}

	public boolean isBlack() {
		return color == BLACK;
	}

	public boolean getColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	// 清楚该节点的关系
	public void clearRelation() {
		this.parent = null;
		this.setLeft(null);
		this.setRight(null);
	}

	public void addParent(RBNode<K, V> parent) {
		this.parent = parent;
		if (parent != null) {
			int compare = this.getKey().compareTo(parent.getKey());
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
			((RBNode<K, V>) left).setParent(this);
	}

	public void addRight(BinaryNode<K, V> right) {
		super.setRight(right);
		if (right != null)
			((RBNode<K, V>) right).setParent(this);
	}

	public RBNode<K, V> getParent() {
		return parent;
	}

	// 控制该方法的访问权限
	private void setParent(RBNode<K, V> parent) {
		this.parent = parent;
	}

	@Override
	public RBNode<K, V> getLeft() {
		return (RBNode<K, V>) super.getLeft();
	}

	@Override
	public RBNode<K, V> getRight() {
		return (RBNode<K, V>) super.getRight();
	}

	@Override
	public int compareTo(RBNode<K, V> node) {
		return this.getKey().compareTo(node.getKey());
	}

}
