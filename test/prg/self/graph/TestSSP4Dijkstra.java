package prg.self.graph;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.self.graph.AdjacencyMatrix;
import org.self.graph.SSP4Dijkstra;

class TestSSP4Dijkstra {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Matrix by Test		<p>
	 * 0	4	6	6	w	w	w	<p>
	 * w	0	1	w	7	w	w	<p>
	 * w	w	0	w	6	4	w	<p>
	 * w	w	2	0	w	5	w	<p>
	 * w	w	w	w	0	w	6	<p>
	 * w	w	w	w	1	0	8	<p>
	 * w	w	w	w	w	w	0
	 */
	@Test
	void test() {
		AdjacencyMatrix matrix = new AdjacencyMatrix(7);
		matrix.setEdge(0, 1, 4);
		matrix.setEdge(0, 2, 6);
		matrix.setEdge(0, 3, 6);
		matrix.setEdge(1, 2, 1);
		matrix.setEdge(1, 4, 7);
		matrix.setEdge(2, 4, 6);
		matrix.setEdge(2, 5, 4);
		matrix.setEdge(3, 2, 2);
		matrix.setEdge(3, 5, 5);
		matrix.setEdge(4, 6, 6);
		matrix.setEdge(5, 4, 1);
		matrix.setEdge(5, 6, 8);
		new SSP4Dijkstra(matrix, 0).SingleSourceShortestPaths();
	}

}
