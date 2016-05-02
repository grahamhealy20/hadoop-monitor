package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.net.ConnectTimeoutException;
import org.junit.Test;

import com.graham.model.Cluster;
import com.graham.model.Metric;
import com.graham.model.Rule;
import com.graham.model.utils.ValidationHelper;

public class UnitTests {
	
	@Test
	public void test() {
		int a = 1;
		int b = 2;
		assertEquals(3, a + b);
	}

	/*
	 * VALIDATION TESTS
	 * */
	// Validate IP
	public void testValidCluster() {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.0.111");
		c.setName("Test cluster");
		c.setUsername("hadoop");
	
		boolean valid = ValidationHelper.validateIpAddress(c.getIpAddress());
		assertEquals(true, valid);
	}
	
	// Test cluster ip address with invalid data 
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCluster() {
		Cluster c = new Cluster();
		c.setIpAddress("abc.1.1234.-1");
		c.setName("Test cluster");
		c.setUsername("hadoop");
	}
	
	// Test invalid cluster info
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidClusterIP() {
		Cluster c = new Cluster();
		c.setIpAddress("abc.1.1234.-1");
		c.setName("Test cluster");
		c.setUsername("hadoop");
	}
	
	// Test required cluster name
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidClusterName() {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.192.0");
		c.setName("");
		c.setUsername("hadoop");
	}
	
	// Test required cluster username
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidClusterUser() {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.192.0");
		c.setName("Test cluster");
		c.setUsername("");
	}

	
	/*
	 * HADOOP TESTS
	 * */
	// Test to check that connection timeout is handled if cluster is unavailable
	@Test(expected = ConnectTimeoutException.class)
	public void testClusterTimeout() throws org.apache.hadoop.net.ConnectTimeoutException, IOException {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.100.100");
		c.setName("Test cluster");
		c.setUsername("hadoop");
		c.runDFSIOBenchmark(1, 10);
	}
	
	// Test that DFSIO benchmark input is validated
	@Test(expected = IllegalArgumentException.class)
	public void testHadoopDFSIO() throws ConnectTimeoutException, IOException {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.0.106");
		c.setName("Test cluster");
		c.setUsername("hadoop");
		
		c.runDFSIOBenchmark(0, 0);
	}
	
	// Test the MRBench benchmark input is validated
	@Test(expected = IllegalArgumentException.class)
	public void testHadoopMRBench() throws Exception {
		Cluster c = new Cluster();
		c.setIpAddress("192.168.0.106");
		c.setName("Test cluster");
		c.setUsername("hadoop");
		
		c.runMRBenchmarkAsync(0);
	}
	
	// Test rule creation - valid rule
	@Test
	public void testRuleCreation() {
		Rule r = new Rule("MemMaxM", "MaxMemory", "Equals", 100, "Email");
		assertEquals(true, r != null);
	}
	
	// Test rule, invalid data
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRuleCreation() {
		Rule r = new Rule("", "", "", 0, "");
	}
	
	@Test
	public void testMetricValidation() {
		Metric m = new Metric("TestMetric", "This is a test metric", "%", "100");
		assertEquals(true, m != null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMetricValidation() {
		Metric m = new Metric("", "", "%", "");
	}
}
