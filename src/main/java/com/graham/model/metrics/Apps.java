package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Maps to 8088/ws/v1/cluster/apps
@JsonIgnoreProperties(ignoreUnknown = true)
public class Apps {
	
	@JsonProperty("apps")
	private Object apps;

	public Object getApps() {
		return apps;
	}

	public void setApps(Object apps) {
		this.apps = apps;
	}

}
