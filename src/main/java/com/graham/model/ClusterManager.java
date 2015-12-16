package com.graham.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graham.model.dbaccess.ClusterService;

public class ClusterManager {


	
	public Cluster getCluster() {
		return new Cluster();
	}
	
	// TODO return list of clusters from db.
	public void getAllClusters() {

	}
	
	public ClusterManager() {
		
	}
}
