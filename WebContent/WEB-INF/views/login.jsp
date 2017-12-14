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
				<h1>Login to Your Account</h1><br>
			  <form action="login" method="POST">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="submit" name="login" class="login loginmodal-submit" value="Login">
			  </form>
				
			  <div class="login-help">
				<a href="signup">Signup</a>
			  </div>
			</div>
	</div>
	<div class="footer"></div>
</body>
</html>