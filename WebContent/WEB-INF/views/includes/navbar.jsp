<%@include file="header.jsp" %>
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