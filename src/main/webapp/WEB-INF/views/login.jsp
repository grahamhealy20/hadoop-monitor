<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>    
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<!--         <link rel="stylesheet" href="dataurl.css"> -->
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

        <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>    
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
        
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    
		<script src="${pageContext.request.contextPath}/resources/pace.min.js"></script>
        
    </head>
<body id="body">
        <div class="blur">
        
        </div>
        <div class="container body-content">
            <div class="row">
                <div class="col-md-6 col-md-push-3">
                    <div class="loginScreen">
                        <div class="header">
                            <img src="${pageContext.request.contextPath}/resources/images/ibmlogo2.png" width="300" class="img-responsive">
                            <h1 class="text-center">Admin Login</h1>
                        </div>
                        <div class="loginSection">
                        
                        	<c:url value="/login" var="loginUrl"/>
                            <form class="loginForm" action="${loginUrl}" method="post">
                            	<c:if test="${param.error != null}">
									<p>
										Invalid username and password.
									</p>
								</c:if>
								<c:if test="${param.logout != null}">
									<p>
										You have been logged out.
									</p>
								</c:if>
                            
                            <div class="form-group">
                                <label for="username">Email address</label>
                                <input type="email" class="form-control" id="username" name="username" placeholder="Email" required>
                            </div>
                                
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                            </div>
                            
                            <input type="hidden"                        
							name="${_csrf.parameterName}"
							value="${_csrf.token}"/>
                            
                            <button type="submit" class="btn btn-block btn-primary">Sign In</button>
                            <button class="btn btn-block btn-link forgotPassword">Forgot Password</button> 
                            </form>
                        </div>

                    </div>
                    <div class="forgotPasswordSection">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label>
                            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
                        </div>
                        <button type="submit" class="btn btn-block btn-danger">Reset Password</button>
                    </div>
                </div>
            
            </div>
            <div class="row">
                <h4 class="about">By Graham Healy</h4>
            </div>
        </div>
    </body>
    
    <script>
        $(".loginForm").validate();
        
        var toggled = 0;
    
        $('.forgotPassword').click(function(e){
            e.preventDefault();
            var btn = $('.forgotPassword');
            $('.forgotPasswordSection').slideToggle(100);
            $('.forgotPassword').toggleClass('active');
            toggled = !toggled;
            if(toggled == 1) {
                btn.text("Cancel");
            } else {
                btn.text("Reset Password");
            }
            
        })
        
    </script>
    
    <style>
         #body {
             position: relative;
             background: url(/HadoopMon/resources/images/background.jpg) no-repeat;
             background-attachment: fixed;
             background-repeat: no-repeat;
             background-size: cover;
             height: 100vh;
         } 
        
         .blur { 
             position: absolute; 
             background: url(/HadoopMon/resources/images/background.jpg) no-repeat;
             background-attachment: fixed;
             background-repeat: no-repeat;
             background-size: cover;
             height: 100%;
             width: 100%;
            
             /* Blur */
            -webkit-filter: blur(10px);
             -moz-filter: blur(10px);
             -o-filter: blur(10px);
             -ms-filter: blur(10px);
             filter: blur(10px);
         }
        
        h1 {
            color: white;
            text-shadow: 0px 0px 5px rgba(0, 0, 0, 0.5);
        }
        
        .forgotPasswordSection {
            margin-top: -10px;
            background-color: #F0F0F0;
            padding: 15px;
            padding-top: 20px;
            display: none;
            box-shadow: 0px 0px 5px rgba(0, 0, 20, 0.5);
            border-bottom-left-radius: 5px;
            border-bottom-right-radius: 5px;
        }
        
        .header img {
            margin: auto;
            
        }
        
        .loginScreen {
            margin-top: 100px;
            position: relative;
            animation: fadeDown 0.5s;
        }
        
        .loginSection {
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0px 0px 5px rgba(0, 0, 30, 0.7);
            background-color: white;
        }
        
        .about {
            color: white;
            position: fixed;
            bottom: 15px;
            right: 15px;
        }
        
        label.error {
            color: red;
        }
        
        @-webkit-keyframes fadeDown {
            from {
                margin-top: -200px;
                opacity: 0;
            }
            to {
                margin-top: 100px;
                opacity: 1;
            }
        } 
    </style>

</html>