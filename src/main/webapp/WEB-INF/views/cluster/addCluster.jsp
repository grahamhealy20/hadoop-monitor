<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Add a Cluster - Hadoop Monitor</title>

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
	<ol class="breadcrumb">
		<li><a href="clusters">Clusters</a></li>
		<li class="active">Add a Cluster</li>
	</ol>
	<div class="container-fluid">

		<div class="row">
			<div class="col-md-3">
				<h1>Add a new Cluster</h1>
				<hr />
				
				<form action="add" method="post">
				  <div class="form-group">
				    <label for="name">Cluster Name</label>
				    <input type="text" class="form-control" id="name" name="name" placeholder="Name" required>
				  </div>
				  <div class="form-group">
				    <label for="ipAddress">IP Address</label>
				    <input type="text" class="form-control" id="ipAddress" name="ipAddress" placeholder="IP Address" required>
				  </div>
					<hr />
				  <a class="btn btn-default" href="clusters">Back to Clusters</a>
				  <button type="submit" class="btn btn-primary">Add new Cluster</button>
				</form>
				
				
			</div>

		</div>
	</div>
</body>
</html>