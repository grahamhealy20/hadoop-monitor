package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameNodeActivityMetric extends Bean {

	@JsonProperty("CreateFileOps") private int createFileOps;
	@JsonProperty("FilesCreated") private int filesCreated;
	@JsonProperty("FilesAppended") private int filesAppended;
	@JsonProperty("GetBlockLocations") private int getBlockLocations;
	@JsonProperty("FilesRenamed") private int filesRenamed;
	@JsonProperty("FilesTruncated") private int filesTruncated;
	@JsonProperty("GetListingOps") private int getListingOps;
	@JsonProperty("DeleteFileOps") private int deleteFileOps;
	
}
