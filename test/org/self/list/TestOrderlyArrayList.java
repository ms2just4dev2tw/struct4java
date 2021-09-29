package org.self.list;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestOrderlyArrayList {

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

	@Test
	void testCompareToNull() {
		Integer key1 = 2;
		Integer key2 = 0;
		Integer null0 = null;
		System.out.println(key1.compareTo(key2));
		System.out.println(key1.compareTo(null0));
	}

}
