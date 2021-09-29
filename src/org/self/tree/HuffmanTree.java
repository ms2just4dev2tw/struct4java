package org.self.tree;

import org.self.queue.BinaryHeapEnum;
import org.self.queue.PriorityQueue;
import org.self.tree.node.HuffmanElement;

/**
 * 哈夫曼树（最优二叉树）
 * <p>
 * 一组数据和每个数据对应的权重
 * <p>
 * 1，选出权重最小的两个权重值
 * <p>
 * 2，这两个权重值作为一个生成节点的子节点，且生成节点权重为子节点之和
 * <p>
 * 3，将生成节点的权重加入比较范围内，而不再考虑两个最小权重值
 * <p>
 * 4，重复以上操作，最后生成一个最优权重二叉树
 * <p>
 * WPL = w[i] x l[i]，i 指的是所有叶子节点的其中一个，w 是权值，l 是根节点到 i 的路径长度
 * 
 * @author wh136
 *
 */
public class HuffmanTree {

	private int num;
	private HuffmanElement top;
	// 优先队列
	private PriorityQueue<HuffmanElement> queue;

	public HuffmanTree(int weight[]) {
		this.num = weight.length;
		queue = new PriorityQueue<>(num, BinaryHeapEnum.MIN_HEAP);
		// 添加节点到优先队列中
		for (int i = 0; i < num; i++)
			queue.insert(new HuffmanElement(weight[i]));
	}

	/**
	 * 生成哈夫曼树
	 * 
	 * @return
	 */
	public HuffmanTree build() {
		// 哈夫曼的生成过程中最多会循环 权重数组的长度-1
		for (int times = 0; times < num - 1; times++) {
			HuffmanElement node1 = queue.pop(), node2 = queue.pop();
			int weight1 = node1.getWeight(), weight2 = node2.getWeight();
			// 用最小的两个最小权重生成新的哈夫曼节点
			top = new HuffmanElement(weight1 + weight2);
			// 设置左右子树
			if (weight1 < weight2) {
				top.setLeft(node1);
				top.setRight(node2);
			} else {
				top.setLeft(node2);
				top.setRight(node1);
			}
			// 重新将top节点放入list中
			queue.insert(top);
		}
		return this;
	}

	// 哈夫曼编码
	public void code() {

	}

	public void display() {
		TreeDisplay.display(top);
		System.out.println();
	}

}
