package com.graham.model.dbaccess;

import java.util.List;
import java.util.UUID;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.graham.model.Cluster;
import com.graham.model.Metric;

@Repository
public class MetricsService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "metrics";

	public Metric addMetric(Metric metric) {
		if (!mongoTemplate.collectionExists(Cluster.class)) {
			mongoTemplate.createCollection(Cluster.class);
		}		
		metric.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(metric, COLLECTION_NAME);
		Log.info("SAVE SUCCESS!");
		return metric;
	}

	public Metric getMetric(String id) {
		return mongoTemplate.findById(id, Metric.class, COLLECTION_NAME);
	}

	public List<Metric> listMetrics() {
		return mongoTemplate.findAll(Metric.class, COLLECTION_NAME);
	}

	public void deleteMetric(Metric metric) {
		mongoTemplate.remove(metric, COLLECTION_NAME);
	}

	public void updateMetric(Metric metric) {
		Metric metricToUpdate = mongoTemplate.findOne(Query.query(Criteria.where("id").is(metric.getId())), Metric.class);
		metricToUpdate = metric;
		mongoTemplate.save(metricToUpdate, COLLECTION_NAME);
	}
}