package org.self.tree;

import org.self.tree.node.Node;

/**
 * 树 A(B(E，F)，C(G(J))，D(H，I(K，L，M))) 的遍历方法有
 * <p>
 * 1，先根遍历
 * <p>
 * 先访问根，再从左至右的先根遍历根节点的每一棵子树
 * <p>
 * A，B，E，F，C，G，J，D，H，I，K，L，M
 * <p>
 * 2，后根遍历
 * <p>
 * 先从左至右的先根遍历根节点的每一棵子树，再访问根
 * <p>
 * E,，F，B，J，G，C，H，K，L，M，I，D，A
 * <p>
 * 3，层次遍历
 * <p>
 * 从根节点开始，从上至下，从左至右地遍历每一个节点
 * <p>
 * A，B，C，D，E，F，G，H，I，J，K，L，M
 * <p>
 * 
 * @author TungWang
 *
 */
public interface Tree<K, V> {

	/**
	 * 查找操作
	 * @param top 根节点
	 * @param key 查找的关键字
	 * @return 返回查找的节点，如果不存在该关键字的节点，返回最后查找的节点
	 */
	Node<K, V> search(Node<K, V> top, K key);

	/**
	 * 插入操作
	 * @param key 关键字
	 * @param value 值
	 * @return 存在一样关键字的节点返回false，否则插入并返回true
	 */
	boolean put(K key, V value);

	/**
	 * 删除操作
	 * @param key 删除节点的关键字
	 * @return 存在该关键字的节点，删除并返回true，否则返回false
	 */
	boolean delete(K key);

	/**
	 * top节点下面的最小节点
	 * @param top 当前节点，并不一定是根节点
	 * @return 找到的最小节点
	 */
	Node<K, V> min(Node<K, V> top);

	/**
	 *  top节点下面的最大节点
	 * @param top 当前节点，并不一定是根节点
	 * @return 找到的最大节点
	 */
	Node<K, V> max(Node<K, V> top);

	/**
	 * 二叉树类型
	 * <p>
	 * 一般是按照<b>中序遍历</b>的方式和<b>括号表示法</b>的结构形式来打印出二叉树的结构
	 * <p>
	 * 多叉树类型
	 * <p>
	 */
	void display();

}
