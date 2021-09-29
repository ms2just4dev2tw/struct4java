package prg.self.graph;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.self.graph.ASP4Floyd;
import org.self.graph.AdjacencyMatrix;

class TestASP4Floyd {

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
	 * Matrix by Test	<p>
	 * 0	5	w	7			<p>
	 * w	0	4	2			<p>
	 * 3	3	0	2			<p>
	 * w	w	1	0
	 */
	@Test
	void test() {
		AdjacencyMatrix matrix = new AdjacencyMatrix(4);
		matrix.setEdge(0, 1, 5);
		matrix.setEdge(0, 3, 7);
		matrix.setEdge(1, 2, 4);
		matrix.setEdge(1, 3, 2);
		matrix.setEdge(2, 0, 3);
		matrix.setEdge(2, 1, 3);
		matrix.setEdge(2, 3, 2);
		matrix.setEdge(3, 2, 1);
		new ASP4Floyd(matrix, 4).AllPairsShortestPaths();
	}

}
