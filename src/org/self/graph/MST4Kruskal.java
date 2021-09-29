package org.self.graph;

/**
 * Minimum Spanning Tree 最小生成树
 * <p>
 * Kruskal算法 贪心策略
 * 
 * @author TungWang
 *
 */
public class MST4Kruskal {

	// 边
	private class Edge {
		int startVertex;
		int endVertex;
		int weight;

		private Edge(int startVertex, int endVertex, int weight) {
			this.startVertex = startVertex;
			this.endVertex = endVertex;
			this.weight = weight;
		}
	}

	private int length;
	private Edge allEdge[];

	public MST4Kruskal(Adjacency graph) {
		length = graph.getVertexNum();
		allEdge = new Edge[length * length];
		// 过滤掉边值为0和边值无法达的无效边
		int top = 0;
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++) {
				int edgeValue = graph.getEdgeValue(i, j);
				if (edgeValue != 0 && edgeValue != Integer.MAX_VALUE)
					allEdge[top++] = new Edge(i, j, edgeValue);
			}
		// 重新确定边界长度
		Edge tmp[] = new Edge[top];
		for (int i = 0; i < top; i++)
			tmp[top] = allEdge[i];
		allEdge = tmp;
	}

	public void MinimumSpanningTree() {
		//
		for (int times = 0, index = 0; times < length - 1; index++) {
			int start = allEdge[index].startVertex, end = allEdge[index].endVertex;

		}
	}

}
