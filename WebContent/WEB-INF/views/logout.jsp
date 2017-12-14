<%@ include file="includes/header.jsp"%>

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
				<h1>Logout</h1><br>
			  <form action="logout" method="POST">
				<input type="submit" class="login loginmodal-submit" value="Logout">
			  </form>
			</div>
	</div>
	<div class="footer"></div>
</body>
</html>