<%@include file="includes/navbar.jsp" %>
	<div class="main">
		<h1>Invites</h1>
		<div class="panel panel-default">
		  <div class="panel-heading">
		  	<div class="row">
		  		<div class="col-md-6">
		  			<span>Sent Invites:</span>
		  		</div>
		  	</div>
		  </div>
		  <!-- Table -->
		  <table class="table" id="online-users-table">
		  	<tr>
		  		<th>Username</th>
		  		<th>Board size</th>
		  		<th>Rank</th>
		  	</tr>
		  	<c:forEach items="${ sentInvites }" var="inv">
				<tr>
					<td>"${ inv.invitee.username }"</td>
					<td>19x19</td>
					<td>25 kyu</td>
				</tr>
			</c:forEach>
		  </table>
		</div>
		
		<div class="panel panel-default">
		  <div class="panel-heading">
		  	<div class="row">
		  		<div class="col-md-6">
		  			<span>Invites Received:</span>
		  		</div>
		  	</div>
		  </div>
		  <!-- Table -->
		  <table class="table" id="online-users-table">
		  	<tr>
		  		<th>Username</th>
		  		<th>Board size</th>
		  		<th>Rank</th>
		  		<th>Action</th>
		  	</tr>
		  	<c:forEach items="${ receivedInvites }" var="inv">
				<tr>
					<td>"${ inv.invitor.username }"</td>
					<td>19x19</td>
					<td>25 kyu</td>
					<td>
						"${ inv.inviteId }"
						<form action="invite/accept" method="POST" style="margin: 0; padding: 0; display:inline;">
							<input type="text" name="inviteId" value="${ inv.inviteId }" hidden="true">
							<input type="submit" value="Accept" style="display:inline;" class="btn btn-default">
						</form> / 
						<form action="invite/reject" method="POST" style="margin: 0; padding: 0; display:inline;">
							<input type="text" name="inviteId" value="${ inv.inviteId }" hidden="true">
							<input type="submit" value="Reject" style="display:inline;" class="btn btn-default">
						</form>
					</td>
				</tr>
			</c:forEach>
		  </table>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>