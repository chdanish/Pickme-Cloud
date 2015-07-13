<!-- Authenticated Navbar -->
<div  data-role="navbar" ng-show="authenticated" >
		<ul>
			<li><a href="#">New Route</a></li>
			<li><a href="#">Yahoo</a></li>
		</ul>
</div><!-- /navbar -->
<h1>Route Testing</h1>
<div ng-show="authenticated">
	<p>The ID is {{routedata.id}}</p>
	<p>The content is {{routedata.content}}</p>
</div>
<div  ng-show="!authenticated">
	<p>Login to see your greeting</p>
</div>
