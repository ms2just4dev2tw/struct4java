package org.self.graph;

/**
 * 邻接链表
 * 
 * @author TungWang
 *
 */
public class AdjacencyList implements Adjacency {

	public static class AdjacencyNode {
		private int vertex;
		private int weight;
		private AdjacencyNode next;

		public AdjacencyNode(int vertex, int weight) {
			this.next = null;
			this.vertex = vertex;
			this.weight = weight;
		}

		public boolean hasNext() {
			return next != null;
		}

		public AdjacencyNode next() {
			return next;
		}
	}

	// 顶点个数
	private int vertexes;
	// 顶点访问标记
	private boolean visited[];
	// 链表数组
	private AdjacencyNode list[];

	public AdjacencyList(int vertexes) {
		this.vertexes = vertexes;
		visited = new boolean[vertexes];
		list = new AdjacencyNode[vertexes];

		// 初始化list的每个首部节点为(0,0),(1,1),......,(vertexes,vertexes)
		for (int i = 0; i < vertexes; i++)
			list[i] = new AdjacencyNode(i, 0);
	}

	/**
	 * 广度优先遍历
	 * <p>
	 * 可以使用队列结构来辅助该过程
	 */
	public void BreadthFirstSearch() {
		resetVisited();
		AdjacencyNode tmp;
		for (int i = 0; i < list.length; i++)
			for (tmp = list[i].next; tmp.hasNext(); tmp = tmp.next)
				if (visited[tmp.vertex] == false) {
					visited[tmp.vertex] = true;
					System.out.println(tmp.vertex);
				}
	}

	/**
	 * 深度优先遍历
	 * <p>
	 * 借用栈结构去辅助该过程
	 */
	public void DepthFirstSearch() {
		resetVisited();
		int top = 0; // stack的顶部指针
		int stack[] = new int[list.length];

		AdjacencyNode tmp = list[0].next;
		while (tmp.hasNext() || top != 0) {
			if (tmp.hasNext()) {
				stack[top++] = tmp.vertex;
				tmp = tmp.next;
			} else
				top--;
		}
	}

	// 重置顶点的访问标记
	private void resetVisited() {
		for (int i = 0; i < visited.length; i++)
			visited[i] = false;
	}

	// 无向图
	@Override
	public void setEdge(int startVertex, int endVertex) {
		int value = startVertex == endVertex ? 0 : 1;
		setEdge(startVertex, endVertex, value);
	}

	// 有向图
	@Override
	public void setEdge(int startVertex, int endVertex, int weight) {
		AdjacencyNode node = list[startVertex];
		// 由于初始化的原因每个node至少有个节点(startVertex,startVertex)
		while (node.hasNext())
			if (node.vertex == endVertex) {
				node.weight = weight;
				return; // 设置值后停止方法
			} else
				node = node.next;
		// 找不到节点就指向一个新生成的节点
		node.next = new AdjacencyNode(endVertex, weight);
	}

	@Override
	public int getEdgeValue(int start, int end) {
		AdjacencyNode node = list[start];
		do {
			if (node.vertex == end)
				return node.weight;
			else
				node = node.next;
		} while (node != null);
		// 不存在这样的边
		return Integer.MAX_VALUE;
	}

	@Override
	public int getVertexNum() {
		return vertexes;
	}

}
