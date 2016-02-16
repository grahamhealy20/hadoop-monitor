package com.graham.model.dbaccess;

import java.util.List;
import java.util.UUID;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.graham.model.benchmarks.TeraSortBenchmarkResult;

@Repository
public class TeraSortBenchmarkResultService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "terasort_benchmark_result";

	public void addBenchmarkResult(TeraSortBenchmarkResult result) {
		if (!mongoTemplate.collectionExists(TeraSortBenchmarkResult.class)) {
			mongoTemplate.createCollection(TeraSortBenchmarkResult.class);
		}		
		result.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(result, COLLECTION_NAME);
		Log.info("SAVE SUCCESS!");
	}

	public TeraSortBenchmarkResult getBenchmarkResult(String id) {
		return mongoTemplate.findById(id, TeraSortBenchmarkResult.class, COLLECTION_NAME);
	}

	public List<TeraSortBenchmarkResult> listBenchmarkResult() {
		return mongoTemplate.findAll(TeraSortBenchmarkResult.class, COLLECTION_NAME);
	}

	public List<TeraSortBenchmarkResult> listBenchmarkResultByDate() {
		Query q = new Query().with(new Sort(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(q, TeraSortBenchmarkResult.class, COLLECTION_NAME);
	}

	public void deleteBenchmarkResult(TeraSortBenchmarkResult result) {
		mongoTemplate.remove(result, COLLECTION_NAME);
	}

	public void updateBenchmarkResult(TeraSortBenchmarkResult result) {
		mongoTemplate.insert(result, COLLECTION_NAME);		
	}

	public List<TeraSortBenchmarkResult> listClusterBenchmarkResultByDate(String clusterId) {
		Query q = new Query().with(new Sort(Sort.Direction.DESC, "date"));
		q.addCriteria(Criteria.where("clusterId").is(clusterId));
		return mongoTemplate.find(q, TeraSortBenchmarkResult.class, COLLECTION_NAME);
	}
}
