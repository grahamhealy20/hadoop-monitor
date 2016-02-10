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

<title>DFSIO Benchmarks - Hadoop Monitor</title>

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sidebar.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"></link>

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
				<li><a href="${pageContext.request.contextPath}/cluster/clusters">Clusters</a></li>
			</ul>
		</div>
	</div>
</nav>
<body>

	<div class="sidebar">
		<ul>
			<li><a href="${pageContext.request.contextPath}/cluster/cluster?id=${cluster.id}">Overview</a></li>
			<li class="active"><a href="?id=${cluster.id}">DFSIO</a></li>
			<li><a href="${pageContext.request.contextPath}/mrbench/mrbenchmarks?id=${cluster.id}">MRBench</a></li>
			<li><a href="${pageContext.request.contextPath}/terasort/benchmarks?id=${cluster.id}">TeraSort</a></li>
			<li><a href="${pageContext.request.contextPath}/cluster/configure?id=${cluster.id}">Configure</a></li>
		</ul>
	</div>

	<div id="body" class="container-fluid">
		<h1>DFSIO Benchmarks</h1>
		<div class="row">

			<div class="col-md-12 table-responsive">
			
				<form id="dfsioForm" class="form-inline" action="test" method="get">
				  <div class="form-group">
				    <label for="cluster">Cluster</label>
				    <select class="form-control" id="id" name="id" required>
				    	<c:forEach var="cluster" items="${clusters}">
				    		<option value="${cluster.id}"> ${cluster.name}</option>
				    	</c:forEach>
				    </select>
				  </div>
				  
				  <div class="form-group">
				    <label for="numFiles">Number of Files</label>
				    <input type="number" class="form-control" id="numFiles" name="numFiles" placeholder="1" value="10" min="1" required>
				  </div>
				  <div class="form-group">
				    <label for="fileSize">File Size</label>
				    <input type="number" class="form-control" id="fileSize" name="fileSize" placeholder="1" value="100" min="1" required>
				  </div>
				  
				  <button id="dfsioAsync" class="btn btn-primary">Run Benchmark</button>
				  
				                              <input type="hidden"                        
							name="${_csrf.parameterName}"
							value="${_csrf.token}"/>
				  
				</form>
			
				<table class="table">
					<thead>
						<tr>
							<th>Cluster</th>
							<th>Type</th>
							<th>Date</th>
							<th># Files</th>
							<th>Total MB</th>
							<th class="megabytePerSec">Throughput</th>
							<th class="megabytePerSec">Avg IO Rate</th>
							<th>IO Rate Standard Deviation</th>
							<th>Total Time Taken</th>
						</tr>

					</thead>

					<tbody>
						<c:forEach var="benchmark" items="${dfsio}">
							<c:set value="1" var="isAlarm"></c:set>
							<c:if test="${benchmark.alarm eq true}">
								<c:set value="danger" var="isAlarm"></c:set>
							</c:if>
							
							<tr class="${isAlarm}">
								<td>${benchmark.clusterName }</td>
								<td>${benchmark.type}</td>
								<td>${benchmark.date}</td>
								<td>${benchmark.nrFiles}</td>
								<td class="megabyte">${benchmark.totalMb}</td>
								<td class="megabytePerSec">${benchmark.throughputMb}</td>
								<td class="megabytePerSec">${benchmark.avgIORate}</td>
								<td>${benchmark.stdDeviation}</td>
								<td class="seconds">${benchmark.totalTime}</td>
								<td>
								<a class="btn btn-info btn-xs"   href="${pageContext.request.contextPath}/dfsio/benchmark?id=${benchmark.id}">Details</a> 
								<a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/dfsio/delete?id=${benchmark.id}&clusterId=${cluster.id}">Delete</a></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>



	</div>
	
	<script>
		$(document).ready(function() {
			
			$('#dfsioAsync').click(function(e) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
					url: "${pageContext.request.contextPath}/dfsio/dfsio",
					method: "POST",
					data: {
						id: $('#id').val(),
						numFiles: $('#numFiles').val(),
						fileSize: $('#fileSize').val()
					},
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(data) {
						console.log(data);
						
						var classDgr = ""
						if(data.alarm == true) {
							classDgr = "danger";
						}							
							
						$('tbody').prepend('<tr class="'+ classDgr +'"><td>' + data.clusterName + '</td> <td>' + data.type + '</td> <td>' + data.date +'</td> <td>' + data.nrFiles +'</td> <td class="megabyte">' + data.totalMb + '</td> <td class="megabytePerSec">' + data.throughputMb + '</td> <td class="megabytePerSec">'+ data.avgIORate +'</td> <td>'+ data.stdDeviation + '</td> <td class="seconds">'+ data.totalTime +'</td>' +
							'<td><a class="btn btn-info btn-xs" href="benchmark?id=' + data.id +'">Details</a> ' +
							'<a class="btn btn-danger btn-xs" href="delete?id=' + data.id + '">Delete</a></td></td></tr>');
						
					}
					
				});
				console.log("Preventing Default");
				e.preventDefault();
				
			});
			
			
		});
	</script>
</body>
</html>