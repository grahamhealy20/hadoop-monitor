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

<title>Cluster Overview - Hadoop Monitor</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"></link>	

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
<!-- PACE JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pace.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sidebar.css">

<!-- jQuery Validate 	 -->
<!-- <script src="http://cdn.jsdelivr.net/jquery.validation/1.14.0/jquery.validate.min.js"></script> -->

<script src="${pageContext.request.contextPath}/resources/sockjs-0.3.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/stomp.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/metricsocket.js"></script>


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

textarea {
	width: 100%;
	height: 150px;
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
<body onload="connect('${cluster.id}', onMetrics)">

	<div class="sidebar">
		<ul>
			<li class="active" ><a href="cluster?id=${cluster.id}">Overview</a></li>
			<li><a href="${pageContext.request.contextPath}/dfsio/dfsiobenchmarks/?id=${cluster.id}">DFSIO</a></li>
			<li><a href="${pageContext.request.contextPath}/mrbench/mrbenchmarks?id=${cluster.id}">MRBench</a></li>
			<li><a href="${pageContext.request.contextPath}/terasort/benchmarks?id=${cluster.id}">TeraSort</a></li>
			<li><a href="configure?id=${cluster.id}">Configure</a></li>
		</ul>
	</div>

	<div id="body" class="container-fluid">
		<div class="row">
	
			<h1>Cluster Overview</h1>
			<div class="col-md-12">
				<h2>Name: ${cluster.name}</h2>
				<h2>IP Address: ${cluster.ipAddress}</h2>
			
			<hr/>
			</div>
		</div>
		
		<div class="row">
			<h2>JVM Metrics</h2>
			<div class="col-md-12">
				<div class="col-md-3">
		
					<h4>Non Heap Memory Used</h4>
					<p id="nonHeapMemory"></p>
				</div>
				
				<div class="col-md-3">
					<h4>Heap Memory Used</h4>
					<p id="heapMem"></p>
				</div>
				
				<div class="col-md-3">
					<h4>Heap Memory Used</h4>
					<p id="heapMem"></p>
				</div>
			
			</div>
			
			<div class="col-md-12">
				<div class="col-md-3">
		
					<h4>Threads Blocked</h4>
					<p id="threadsBlocked"></p>
				</div>
				
				<div class="col-md-3">
					<h4>Threads New</h4>
					<p id="threadsNew"></p>
				</div>
				
				<div class="col-md-3">
					<h4>Threads Runnable</h4>
					<p id="threadsRunnable"></p>
				</div>

				<div class="col-md-3">
					<h4>Threads Waiting</h4>
					<p id="threadsWaiting"></p>
				</div>
			</div>
		</div>
		
		<!-- Logs -->
		<div class="row">
		<hr/>
		<h2>System Logs</h2>
			<div class="col-md-12">
				<a href="log/namenode?id=${cluster.id }">Name Node</a>
			</div>
			<div class="col-md-12">
				<a href="log/datanode?id=${cluster.id }">Data Node</a>
			</div>
			</div>
	</div>
	
	<script>
	
	// Websocket callback func
	function onMetrics(data) {
		console.log(data);
		var jvm = data.beans[0];
		console.log(jvm);
		$('#nonHeapMemory').text(jvm.MemNonHeapUsedM);
		$('#heapMem').text(jvm.MemHeapUsedM);
		
		$('#threadsBlocked').text(jvm.ThreadsBlocked);
		$('#threadsNew').text(jvm.ThreadsNew);
		$('#threadsRunnable').text(jvm.ThreadsRunnable);
		$('#threadsWaiting').text(jvm.ThreadsWaiting)
	}
	
	</script>
	
</body>
</html>
