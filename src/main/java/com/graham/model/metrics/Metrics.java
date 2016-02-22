package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metrics {
	private Object[] beans;

	public Object[] getBeans ()
	{
		return beans;
	}

	public void setBeans (Object[] beans)
	{
		this.beans = beans;
	}
}
