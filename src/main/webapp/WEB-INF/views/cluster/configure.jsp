<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Cluster Overveiew - Hadoop Monitor</title>

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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sidebar.css"></script>

<!-- jQuery Validate 	 -->
<!-- <script src="http://cdn.jsdelivr.net/jquery.validation/1.14.0/jquery.validate.min.js"></script> -->



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
  height: 6px;
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
			<li><a href="/HadoopMon/dfsio/dfsiobenchmarks/?id=${cluster.id}">DFSIO</a></li>
			<li><a href="/HadoopMon/mrbench/mrbenchmarks?id=${cluster.id}">MRBench</a></li>
			<li><a href="/HadoopMon/terasort/benchmarks?id=${cluster.id}">TeraSort</a></li>
			<li class="active"><a href="configure?id=${cluster.id}">Configure</a></li>
		</ul>
	</div>

	<div id="body" class="container-fluid">
		<h1>Configure Cluster</h1>
		<div class="col-md-12">
			<h2>Name: ${cluster.name}</h2>
			<h2>IP Address: ${cluster.ipAddress}</h2>
		</div>
	
	</div>
	
	<script>
		$(document).ready(function() {

			
			
			
		});
	</script>
</body>
</html>
