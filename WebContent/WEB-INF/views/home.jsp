<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>GGGo</title>
	<meta charset="utf-8"/>
	<title>GGGo</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<!-- material bootstrap -->
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.3/css/mdb.min.css">

	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/go.js"></script>
</head>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">
	        <img class="logo" alt="GGGo" src="img/go-symbol-logo.jpg">
	      </a>
	    </div>
	    <a class="navbar-brand" href="home">
        <img alt="GGGo">
      </a>
			<ul class="nav navbar-nav navbar-right">
        <li><a href="login">Login</a></li>
        <li><a href="signup">Signup</a></li>
        <li>
        	<a class="navbar-brand" href="main.html">
	        	<img class="profile-pic" alt="imranariffin" src="img/sai3.jpeg">
	      	</a>
	      </li>
       </ul>
	  </div>
	</nav>
	<div class="jumbotron">
		<div class="container container-center">
			<h1>Welcome to GGGo</h1>
			<p>An interactive platform for playing go</p>
			<p><a class="btn btn-primary btn-lg" href="play" role="button">Play!</a></p>
		</div>
	</div>
	<div class="main">
		<div class="panel panel-default">
		  <!-- Default panel contents -->
		  <div class="panel-heading">
		  	<div class="row">
		  		<div class="col-md-6">
		  			<span>Finished games</span>
		  		</div>
		  		<div class="col-md-6">
		  			<div class="input-group">
				      <span class="input-group-btn">
				        <button class="btn btn-default" type="button">Go!</button>
				      </span>
				      <input type="text" class="form-control" placeholder="Search for...">
				    </div>
		  		</div>
		  	</div>
		  </div>
		  <!-- Table -->
		  <table class="table">
		  	<tr>
		  		<th>Date</th>
		  		<th>Black</th>
		  		<th>White</th>
		  		<th>Result</th>
		  	</tr>
	    	<tr>
	    		<td>4/12/2017</td>
	    		<td><a href="invite">user1</a></td>
	    		<td><a href="invite">user93</a></td>
	    		<td>W+R</td>
	    	</tr>
	    	<tr>
	    		<td>4/11/2017</td>
	    		<td><a href="invite">user1123</a></td>
	    		<td><a href="invite">user93</a></td>
	    		<td>B+R</td>
	    	</tr>
	    	<tr>
	    		<td>4/10/2017</td>
	    		<td><a href="invite">opuser</a></td>
	    		<td><a href="invite">saifujiwara</a></td>
	    		<td>W+R</td>
	    	</tr>
	    	<tr>
	    		<td>4/9/2017</td>
	    		<td><a href="invite">yongha</a></td>
	    		<td><a href="invite">hikarushindou</a></td>
	    		<td>B+0.5</td>
	    	</tr>
		  </table>
		</div>
	</div>
	<div class="footer"></div>
</body>