<%@include file="includes/header.jsp" %>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="home">
	        <img class="logo" alt="GGGo" src="img/go-symbol-logo.jpg">
	      </a>
	    </div>
	    <a class="navbar-brand" href="home">
        <img alt="GGGo">
      </a>
	  </div>
	</nav>
	<div class="main">
		<div class="container-center container-auth">
			<div class="loginmodal-container">
				<h1>Create an Account</h1><br>
			  <form action="signup" method="POST">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="password" name="confirmPassword" placeholder="Confirm Password">
				<input type="submit" class="login loginmodal-submit" value="Signup">
			  </form>
			  
			  <div class="login-help">
				<a href="login">Login</a>
			  </div>
			</div>
	</div>
	<div class="footer"></div>
</body>
</html>