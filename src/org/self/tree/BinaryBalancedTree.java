package org.self.tree;

import org.self.tree.TreeDisplay.TreeType;
import org.self.tree.node.BBNode;
import org.self.tree.node.Node;

/**
 * 平衡二叉树（又叫AVL树）
 * <p>
 * 优点是对于查找，不会受到插入和删除的影响，保持log2(n)
 * <p>
 * 缺点是对于插入和删除操作比较频繁的情况下，效率比较低下
 * <p>
 * 每个节点的平衡因子是其左子树的高度减去右子树的高度
 * <p>
 * 每个子树的高度是不同的，没有子节点的高度为0，然后有子树的节点的高度层层加一
 * <p>
 * 平衡二叉树的调整方式
 * <p>
 * 1，LL型调整
 * <p>
 * 2，RR型调整
 * <p>
 * 3，LR型调整
 * <p>
 * 4，RL型调整
 * <p>
 * AVL树适合查找多，插入和删除少的使用场景
 * <p>
 * 
 * @author TungWang
 *
 */
public class BinaryBalancedTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

	private BBNode<K, V> top;

	public BinaryBalancedTree() {
	}

	public BinaryBalancedTree(K key, V value) {
		top = new BBNode<>(key, value);
	}

	@Override
	public BBNode<K, V> search(Node<K, V> top, K key) {
		return (BBNode<K, V>) super.search(top, key);
	}

	@Override
	public boolean put(K key, V value) {
		if (top == null) // 如果top节点为null，直接插入就行了
			top = new BBNode<>(key, value);
		else {
			// 定位插入节点的位置
			BBNode<K, V> parent = search(top, key);
			BBNode<K, V> insert = new BBNode<>(key, value);
			// insert 节点添加父节点 parent
			insert.addParent(parent);
			// 层层向上计算节点的高度和平衡因子，判断是否进行调整
			fixup(insert);
		}
		return true;
	}

	@Override
	public boolean delete(K key) {
		// 定位key指向的node
		BBNode<K, V> node = search(top, key);
		// 检测定位到的 node 是否正确
		if (node == null || node.getKey() != key)
			return false;
		// 找到 node 节点的替换节点
		BBNode<K, V> replace = findReplace(node);
		// 开始调整树结构
		fixup(replace(node, replace));
		return true;
	}

	/**
	 * 
	 * @param node
	 * @return 根据node的平衡因子选择 node 的左边最大值，或者是选择 node 的右边最小值
	 */
	private BBNode<K, V> findReplace(BBNode<K, V> node) {
		if (node.getBalance() > 0)
			return max(node.getLeft());
		else if (node.getBalance() < 0)
			return min(node.getRight());
		else
			return node.getLeft() != null ? max(node.getLeft()) : node;
	}

	/**
	 * 节点的关键字和数据替换，并移除替换节点 replace
	 * 
	 * @param node 节点
	 * @param replace 替换节点 node 的节点
	 * @return 返回replace的父节点
	 */
	private BBNode<K, V> replace(BBNode<K, V> node, BBNode<K, V> replace) {
		node.setKey(replace.getKey());
		node.setValue(replace.getValue());
		// 移除节点 replace
		return removeNode(replace);
	}

	/**
	 * 从平衡二叉树中删除的节点最多只能有一个节点
	 * 
	 * @param node 节点
	 * @return node 节点的父节点
	 */
	private BBNode<K, V> removeNode(BBNode<K, V> node) {
		BBNode<K, V> parent = node.getParent(), left = node.getLeft(), right = node.getRight();
		// 释放 node 节点的关系
		node.clearRelation();
		// 如果 parent 存在，就用 child 去填补位置
		if (parent != null) {
			BBNode<K, V> child = left != null ? left : right;
			if (node.compareTo(parent) < 0) // node 在其父节点的左边
				parent.addLeft(child);
			else
				parent.addRight(child);
		} else // 如果没有父节点，就说明这是最后一个节点
			top = null;
		return parent;
	}

	@Override
	public BBNode<K, V> min(Node<K, V> top) {
		return (BBNode<K, V>) super.min(top);
	}

	@Override
	public BBNode<K, V> max(Node<K, V> top) {
		return (BBNode<K, V>) super.max(top);
	}

	// 每次插入或者删除都需要判断是否要进行调整
	private void fixup(BBNode<K, V> parent) {
		BBNode<K, V> node = null;
		for (; parent != null; parent = node.getParent()) {
			// 计算父节点的高度和平衡因子
			parent.setBalanceAndHeighr();
			// 平衡因子的绝对值等于 2 说明已经失衡
			if (parent.getBalance() == 2) {
				node = parent.getLeft();
				node = node.getBalance() == 1 ? rightRotate(node) : fixupLR(node);
			} else if (parent.getBalance() == -2) {
				node = parent.getRight();
				node = node.getBalance() == -1 ? leftRotate(node) : fixupRL(node);
			} else
				node = parent;
		}
		// 重置top指针指向
		top = node;
	}

	/**
	 * LL型调整
	 * <p>
	 * 1，top节点的左节点left右上旋转变成根节点
	 * <p>
	 * 2，根节点top右下旋转变成left的右节点
	 * <p>
	 * 3，原先left节点的右子树变成top节点的左子树
	 */
	private BBNode<K, V> rightRotate(BBNode<K, V> node) {
		// parent 一定存在，但是 right 和 grandparent 不一定存在
		BBNode<K, V> parent = node.getParent();
		BBNode<K, V> right = node.getRight();
		BBNode<K, V> grandparent = parent.getParent();
		// 旋转
		node.addRight(parent);
		parent.addLeft(right);
		node.addParent(grandparent);
		// 重新計算节点的高度和平衡因子
		parent.setBalanceAndHeighr();
		node.setBalanceAndHeighr();
		// 返回旋转后的高层节点
		return node;
	}

	/**
	 * RR型调整
	 * <p>
	 * 1，top节点的右节点right左上旋转变成根节点
	 * <p>
	 * 2，根节点top左下旋转变成right的左节点
	 * <p>
	 * 3，原先right节点的左子树变成top节点的右子树
	 */
	private BBNode<K, V> leftRotate(BBNode<K, V> node) {
		// parent 一定存在，但是 left 和 grandparent 不一定存在
		BBNode<K, V> parent = node.getParent();
		BBNode<K, V> left = node.getLeft();
		BBNode<K, V> grandparent = parent.getParent();
		// 旋转
		node.addLeft(parent);
		parent.addRight(left);
		node.addParent(grandparent);
		// 重新計算节点的高度和平衡因子
		parent.setBalanceAndHeighr();
		node.setBalanceAndHeighr();
		// 返回旋转后的高层节点
		return node;
	}

	/**
	 * LR型调整
	 * <p>
	 * 1，top节点的左节点left的右子树leftRight左上旋转变成left
	 * <p>
	 * 2，left左下旋转变成leftRight的左节点
	 * <p>
	 * 3，原先leftRight节点的左子树变成left节点的右子树
	 * <p>
	 * 4，然后，新的left节点右上旋转变成根节点
	 * <p>
	 * 5，根节点top右下旋转变成left的右节点
	 * <p>
	 * 6，原先left节点的右子树变成top节点的左子树
	 * <p>
	 * 简单来说，有top，left。leftRight三个节点
	 * <p>
	 * leftRight变成根节点，top变成根节点的右子树，left变成变成根节点的左子树
	 * <p>
	 * leftRight原先的左子树变成left的右子树，leftRight原先的右子树变成top的左子树
	 */
	private BBNode<K, V> fixupLR(BBNode<K, V> node) {
		// 先左旋转 再右旋转
		return rightRotate(leftRotate(node.getRight()));
	}

	/**
	 * RL型调整
	 * <p>
	 * 1，top节点的右节点right的左子树rightLeft右上旋转变成right
	 * <p>
	 * 2，right右下旋转变成rightLeft的右节点
	 * <p>
	 * 3，原先rightLeft节点的右子树变成right节点的左子树
	 * <p>
	 * 4，然后，新的right节点左上旋转变成根节点
	 * <p>
	 * 2，根节点top左下旋转变成right的左节点
	 * <p>
	 * 3，原先right节点的左子树变成right节点的右子树
	 * <p>
	 * 简单来说，有top，right。rightLeft三个节点
	 * <p>
	 * rightLeft变成根节点，top变成根节点的左子树，right变成变成根节点的右子树
	 * <p>
	 * rightLeft原先的左子树变成top的右子树，leftRight原先的右子树变成right的左子树
	 */
	private BBNode<K, V> fixupRL(BBNode<K, V> node) {
		// 先右旋转 再左旋转
		return leftRotate(rightRotate(node.getLeft()));
	}

	@Override
	public void display() {
		TreeDisplay.display(top, TreeType.BinaryBalancedTree);
		System.out.println();
	}

}
