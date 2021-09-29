package org.self.tree;

import java.util.Stack;

import org.self.tree.node.BBNode;
import org.self.tree.node.BMNode;
import org.self.tree.node.BinaryNode;
import org.self.tree.node.HuffmanElement;
import org.self.tree.node.Node;
import org.self.tree.node.RBNode;

/**
 * 打印出树的结构
 * <p>
 * 树的表示法有4种，树形表示法，文氏图表示法，凹入表示法
 * <p>
 * 此处使用括号表示法，A(B(E，F)，C(G(J))，D(H，I(K，L，M)))
 * 
 * @author wh136
 *
 */
public class TreeDisplay {

	public enum TreeType {
		BinaryTree, // 二叉树
		BinarySearchTree, // 二叉搜索树
		BinaryBalancedTree, // 二叉平衡树
		RedBlackTree, // 红黑树
		HuffmanTree, // 哈夫曼树
		BalancedMultiwayTree, // B树，又称B-树
		BalancedPlusTree; // B+树
	}

	/**
	 * 先序遍历
	 * 
	 * @param data  完全二叉树的顺序存储结构
	 * @param start 当前起始节点位置
	 */
	public static void preOrder(int data[], int start) {

	}

	public static <K extends Comparable<K>, V> void preOrder(BinaryNode<K, V> top, TreeType type) {
		Stack<BinaryNode<K, V>> stack = new Stack<>();
		BinaryNode<K, V> node = top;

		while (node != null || !stack.empty()) {
			// 压入左节点
			while (node != null) {
				stack.push(node);
				print(node, type);
				node = node.getLeft();
			}

			// node没有左节点，打印node
			node = stack.pop();

			// 指向右节点
			node = node.getRight();
		}
	}

	/**
	 * 中序遍历
	 * 
	 * @param data     完全二叉树的顺序存储结构
	 * @param index    当前节点位置
	 * @param maxIndex 最大的节点位置
	 */
	public static void inOrder(int data[], int start, int end) {

	}

	public static <K extends Comparable<K>, V> void inOrder(BinaryNode<K, V> top, TreeType type) {
		Stack<BinaryNode<K, V>> stack = new Stack<>();
		stack.push(top);
		BinaryNode<K, V> node = top.getLeft();

		while (node != null || !stack.empty()) {
			// 压入左节点
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}

			// node没有左节点，打印node
			node = stack.pop();
			print(node, type);

			// 指向右节点
			node = node.getRight();
		}
	}

	/**
	 * 后序遍历
	 * <p>
	 * 难度在于如何确定节点的右子树被访问过
	 * 
	 * @param top
	 */
	public static <K extends Comparable<K>, V> void postOrder(BinaryNode<K, V> top, TreeType type) {
		Stack<BinaryNode<K, V>> stack = new Stack<>();
		BinaryNode<K, V> node = top;

		while (node != null || !stack.empty()) {
			// 压入左节点
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}

			// node没有左节点
			node = stack.pop();

			// 指向右节点
			BinaryNode<K, V> right = node.getRight();
			// 节点的右子树设为null，之后访问右子树就可以等同没有右子树
			node.setRight(null);

			if (right == null) {
				print(node, type);
				node = null;
			} else {
				stack.push(node);
				node = right;
			}
		}
	}

	/**
	 * 层次遍历（使用队列）
	 * 
	 * @param top
	 */
	public static <K extends Comparable<K>, V> void levelOrder(BinaryNode<K, V> top) {
	}

	// 打印树的结构
	public static void display(int data[], int start, int end) {
		System.out.print(data[start - 1]);
		int nextLeft = start * 2, nextRight = nextLeft + 1;

		// 递归打印左子树
		if (nextLeft <= end) {
			System.out.print("  (  ");
			display(data, nextLeft, end);
		}

		// 递归打印右子树
		if (nextRight <= end) {
			System.out.print("  ,  ");
			display(data, nextRight, end);
			System.out.print("  )  ");
		}

		// 单个叶子节点要补个括回
		if (nextLeft <= end && nextRight > end)
			System.out.print("  )  ");
	}

	public static <K extends Comparable<K>, V> void display(BinaryNode<K, V> top, TreeType type) {
		print(top, type);

		BinaryNode<K, V> nextLeft = top.getLeft(), nextRight = top.getRight();
		if (nextLeft == null && nextRight == null)
			return;

		System.out.print("  (  ");
		// 递归打印左子树
		if (nextLeft != null)
			display(nextLeft, type);
		// 递归打印右子树
		if (nextRight != null) {
			System.out.print("  ,  ");
			display(nextRight, type);
		}
		System.out.print("  )  ");
	}

	public static void display(HuffmanElement top) {
		// 打印上层节点
		System.out.printf("W[%d]", top.getWeight());

		// 开始准备左右子节点
		HuffmanElement nextLeft = top.getLeft(), nextRight = top.getRight();
		if (nextLeft == null && nextRight == null)
			return;

		System.out.print("  (  ");
		// 递归打印左子树
		if (nextLeft != null)
			display(nextLeft);
		// 递归打印右子树
		if (nextRight != null) {
			System.out.print("  ,  ");
			display(nextRight);
		}
		System.out.print("  )  ");
	}

	public static <K extends Comparable<K>, V> void display(BMNode<K, V> top, int count) {
		if (top.isLeaf())
			for (int index = 0; index < top.size4Entry(); index++)
				System.out.printf("%d-K[%d] ", count, top.getKey(index));
		else {
			for (int index = 0; index < top.size4Entry(); index++) {
				System.out.print(" ( ");
				display(top.getChild(index), count + 1);
				System.out.print(" ) ");
				System.out.printf("%d-K[%d] ", count, top.getKey(index));
			}
			System.out.print(" ( ");
			display(top.getChild(top.size4Entry()), count + 1);
			System.out.print(" ) ");
		}
	}

	private static <K extends Comparable<K>, V> void print(Node<K, V> node, TreeType type) {
		switch (type) {
		case BinarySearchTree:

			break;
		case BinaryBalancedTree:
			BBNode<K, V> bb = (BBNode<K, V>) node;
			System.out.printf("K[%d]-H[%d]-B[%d]", bb.getKey(), bb.getHeight(), bb.getBalance());
			break;
		case RedBlackTree:
			RBNode<K, V> rb = (RBNode<K, V>) node;
			String color = rb.isBlack() ? "B" : "R";
			System.out.printf("K[%d]:%s", rb.getKey(), color);
			break;
		default:

			break;
		}
	}
}
