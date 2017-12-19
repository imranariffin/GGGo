<%@include file="includes/navbar.jsp" %>
	<div class="jumbotron">
		<div class="container container-center">
			<h1>Welcome to GGGo</h1>
			<p>An interactive platform for playing go</p>
			<p><a class="btn btn-primary btn-lg" href="invites" role="button">Play!</a></p>
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