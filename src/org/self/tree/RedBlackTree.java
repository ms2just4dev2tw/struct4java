package org.self.tree;

import org.self.tree.TreeDisplay.TreeType;
import org.self.tree.node.Node;
import org.self.tree.node.RBNode;

/**
 * 红黑树的性质
 * <p>
 * 1，每个节点非黑即红
 * <p>
 * 2，根节点是黑色（根属性）
 * <p>
 * 3，每个叶子节点(NIL)都是黑色
 * <p>
 * 4，红色节点的两个子节点都是黑色（红属性）
 * <p>
 * 5，每个节点到其所有后代叶节点的简单路径上，均包含相同数目的黑色节点（黑属性）
 * <p>
 * 红黑树是一种弱平衡的二叉排序树
 * <p>
 * 它的插入，删除，查找都能保持log(n)的效率，所以在它的操作类型比较平衡的情况下使用
 * 
 * @author TungWang
 *
 */
public class RedBlackTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

	// 黑色根节点
	private RBNode<K, V> top;

	public RedBlackTree() {
	}

	public RedBlackTree(K key, V value) {
		top = new RBNode<>(key, value, true);
	}

	@Override
	public RBNode<K, V> search(Node<K, V> top, K key) {
		return (RBNode<K, V>) super.search(top, key);
	}

	@Override
	public boolean put(K key, V value) {
		if (top == null) // 如果top节点为null，直接插入就行了
			top = new RBNode<>(key, value, RBNode.BLACK);
		else {
			// 定位插入节点的位置，如果插入节点存在，插入失败
			RBNode<K, V> parent = search(top, key);
			RBNode<K, V> insert = new RBNode<>(key, value);
			// insert 节点添加父节点 parent
			insert.addParent(parent);
			// 调整树结构
			insertFixup(insert, parent);
		}
		return true;
	}

	/**
	 * 如果插入的节点node与其父节点parent同为红色，不符合红属性
	 * <p>
	 * 在父节点是祖父节点的左子树的情况下
	 * <p>
	 * 情况1，uncle节点为红色
	 * <p>
	 * 情况2，uncle节点为黑色，且node节点为parent的左节点
	 * <p>
	 * 情况3，uncle节点为黑色，且node节点为parent的右节点
	 * <p>
	 * 在父节点是祖父节点的右子树的情况下，可能的情况是相同的，不同点在于调整的方式
	 * 
	 * @param node
	 * @param parent
	 */
	private void insertFixup(RBNode<K, V> node, RBNode<K, V> parent) {
		// 节点 node, parent 都是红色时，需要调整
		for (; node.isRed() && parent.isRed(); parent = node.getParent()) {
			RBNode<K, V> grand = parent.getParent();
			RBNode<K, V> uncle = getBrother(parent, grand);

			// uncle节点为黑色
			if (nodeIsBlack(uncle)) {
				// node节点为parent的左节点
				if (node == parent.getLeft()) {
					grand.setColor(RBNode.RED); // grandfather设为红色
					if (parent == grand.getLeft()) { // parent在grandparent的位置确定旋转的方向
						parent.setColor(RBNode.BLACK); // 情况2，parent设为黑色
						node = rightRotate(parent); // parent节点右旋
					} else {
						node.setColor(RBNode.BLACK); // 情况5，node设为黑色
						node = leftRotate(rightRotate(node)); // node节点右旋，然后node节点左旋
					}
				} else { // uncle节点为黑色，且node节点为parent的右节点
					grand.setColor(RBNode.RED); // grandfather设为红色
					if (parent == grand.getLeft()) { // parent在grandparent的位置确定旋转的方向
						node.setColor(RBNode.BLACK); // 情况3，node设为黑色
						node = rightRotate(leftRotate(node)); // node节点左旋，然后node节点右旋
					} else {
						parent.setColor(RBNode.BLACK); // 情况6，parent设为黑色
						node = leftRotate(parent); // parent节点左旋
					}
				}
			} else { // uncle节点为红色，情况1和情况3是parent左右子树的两种情况
				if (grand != top) // 只有在 grand 不是 top 顶点时才设置
					grand.setColor(RBNode.RED); // grandfather设为红色可能会破坏红属性
				parent.setColor(RBNode.BLACK); // parent设为黑色
				uncle.setColor(RBNode.BLACK); // uncle设为黑色
				node = grand;
			}
		}
		// 如果
		top = node.getParent() == null ? node : top;
	}

	@Override
	public boolean delete(K key) {
		// 定位key指向的node
		RBNode<K, V> node = search(top, key);
		// 检测定位到的 node 是否正确
		if (node == null || node.getKey() != key)
			return false;
		// 找到 node 节点的替换节点
		RBNode<K, V> replace = findReplace(node);
		replace = replace == null ? node : replace;
		// 开始调整树结构
		return deleteFixup(node, replace);
	}

	/**
	 * 从左右子树中选择 key 值间距 node 最大的一边
	 * <p>
	 * 1，如果是左子树就就选择左子树下的最大关键字节点
	 * <p>
	 * 2，如果是右子树就就选择左子树下的最小关键字节点
	 * 
	 * @param node
	 * @return 返回 node 节点的替换节点
	 */
	private RBNode<K, V> findReplace(RBNode<K, V> node) {
		RBNode<K, V> left = node.getLeft(), right = node.getRight();
		int leftGap = left == null ? 0 : node.compareTo(left);
		int rightGap = right == null ? 0 : right.compareTo(node);
		//
		if (leftGap > rightGap)
			return max(left);
		else if (rightGap > leftGap)
			return min(right);
		else
			return leftGap != 0 ? max(node.getLeft()) : node;
	}

	/**
	 * 如果删除节点的颜色为黑色，就需要调整
	 * 
	 * @param node
	 * @param replace
	 * @return
	 */
	private boolean deleteFixup(RBNode<K, V> node, RBNode<K, V> replace) {
		RBNode<K, V> tmp = replace, parent = tmp.getParent();
		// 一直向上调整，直到节点不为黑色或者节点没有父节点
		for (; tmp.isBlack() && parent != null; parent = tmp.getParent()) {
			RBNode<K, V> brother = getBrother(tmp, parent);
			RBNode<K, V> leftNephew = brother == null ? null : brother.getLeft();
			RBNode<K, V> rightNephew = brother == null ? null : brother.getRight();

			// 兄弟节点为黑色
			if (nodeIsBlack(brother)) {
				// 兄弟节点的左右节点都为黑色，情况4和情况8
				if (nodeIsBlack(leftNephew) && nodeIsBlack(rightNephew)) {
					if (brother != null)
						brother.setColor(RBNode.RED); // 兄弟节点设为红色
					// 然后tmp节点指向父节点，会进一步判断
					tmp = parent;
				} else if (brother == parent.getRight()) {
					if (rightNephew.isRed()) { // 情况3
						brother.setColor(parent.getColor()); // brother设为parent的颜色
						rightNephew.setColor(RBNode.BLACK); // 右侄子设为黑色
						tmp = leftRotate(brother); // brother左旋
					} else { // 情况2
						rightNephew.setColor(parent.getColor()); // 右侄子设为parent的颜色
						tmp = rightRotate(leftRotate(rightNephew)); // 右侄子左旋，然后右旋
					}
					parent.setColor(RBNode.BLACK); // 父节点设为黑色
					// 要判断是否需要判断top的指向
					top = tmp.getParent() == null ? tmp : top;
					break; // 这里就已经可以停止循环
				} else {
					if (leftNephew.isRed()) { // 情况7
						brother.setColor(parent.getColor()); // brother设为parent的颜色
						leftNephew.setColor(RBNode.BLACK); // 左侄子设为黑色
						tmp = rightRotate(brother); // brother右旋
					} else { // 情况6
						leftNephew.setColor(parent.getColor()); // leftNephew设为parent的颜色
						tmp = leftRotate(rightRotate(leftNephew)); // 左侄子右旋，然后左旋
					}
					parent.setColor(RBNode.BLACK); // 父节点设为黑色
					// 要判断是否需要判断top的指向
					top = tmp.getParent() == null ? tmp : top;
					break; // 这里就已经可以停止循环
				}
			} else { // 兄弟节点为红色
				brother.setColor(RBNode.BLACK); // 兄弟节点设为黑色
				parent.setColor(RBNode.RED); // 父节点设为红色
				if (brother == parent.getRight())
					leftRotate(brother); // 情况1，兄弟节点左旋
				else
					rightRotate(brother); // 情况5，兄弟节点右旋
				// 这个解决方案不完整，会进入情况2的解决方案
			}
		}
		// 在tmp为红色的情况下，染色为黑色
		tmp.setColor(RBNode.BLACK);
		replace(node, replace);
		return true;
	}

	/**
	 * 
	 * @param node
	 * @param replace
	 * @return 返回replace的父节点
	 */
	private RBNode<K, V> replace(RBNode<K, V> node, RBNode<K, V> replace) {
		node.setKey(replace.getKey());
		node.setValue(replace.getValue());
		// 移除节点 replace
		return removeNode(replace);
	}

	/**
	 * 从红黑树中删除的节点最多只能有一个节点
	 * 
	 * @param node 节点
	 * @return node 节点的父节点
	 */
	private RBNode<K, V> removeNode(RBNode<K, V> node) {
		RBNode<K, V> parent = node.getParent(), left = node.getLeft(), right = node.getRight();
		// 释放 node 节点的关系
		node.clearRelation();
		// 如果 parent 存在，就用 child 去填补位置
		if (parent != null) {
			RBNode<K, V> child = left != null ? left : right;
			if (node.compareTo(parent) < 0) // node 在其父节点的左边
				parent.addLeft(child);
			else
				parent.addRight(child);
		} else // 如果没有父节点，就说明这是最后一个节点
			top = null;
		return parent;
	}

	/**
	 * 右旋操作
	 * 
	 * @param node 要进行右旋的node
	 */
	private RBNode<K, V> rightRotate(RBNode<K, V> node) {
		// parent一定存在，但是right和grandparent不一定存在
		RBNode<K, V> parent = node.getParent(), right = node.getRight();
		RBNode<K, V> grand = parent.getParent();
		// 旋转
		node.addRight(parent);
		parent.addLeft(right);
		node.addParent(grand);
		// 返回旋转后的高层节点
		return node;
	}

	/**
	 * 左旋操作
	 * 
	 * @param node 要进行左旋的操作
	 */
	private RBNode<K, V> leftRotate(RBNode<K, V> node) {
		// parent一定存在，但是left和grandparent不一定存在
		RBNode<K, V> parent = node.getParent(), left = node.getLeft();
		RBNode<K, V> grand = parent.getParent();
		// 旋转
		node.addLeft(parent);
		parent.addRight(left);
		node.addParent(grand);
		// 返回旋转后的高层节点
		return node;
	}

	private boolean nodeIsBlack(RBNode<K, V> node) {
		return node == null ? true : node.isBlack();
	}

	private RBNode<K, V> getBrother(RBNode<K, V> node, RBNode<K, V> parent) {
		return node == parent.getLeft() ? parent.getLeft() : parent.getRight();
	}

	@Override
	public RBNode<K, V> min(Node<K, V> top) {
		return (RBNode<K, V>) super.min(top);
	}

	@Override
	public RBNode<K, V> max(Node<K, V> top) {
		return (RBNode<K, V>) super.max(top);
	}

	@Override
	public void display() {
		TreeDisplay.display(top, TreeType.RedBlackTree);
		System.out.println();
	}

}
