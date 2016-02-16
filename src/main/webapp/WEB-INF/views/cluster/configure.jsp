<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token }"/>
<meta name="_csrf_header" content="${_csrf.headerName }"/>

<title>Cluster Overveiew - Hadoop Monitor</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"></link>	

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
	
<!-- PACE JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pace.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sidebar.css"><script type="text/javascript"></script>

<!-- jQuery Validate 	 -->
<!-- <script src="http://cdn.jsdelivr.net/jquery.validation/1.14.0/jquery.validate.min.js"></script> -->



<style type="text/css">
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
	height: 6px;
}
</style>
</head>

<nav class="navbar navbar-fixed-top navbar-inverse">
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
			<a class="navbar-brand" href="${pageContext.request.contextPath}/cluster/clusters">Hadoop Monitor</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="clusters">Clusters <span
						class="sr-only">(current)</span></a></li>
			</ul>
		</div>
	</div>
</nav>
<body>

	<div class="sidebar">
		<ul>
			<li><a class="active" href="cluster?id=${cluster.id}">Overview</a></li>
			<li><a href="${pageContext.request.contextPath}/dfsio/dfsiobenchmarks/?id=${cluster.id}">DFSIO</a></li>
			<li><a href="${pageContext.request.contextPath}/mrbench/mrbenchmarks?id=${cluster.id}">MRBench</a></li>
			<li><a href="${pageContext.request.contextPath}/terasort/benchmarks?id=${cluster.id}">TeraSort</a></li>
			<li class="active"><a href="configure?id=${cluster.id}">Configure</a></li>
		</ul>
	</div>

	<div id="body" class="container-fluid">
		<h1>Configure Cluster</h1>
		<hr/>
			<form action="updateCluster" method="post">
			
			<input type="hidden" name="id" id="id" value="${cluster.id }">
			
			<h2>General Information</h2>
			<div class="row">
			
			
				<div class="col-md-3">
					<div class="form-group">
						
						
						<label for="clusterName">Cluster Name</label> 
						<input type="text"
							class="form-control" name="clusterName" id="clusterName" value="${cluster.name}">
					</div>

					<div class="form-group">
						<label for="ipAddress">IP Address</label>
						 <input type="text"
							class="form-control" name="ipAddress" id="ipAddress" value="${cluster.ipAddress}">
					</div>
				</div>
			</div>
			
			<hr/>
			
			<h2>DFSIO Information</h2>
			<div class="row">
				
				<div class="col-md-3">
					<div class="form-group">
						<label for="throughputThreshold">Throughput Warning Threshold</label> 
						<input type="number"
							class="form-control" name="throughputThreshold" id="throughputThreshold" value="${cluster.throughputThreshold }">
					</div>
				</div>
			
			</div>
			
 		<button type="submit" class="btn btn-primary">Save</button>
		</form>
			
		</div>
	
</body>
</html>
