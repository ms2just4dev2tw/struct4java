package org.self.graph;

import org.self.list.TriangleArray;

/**
 * 邻接矩阵
 * 
 * @author TungWang
 */
public class AdjacencyMatrix implements Adjacency {

	// 顶点的个数
	private int vertexes;
	// 有向图的存储形式
	private int matrix[][];
	// 无向图可以压缩存储矩阵上三角元素
	private TriangleArray array;

	public AdjacencyMatrix(int vertexes) {
		this(vertexes, true);
	}

	/**
	 * @param vertexes 顶点的个数
	 * @param directed true：有向图，false：无向图
	 */
	public AdjacencyMatrix(int vertexes, boolean directed) {
		if (directed) {
			matrix = new int[vertexes][vertexes];
			for (int i = 0; i < vertexes; i++)
				for (int j = 0; j < vertexes; j++)
					if (i == j)
						matrix[i][j] = 0;
					else
						matrix[i][j] = Integer.MAX_VALUE;
		} else
			array = new TriangleArray(vertexes);
		this.vertexes = vertexes;
	}

	/**
	 * 无向图设置边
	 * 
	 * @param startVertex 边的起始顶点
	 * @param endVertex 边的终止顶点
	 */
	@Override
	public void setEdge(int startVertex, int endVertex) {
		int value = startVertex == endVertex ? 0 : 1;
		array.setValue(startVertex, endVertex, value);
	}

	/**
	 * 有向图设置边
	 * 
	 * @param startVertex 边的起始顶点
	 * @param endVertex 边的终止顶点
	 * @param weight 有向图边的权重
	 */
	@Override
	public void setEdge(int startVertex, int endVertex, int weight) {
		// 如果起始顶点 == 终止顶点，边设为0
		matrix[startVertex][endVertex] = startVertex == endVertex ? 0 : weight;
	}

	@Override
	public int getEdgeValue(int startVertex, int endVertex) {
		return array == null ? matrix[startVertex][endVertex] : array.getValue(startVertex, endVertex);
	}

	@Override
	public int getVertexNum() {
		return vertexes;
	}

}
