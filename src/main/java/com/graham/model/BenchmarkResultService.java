package com.graham.model;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BenchmarkResultService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static final String COLLECTION_NAME = "benchmark_result";
	
	public void addBenchmarkResult(BenchmarkResult result) {
		if (!mongoTemplate.collectionExists(BenchmarkResult.class)) {
			mongoTemplate.createCollection(BenchmarkResult.class);
		}		
		result.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(result, COLLECTION_NAME);
		Log.info("SAVE SUCCESS!");
	}
	
	public BenchmarkResult getBenchmarkResult(String id) {
		return mongoTemplate.findById(id, BenchmarkResult.class, COLLECTION_NAME);
	}
	
	public List<BenchmarkResult> listBenchmarkResult() {
		return mongoTemplate.findAll(BenchmarkResult.class, COLLECTION_NAME);
	}
	
	public List<BenchmarkResult> listBenchmarkResultByDate() {
		Query q = new Query().with(new Sort(Sort.Direction.DESC, "date"));
		
		return mongoTemplate.find(q, BenchmarkResult.class, COLLECTION_NAME);
	}
	
	public void deleteBenchmarkResult(BenchmarkResult result) {
		mongoTemplate.remove(result, COLLECTION_NAME);
	}
	
	public void updateBenchmarkResult(BenchmarkResult result) {
		mongoTemplate.insert(result, COLLECTION_NAME);		
	}
	
}
