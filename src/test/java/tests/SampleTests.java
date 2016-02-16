package tests;

import junit.framework.TestCase;

public class SampleTests extends TestCase {

	public void test() {
		int a = 1;
		int b = 2;
		assertEquals(3, a + b);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
