package org.self.tree;

/**
 * 不相交集合的数据结构，又称并查集
 * <p>
 * 亲戚问题
 * <p>
 * (2，4),(5，7),(1，3),(8，9),(1，2),(5，6),(2，3)是亲戚关系
 * <p>
 * 	问，(3，4),(7，10),(8，9)这三个中有几个是亲戚关系
 * 
 * @author TungWang
 */
public class DisjointSet<V> {

	private V data; // 存储的数据
	private int rank; // 当前节点的秩，即当前节点的高度
	private DisjointSet<V> parent;

	private DisjointSet(V data) {
		this.data = data;
		this.rank = 0; // 秩初始化为0
		this.parent = this; // 初始化父节点为自身
	}

	/**
	 * 合并两个集合
	 * 
	 * @param this 有根树的代表
	 * @param root 有根树的代表
	 */
	public void union(DisjointSet<V> node) {
		if (this.rank > node.rank)
			node.parent = this;
		else if (this.rank < node.rank)
			this.parent = node;
		else { // 只有两个节点的秩是相同的情况下，才会使得秩加一
			node.parent = this;
			this.rank++;
		}
	}

	/**
	 * 查找base1和base2是否处于同一个集合
	 * 
	 * @param this 基础节点1
	 * @param base 基础节点2
	 * @return
	 */
	public boolean isRelated(DisjointSet<V> base) {
		return findRoot(this).equals(findRoot(base));
	}

	/**
	 * 找出基础节点的根节点，也是这个有根树的代表
	 * 
	 * @param base 基础节点
	 * @return
	 */
	private DisjointSet<V> findRoot(DisjointSet<V> base) {
		// 直到base的父节点等于自身，就找到根节点
		while (base != base.parent)
			base = base.parent;
		return base;
	}

	public V getData() {
		return data;
	}

	public void setData(V data) {
		this.data = data;
	}

}
