package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Applications {
	private Apps apps;
	
	public class Apps {
		public App[] app;

		public App[] getApp() {
			return app;
		}

		public void setApp(App[] app) {
			this.app = app;
		}
		
		
		
	}
	
	public class App {
		public String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		
	}

	public Apps getApps() {
		return apps;
	}

	public void setApps(Apps apps) {
		this.apps = apps;
	}
	
	
}


