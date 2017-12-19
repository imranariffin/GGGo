<%@include file="includes/navbar.jsp" %>
	<h1>Sent Invites by ${ currentUser.username }</h1>
	<ol>
		<c:forEach items="${ sendInvites }" var="inv">
			<li>"${ inv }"</li>
		</c:forEach>
	</ol>
	<h1>Invites Received by ${ currentUser.username }</h1>
	<ol>
		<c:forEach items="${ receivedInvites }" var="inv">
			<li>"${ inv }"</li>
		</c:forEach>
	</ol>
</body>
</html>