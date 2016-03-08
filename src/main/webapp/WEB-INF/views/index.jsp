<!DOCTYPE html>
<html ng-app="admin">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<link rel="stylesheet" href="resources/css/angular-chart.min.css" />
	<link rel="stylesheet" href="resources/css/loading-bar.css"/>
    <link rel="stylesheet" href="resources/css/sidebar.css" />
    <link rel="stylesheet" href="resources/css/main.css" />
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
</head>

<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" ng-controller="NavbarCtrl">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" ng-href="#/">IBM</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a ng-href="#/">Clusters <span class="sr-only">(current)</span></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#/"><strong>{{time | date:format}}</strong></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav><!-- /.navbar -->

<body>
    <!--Template area inside ng-view -->
    <div ng-view>

    </div>
    
    <div class="alert alert-danger alert-dismissible" role="alert">
	  <button type="button" class="close"> <span aria-hidden="true">&times;</span></button>
	  <strong class="error-title">Error</strong> <p class="error-content"></p>
	</div>
	
	<div class="alert alert-success alert-dismissible" role="alert">
	  <button type="button" class="close"> <span aria-hidden="true">&times;</span></button>
	  <strong class="success-title">Success</strong> <p class="success-content"></p>
	</div>
	
	
	

    <!-- JS -->
    <script src="resources/js/jquery-2.2.0.min.js"></script>
    <script src="resources/js/Chart.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
     
    <!-- Websocket JS -->
    <script src="resources/js/sockjs-0.3.4.min.js"></script>
    <script src="resources/js/stomp.min.js"></script>
    <script src="resources/js/metricsocket.js"></script>

	<!-- Angular JS -->
    <script src="resources/js/angular.min.js"></script>
    <script src="resources/js/angular-route.min.js"></script>
    <script src="resources/js/angular-animate.min.js"></script>
    <script src="resources/js/angular-chart.min.js"></script>
    <script src="resources/js/loading-bar.js"></script>
    
    <script src="resources/js/config/angularInit.js"></script>
    <script src="resources/js/services/monitorService.js"></script>
    <script src="resources/js/controllers/clustersCtrl.js"></script>
    <script src="resources/js/controllers/overviewCtrl.js"></script>
    <script src="resources/js/controllers/jobsCtrl.js"></script>
    <script src="resources/js/controllers/dfsioCtrl.js"></script>
    <script src="resources/js/controllers/mrBenchCtrl.js"></script>
    <script src="resources/js/controllers/configureCtrl.js"></script>
    <script src="resources/js/controllers/navbarCtrl.js"></script>
    <script src="resources/js/controllers/sidebarCtrl.js"></script>
    <script src="resources/js/directives/sidebarDirective.js"></script>
    
        <script>
       
		
		////////// Metrics Socket //////////
		function metricsSocketCallback(data) {
			console.log(data);
		}
        
        ////////// Error Handler //////////
        function handleError(response) {
            $('.error-content').text(response.data.message);
        	$('.alert-danger').slideDown(250);
        	
        	//Close alert box after 2 seconds
        	setTimeout(function() {
        		$('.alert-danger').slideUp(250);
        	}, 5000);
        }
        
        ////////// Success Handler //////////
        function handleSuccess(message) {
            $('.success-content').text(message);
        	$('.alert-success').slideDown(250);
        	
        	//Close alert box after 2 seconds
        	setTimeout(function() {
        		$('.alert-success').slideUp(250);
        	}, 5000);
        }
        
        // Close down alert
        $('.close').click(function(e) {
        	$('.alert').slideUp(250);
        })
    </script>
</body>
</html>
