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
				<h1>Send invite to ${ invitee }</h1><br>
			  <form action="send-invite" method="POST">
				<input type="text" name="invitee" hidden="true" value="${ invitee }">
				<input type="submit" name="send-invite" class="login loginmodal-submit" value="Send Invite">
			  </form>
			</div>
	</div>
	<div class="footer"></div>
</body>
</html>