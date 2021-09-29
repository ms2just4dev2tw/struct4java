package org.self.graph;

public interface Adjacency {

	/**
	 * 无向图设置边
	 * 
	 * @param startVertex 边的起始顶点
	 * @param endVertex 边的终止顶点
	 */
	public void setEdge(int startVertex, int endVertex);

	/**
	 * 有向图设置边
	 * 
	 * @param startVertex 边的起始顶点
	 * @param endVertex 边的终止顶点
	 * @param weight 有向图边的权重
	 */
	public void setEdge(int startVertex, int endVertex, int weight);

	/**
	 * @param startVertex 边的起始顶点
	 * @param endVertex 边的终止顶点
	 * @return 带权图返回权重；其他图返回边的存在：0，不存在；1，存在
	 */
	public int getEdgeValue(int startVertex, int endVertex);

	/**
	 * @return 返回图的顶点个数
	 */
	public int getVertexNum();

}
