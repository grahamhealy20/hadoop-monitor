package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="name", visible = true)
@JsonSubTypes({
	@JsonSubTypes.Type(value = JvmMetric.class, name="Hadoop:service=NameNode,name=JvmMetrics" )
})
public abstract class Bean {
	private String name;
	private String modelerType;
	@JsonProperty("tag.Context") private String tagContext;
	
	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	public String getModelerType() {
		return modelerType;
	}

	public void setModelerType(String modelerType) {
		this.modelerType = modelerType;
	}
	
	public String getTagContext() {
		return tagContext;
	}

	public void setTagContext(String tagContext) {
		this.tagContext = tagContext;
	}
}

