package org.self.tree.node;

/**
 * 哈夫曼树的元素
 * 
 * @author TungWang
 */
public class HuffmanElement implements Comparable<HuffmanElement> {

	// 父节点到该节点的权重值
	private int weight;
	private HuffmanElement left;
	private HuffmanElement right;

	public HuffmanElement() {
	}

	public HuffmanElement(int weight) {
		this.weight = weight;
	}

	public HuffmanElement(HuffmanElement element) {
		this.left = element.getLeft();
		this.right = element.getRight();
		this.weight = element.getWeight();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public HuffmanElement getLeft() {
		return left;
	}

	public void setLeft(HuffmanElement left) {
		this.left = left;
	}

	public HuffmanElement getRight() {
		return right;
	}

	public void setRight(HuffmanElement right) {
		this.right = right;
	}

	@Override
	public int compareTo(HuffmanElement element) {
		return weight - element.weight;
	}

}
