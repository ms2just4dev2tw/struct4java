package org.self.other;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class Test {

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

	@org.junit.jupiter.api.Test
	void test() {
		boolean a = true;
		boolean b = false;
		boolean test = false;
		test |= b;
		System.out.println(test);
		test |= a;
		System.out.println(test);

		int num = 0x40000000 - 1;
		System.out.println(Integer.toHexString(num));
	}

}
