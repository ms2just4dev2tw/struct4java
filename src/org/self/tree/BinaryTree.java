package org.self.tree;

import org.self.tree.node.BinaryNode;

/**
 * 二叉树的结构
 * <p>
 * 1，满二叉树（叶子节点在同一高度）
 * <p>
 * 2，完全二叉树（左边叶子节点的高度大于等于右边叶子节点）
 * <p>
 * 二叉树的遍历
 * <p>
 * 1，先序遍历（等同于树的先根遍历）
 * <p>
 * 2，中序遍历（A(B(D(，G)))，C(E，F)）
 * <p>
 * 先中序遍历左子树，然后访问双亲节点，再访问右子树
 * <p>
 * 中序遍历的结果 D，G，B，A，E，C，F
 * <p>
 * 3，后序遍历（等同于树的后根遍历）
 * <p>
 * 4，层次遍历（等同于树的层次遍历）
 * <p>
 * 
 * @author TungWang
 */
public abstract class BinaryTree<K, V> implements Tree<K, V> {

	/**
	 *  查找后驱操作（这里是指中序遍历的后驱节点）
	 *  
	 * @param node 当前节点
	 * @return 根据二叉树的遍历方式返回当前节点的后驱节点
	 */
	public abstract BinaryNode<K, V> successor(BinaryNode<K, V> node);

	/**
	 *  查找前驱操作（这里是指中序遍历的前驱节点）
	 *  
	 * @param node 当前节点
	 * @return 根据二叉树的遍历方式返回当前节点的前驱节点
	 */
	public abstract BinaryNode<K, V> predecessor(BinaryNode<K, V> node);

}
