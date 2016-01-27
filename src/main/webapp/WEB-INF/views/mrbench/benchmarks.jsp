<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>MRBench Benchmarks - Hadoop Monitor</title>

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

.megabyte:AFTER {
	content: " MB";
}

.megabytePerSec:AFTER {
	content: " MB/sec";
}

.milliseconds:AFTER {
	content: "ms";
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

	<div class="sidebar">
		<ul>
			<li><a href="/HadoopMon/cluster/cluster?id=${cluster.id}">Overview</a></li>
			<li><a href="/HadoopMon/dfsio/dfsiobenchmarks?id=${cluster.id}">DFSIO</a></li>
			<li class="active"><a href="?id=${cluster.id}">MRBench</a></li>
			<li><a href="/HadoopMon/terasort/benchmarks?id=${cluster.id}">TeraSort</a></li>
			<li><a href="/HadoopMon/cluster/configure?id=${cluster.id}">Configure</a></li>
		</ul>
	</div>

	<div id="body" class="container-fluid">
		<h1>MRBench Benchmarks</h1>
		<div class="row">

			<div class="col-md-12 table-responsive">
			
				<form id="mrbenchForm" class="form-inline" action="test" method="get">
				  <div class="form-group">
				    <label for="cluster">Cluster</label>
				    <select class="form-control" id="id" name="id" required>
				    	<c:forEach var="cluster" items="${clusters}">
				    		<option value="${cluster.id}"> ${cluster.name}</option>
				    	</c:forEach>
				    </select>
				  </div>
				  
				  <div class="form-group">
				    <label for="numFiles">Number of Runs</label>
				    <input type="number" class="form-control" id="numRuns" name="numRuns" placeholder="1" value="10" min="1" required>
				  </div>
				 
				  <button id="mrBenchAsync" class="btn btn-primary">Run Benchmark</button>
				</form>
			
				<table class="table">
					<thead>
						<tr>
							<th>Cluster</th>
							<th>Date</th>
							<th># Runs</th>
							<th>Data Lines</th>
							<th>Maps</th>
							<th>Reduces</th>
							<th>Total Time</th>
						</tr>

					</thead>

					<tbody>
						<c:forEach var="benchmark" items="${mrbench}">

							<tr>
								<td>${benchmark.clusterName }</td>
								<td>${benchmark.date}</td>
								<td>${benchmark.numRuns}</td>
								<td>${benchmark.dataLines}</td>
								<td>${benchmark.maps}</td>
								<td>${benchmark.reduces}</td>
								<td class="milliseconds">${benchmark.totalTime}</td>
								
								<td><a class="btn btn-info btn-xs"
									href="benchmark?id=${benchmark.id}">Details</a> <a
									class="btn btn-danger btn-xs" href="delete?id=${benchmark.id}">Delete</a></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>



	</div>
	
	<script>
		$(document).ready(function() {
			
			$('#mrBenchAsync').click(function(e) {
				
				
				$.ajax({
					url: "/HadoopMon/mrbench/mrbench",
					method: "POST",
					data: {
						id: $('#id').val(),
						numRuns: $('#numRuns').val()						
					},
					success: function(data) {
						console.log(data);
						$('tbody').prepend('<tr><td>' + data.clusterName + '</td> <td>' + data.date + '</td> <td>' + data.numRuns +'</td> <td>' + data.dataLines +'</td> <td>' + data.maps + '</td> <td>' + data.reduces + '</td> <td class="milliseconds">'+ data.totalTime +'</td>' +
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