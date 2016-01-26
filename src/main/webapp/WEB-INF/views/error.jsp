<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error - Hadoop Monitor</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<style>
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
			<a class="navbar-brand" href="benchmarks">Hadoop Monitor</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="benchmarks">Benchmarks <span class="sr-only">(current)</span></a></li>
				<li><a href="/HadoopMon/cluster/clusters">Clusters</a></li>
			</ul>
		</div>
	</div>
</nav>

<body>
	<ol class="breadcrumb">
		<li><a href="benchmarks">Benchmarks</a></li>
		<li class="active">Error</li>
	</ol>
	<div class="container-fluid">

		<div class="row">
			<div class="col-md-12">
				<h1>Error</h1>
				<h2>${message}</h2>
				<hr />
				<a class="btn btn-primary" href="benchmarks">Back to Benchmarks</a>
			</div>



		</div>


	</div>
</body>