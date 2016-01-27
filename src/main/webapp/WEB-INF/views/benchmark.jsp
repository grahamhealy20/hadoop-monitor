<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Benchmark Details - Hadoop Monitor</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
<!-- PACE JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pace.min.js"></script>
	

<style>
.pace {
  -webkit-pointer-events: none;
  pointer-events: none;

  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

.pace-inactive {
  display: none;
}

.pace .pace-progress {
  background: #29d;
  position: absolute;
  z-index: 2000;
  top: 50px;
  right: 100%;
  width: 100%;
  height: 3px;
}

.navbar {
	margin-bottom: 0px;
}
.breadcrumb {
	margin-bottom: 5px;
}
.megabyte:AFTER {
	content: " MB";
}

.megabytePerSec:AFTER {
	content: " MB/sec";
}

.seconds:AFTER {
	content: "s";
}


</style>
</head>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/HadoopMon/cluster/clusters">Hadoop Monitor</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/HadoopMon/cluster/clusters">Clusters</a></li>
			</ul>
		</div>
	</div>
</nav>

<body>
	<ol class="breadcrumb">
		<li><a href="benchmarks">Benchmarks</a></li>
		<li class="active">Benchmark Details</li>
	</ol>
	<div class="container-fluid">

		<div class="row">
			<div class="col-md-12">
				<h1>Benchmark Result</h1>
				<h2>Details</h2>
				<hr />

				<h4>Cluster</h4>
				<p>${benchmark.clusterName}</p>

				<h4>Type</h4>
				<p>${benchmark.type}</p>

				<h4>Test Date</h4>
				<p>${benchmark.date}</p>
				
				<h4># Files</h4>
				<p>${benchmark.nrFiles}</p>

				<h4>Total MB Processed</h4>
				<p class="megabyte">${benchmark.totalMb}</p>

				<h4>Throughput (MB/s)</h4>
				<p class="megabytePerSec">${benchmark.throughputMb}</p>

				<h4>Average IO Rate</h4>
				<p class="megabytePerSec">${benchmark.avgIORate}</p>

				<h4>Standard Deviation</h4>
				<p>${benchmark.stdDeviation}</p>

				<h4>Total Time Taken</h4>
				<p class="seconds">${benchmark.totalTime}</p>

				<hr />
				<a class="btn btn-primary" href="benchmarks">Back to Benchmarks</a>
				<a class="btn btn-danger" href="delete?id=${benchmark.id}">Delete
					Benchmark</a>
			</div>



		</div>


	</div>
</body>