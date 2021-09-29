package org.self.graph;

/**
 * Single-Source Shortest Paths 单源最短路径
 * <p>
 * Dijkstra算法
 * <p>
 * 1，将顶点集合V分为已经确定最短路径的顶点集合S和未确定最短路径的顶点集合U
 * <p>
 * 2，集合S开始只包含起始点start，数组dist为起始点到其他顶点的最短路径
 * <p>
 * 3，从dist找到除S中顶点外的最短的顶点tmp，将tmp加入S
 * <p>
 * 4，根据顶点tmp的边 k 来判断是否修改dist，dist[k] = min{dist[k]，dist[tmp]+tmp[k]}
 * 
 * @author TungWang
 *
 */
public class SSP4Dijkstra {

	// 顶点的个数，起始顶点
	private int num, startVertex;
	// 顶点start到顶点end的最短路径长度
	private int dist[];
	// 顶点start到顶点end的最短路径上，end顶点前面的一个顶点mid
	private int path[];
	// source true：未加入源点集合的顶点，false：已经加入到最短路径源点集合的顶点
	private boolean source[];
	// 图的数据结构
	private Adjacency graph;

	public SSP4Dijkstra(Adjacency graph, int startVertex) {
		this.graph = graph;
		this.startVertex = startVertex;
		this.num = graph.getVertexNum();
		dist = new int[num];
		path = new int[num];
		source = new boolean[num];

		// 初始化dist，AvailabledVertex的属性，对起始点重新设置属性
		for (int i = 0; i < num; i++) {
			source[i] = true;
			dist[i] = graph.getEdgeValue(startVertex, i);
			path[i] = dist[i] < Integer.MAX_VALUE ? startVertex : -1;
		}
		source[startVertex] = false;
	}

	public void SingleSourceShortestPaths() {
		// times是执行的次数，selected是选择的下一个顶点
		for (int times = 0; times < num - 1; times++) {
			int min = Integer.MAX_VALUE, selected = -1;
			// 从dist找到除source中顶点外的最短的顶点selectedVertex
			for (int i = 0; i < num; i++)
				if (dist[i] < min && source[i]) {
					min = dist[i];
					selected = i;
				}

			// 在选择了下一个顶点selected后更新与其相关的属性
			source[selected] = false;
			for (int i = 0; i < num; i++) {
				int edgeValue = graph.getEdgeValue(selected, i), value = dist[selected] + edgeValue;
				if (edgeValue < dist[i] && value < dist[i]) { // 为了避免溢出
					dist[i] = value;
					path[i] = selected;
				}
			}
		}
		displayPath();
	}

	/**
	 * 				第一次循环											第二次循环										<p>
	 * path		StringBuilder		更新path		next		StringBuilder		更新path		next	<p>
	 * [1]->0		0,1					-1				false															<p>
	 * [2]->1		0,1,2					-1				false															<p>
	 * [3]->0		0,3					-1				false															<p>
	 * [4]->5		5,4					 2				true			0,1,2,5,4			-1				false	<p>
	 * [5]->2		0,1,2,5				-1				false															<p>
	 * [6]->4		5,4,6					 2				true			0,1,2,5,4,6			-1				false	
	 */
	private void displayPath() {
		int tmpPath[] = new int[num];
		StringBuilder sbPath[] = new StringBuilder[num];
		// 初始化
		for (int i = 0; i < num; i++) {
			tmpPath[i] = path[i];
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
				System.out.println("顶点 [" + startVertex + "] 到顶点 [" + i + "] 的长度为：" + dist[i] + "    路径为：" + sbPath[i]);
	}

}
