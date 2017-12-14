<%@include file="includes/header.jsp" %>
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
		<c:choose>
			<c:when test="${ currentUser == null }">
				<li><a href="login">Login</a></li>
        		<li><a href="signup">Signup</a></li>
			</c:when>
			<c:otherwise>
				<li>
					<a class="navbar-brand" href="main.html">
        				<img class="profile-pic" alt="${ currentUser.username }" src="img/sai3.jpeg">
        			</a>
        		</li>
        		<li><a href="logout">logout</a></li>
			</c:otherwise>
		</c:choose>
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