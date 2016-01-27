package com.graham.model.dbaccess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.benchmarks.MRBenchmarkResult;

@Repository
public class MRBenchmarkResultService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static final String COLLECTION_NAME = "mr_benchmark_result";
	
	public void addBenchmarkResult(MRBenchmarkResult result) {
		if (!mongoTemplate.collectionExists(MRBenchmarkResult.class)) {
			mongoTemplate.createCollection(MRBenchmarkResult.class);
		}		
		result.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(result, COLLECTION_NAME);
		Log.info("SAVE SUCCESS!");
	}
	
	public MRBenchmarkResult getBenchmarkResult(String id) {
		return mongoTemplate.findById(id, MRBenchmarkResult.class, COLLECTION_NAME);
	}
	
	public List<MRBenchmarkResult> listBenchmarkResult() {
		return mongoTemplate.findAll(MRBenchmarkResult.class, COLLECTION_NAME);
	}
	
	public List<MRBenchmarkResult> listBenchmarkResultByDate() {
		Query q = new Query().with(new Sort(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(q, MRBenchmarkResult.class, COLLECTION_NAME);
	}
	
	public void deleteBenchmarkResult(MRBenchmarkResult result) {
		mongoTemplate.remove(result, COLLECTION_NAME);
	}
	
	public void updateBenchmarkResult(MRBenchmarkResult result) {
		mongoTemplate.insert(result, COLLECTION_NAME);		
	}

	public List<MRBenchmarkResult> listClusterBenchmarkResultByDate(String clusterId) {
		Query q = new Query().with(new Sort(Sort.Direction.DESC, "date"));
		q.addCriteria(Criteria.where("clusterId").is(clusterId));
		return mongoTemplate.find(q, MRBenchmarkResult.class, COLLECTION_NAME);
	}
	
}
