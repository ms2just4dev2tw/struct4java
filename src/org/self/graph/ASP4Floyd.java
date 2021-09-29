package org.self.graph;

/**
 * All-Pairs Shortest Paths 所有顶点之间的最短路径
 * <p>
 * Floyd-Warshall算法
 * 
 * @author TungWang
 * @see SSP4Dijkstra 该算法类似于Dijkstra算法
 */
public class ASP4Floyd {

	// 顶点个数
	private int num;
	// 顶点start到顶点end的最短路径长度
	private int dist[][];
	// 顶点start到顶点end的最短路径上，end顶点前面的一个顶点mid
	private int path[][];

	/**
	 * 		dist					path
	 * [[0, 5, w, 7], 		[[-1, 0, -1, 0],		<p>
	 * [w, 0, 4, 2],			[-1, -1, 1, 1], 		<p>
	 * [3, 3, 0, 2], 			[2, 2, -1, 2], 		<p>
	 * [w, w, 1, 0]]		[-1, -1, 3, -1]]
	 * 
	 * @param graph 图
	 * @param vertexes 顶点个数
	 */
	public ASP4Floyd(Adjacency graph, int vertexes) {
		this.num = graph.getVertexNum();
		dist = new int[num][num];
		path = new int[num][num];

		// 初始化dist，path的属性
		for (int i = 0; i < num; i++)
			for (int j = 0; j < num; j++) {
				dist[i][j] = graph.getEdgeValue(i, j);
				if (i != j && dist[i][j] < Integer.MAX_VALUE)
					path[i][j] = i; // 顶点 i 到顶点 j 有边时，i == j 默认无边
				else
					path[i][j] = -1; // 顶点 i 到顶点 j 没有边时，路径置为-1
			}
	}

	public void AllPairsShortestPaths() {
		// mid 从第一个顶点到最后一个顶点
		for (int mid = 0; mid < num; mid++)
			for (int start = 0; start < num; start++)
				for (int end = 0; end < num; end++) {
					// 在顶点 i 到顶点 j 的路径能不能找到一个顶点 times满足以下条件
					int value1 = dist[start][mid], value2 = dist[mid][end], value = value1 + value2;
					// 避免溢出
					if (value1 < dist[start][end] && value2 < dist[start][end] && value < dist[start][end]) {
						dist[start][end] = value;
						// 顶点 i 到顶点 j 的路径上倒数第2个顶点变成顶点 times 到顶点 j 的路径上倒数第2个顶点
						path[start][end] = path[mid][end];
					}
				}
		displayPath();
	}

	/**
	 * {@link SSP4Dijkstra 打印路径的方法具体见此类}
	 */
	public void displayPath() {
		for (int startVertex = 0; startVertex < num; startVertex++) {
			int tmpPath[] = new int[num];
			StringBuilder sbPath[] = new StringBuilder[num];
			// 初始化
			for (int i = 0; i < num; i++) {
				tmpPath[i] = path[startVertex][i];
				sbPath[i] = new StringBuilder().append(i);
			}
			tmpPath[startVertex] = -1;

			for (boolean next = true; next;) {
				next = false;
				for (int i = 0; i < num; i++)
					if (tmpPath[i] == -1) // 过滤掉起始顶点和所有指向起始顶点的顶点
						next |= false;
					else {
						sbPath[i].insert(0, ',').insert(0, sbPath[tmpPath[i]]);
						tmpPath[i] = tmpPath[i] == startVertex ? -1 : tmpPath[tmpPath[i]];
						// 判断新赋值的path是否是-1
						next |= tmpPath[i] == -1 ? false : true;
					}
			}

			// 打印StringBuilder 从顶点0到顶点1的路径长度为：4\t\t路径为：
			for (int i = 0; i < num; i++)
				if (i != startVertex)
					System.out.println("顶点 [" + startVertex + "] 到顶点 [" + i + "] 的长度为：" + dist[startVertex][i]
							+ "    路径为：" + sbPath[i]);
			System.out.println();
		}
	}
}
