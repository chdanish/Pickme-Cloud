<!-- Authenticated Navbar  not compiled-->
<div  data-role="navbar" ng-show="authenticated" >
		<ul>
			<li><a href="#">New Route</a></li>
			<li><a href="#">Yahoo</a></li>
		</ul>
</div><!-- /navbar -->
<h1>Route Testing</h1>
<div ng-show="authenticated" style="background-color: #eeeeee; width: 250px; height: 100%; border-right: solid 1px #e1e1e1;">
	<p>The ID is {{routedata.id}}</p>
	<p>The content is {{routedata.content}}</p>
</div>
<div  ng-show="!authenticated">
	<p>Login to see your greeting</p>
</div>
