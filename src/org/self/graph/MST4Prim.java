package org.self.graph;

/**
 * Minimum Spanning Tree 最小生成树
 * <p>
 * Prim算法 贪心策略
 * 
 * @author TungWang
 *
 */
public class MST4Prim {

	// private AdjacencyList graph;

	public MST4Prim() {

	}

	/**
	 * 最小生成树
	 * <p>
	 * 1，初始化closest和lowCast
	 * <p>
	 * 2，找出下一个最短边的顶点，这会执行length - 1次
	 * <p>
	 * 3，修改满足条件的closest和lowCast
	 * <p>
	 * closest，可以用来构造最小生成树；lowCast，用来选择下一个顶点
	 * 
	 * @param graph 图的数据结构
	 * @param startVertex 图的起始节点
	 */
	public void MinimumSpanningTree(AdjacencyList graph, int startVertex) {
		int length = graph.getVertexNum();
		// closest是每次找出的顶点，lowCast是closest中的顶点到其他未加入顶点的最短边
		int closest[] = new int[length], lowCast[] = new int[length];
		// 初始化属性
		for (int i = 0; i < length; i++)
			lowCast[i] = graph.getEdgeValue(startVertex, i);
		closest[0] = startVertex;
		// 找出length - 1个顶点
		int min = Integer.MAX_VALUE, selectedVertex = -1;
		for (int times = 0; times < length - 1; times++) {
			min = Integer.MAX_VALUE;
			// 从lowCast中找出下一个最短边的顶点
			for (int i = 0; i < lowCast.length; i++)
				if (lowCast[i] != 0 && lowCast[i] < min) {
					min = lowCast[i];
					selectedVertex = i;
				}
			// 标记顶点selectedVertex已经加入最小生成树的顶点集合
			lowCast[selectedVertex] = 0;
			// 修改lowCast和closest的值
			for (int i = 0; i < length; i++) {
				int edgeValue = graph.getEdgeValue(selectedVertex, i);
				if (edgeValue != 0 && edgeValue < lowCast[i]) {
					lowCast[i] = edgeValue;
					// 对满足要求的lowCast[i]设置closest[i]为当前选择的顶点
					closest[i] = selectedVertex;
				}
			}
		}
	}

	public void MinimumSpanningTree(AdjacencyMatrix graph, int startVertex) {

	}
}
