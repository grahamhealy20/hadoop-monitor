<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Clusters - Hadoop Monitor</title>

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
				<li class="active"><a href="clusters">Clusters <span
						class="sr-only">(current)</span></a></li>
			</ul>
			
			
		<ul class="nav navbar-nav navbar-right">
			<sec:authorize access="isAuthenticated()">
			    <li><a href=""><sec:authentication property="principal.username" /></a></li>
			</sec:authorize>
			
        	<li><a href="/HadoopMon/logout">Logout</a></li>
      	</ul>
		</div>
	</div>
</nav>
<body>

	<div class="container-fluid">
		<h1>Clusters</h1>
		<div class="row">
	
			<div class="col-md-12 table-responsive">
			<a href="add" class="btn btn-primary">Add Cluster</a>
				
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>IP Address</th>
						</tr>

					</thead>

					<tbody>
						<c:forEach var="cluster" items="${clusters}">

							<tr>
								<td>${cluster.name}</td>
								<td>${cluster.ipAddress}</td>
								<td><a class="btn btn-info btn-xs"
									href="cluster?id=${cluster.id}">Details</a> <a
									class="btn btn-danger btn-xs" href="delete?id=${cluster.id}">Delete</a></td>
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
				
				
				$.ajax({
					url: "dfsio",
					method: "POST",
					data: {
						numFiles: $('#numFiles').val(),
						fileSize: $('#fileSize').val()
					},
					success: function(data) {
						console.log(data);
						$('tbody').prepend('<tr><td>' + data.type + '</td> <td>' + data.date +'</td> <td>' + data.nrFiles +'</td> <td class="megabyte">' + data.totalMb + '</td> <td class="megabytePerSec">' + data.throughputMb + '</td> <td class="megabytePerSec">'+ data.avgIORate +'</td> <td>'+ data.stdDeviation + '</td> <td class="seconds">'+ data.totalTime +'</td>' +
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