<!doctype html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/wro/wro.css"/>
<link type="text/css" rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-cookies.min.js"></script>
<script language="javascript">
	$.mobile.hashListeningEnabled = false;
	$.mobile.ajaxLinksEnabled = false;
    $.mobile.ajaxEnabled = false;
   
 
</script>
</head>
<body ng-app="hello" ng-cloak class="ng-cloak"><body>
<#if RequestParameters['error']??>
	<div class="alert alert-danger">
		There was a problem logging in. Please try again.
	</div>
</#if>
	<div ng-controller="navigation as tab" class="container">
		<ul class="nav nav-pills" role="tablist">
			<li ng-class="{active:tab.isSet(1)}"><a href ng-click="tab.setTab(1)">login</a></li>
            <li ng-class="{active:tab.isSet(2)}"><a href ng-click="tab.setTab(2)">SignUp</a></li>
            <li><a href ng-click="facebookauth()">Login with facebook</a></li>
		</ul>
	
		<div ng-show="tab.isSet(1)">
             <form role="form" action="login" method="post" data-ajax="false">
			  <div class="form-group">
			    <label for="username">Username:</label>
			    <input type="text" class="form-control" id="username" name="username"/>
			  </div>
			  <div class="form-group">
			    <label for="password">Password:</label>
			    <input type="password" class="form-control" id="password" name="password"/>
			  </div>
			  <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
			
			
		<!-- 	<form role="form" ng-submit="login()">
				<div class="form-group">
					<label for="username">Username:</label> <input type="text"
						class="form-control" id="username" name="username" ng-model="credentials.username"/>
				</div>
				<div class="form-group">
					<label for="password">Password:</label> <input type="password"
						class="form-control" id="password" name="password" ng-model="credentials.password"/>
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form> -->
			
        </div>
        
        <div ng-show="tab.isSet(2)">
             <h3 ng-show="edit">Signup new account:</h3>
			<h3 ng-hide="edit">Edit User:</h3>
			
			<form class="form-horizontal" name="myForm" novalidate>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">First Name:</label>
			    <div class="col-sm-10">
			    <input type="text" ng-model="fName" ng-disabled="!edit" placeholder="First Name">
			    </div>
			  </div> 
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Last Name:</label>
			    <div class="col-sm-10">
			    <input type="text" ng-model="lName" ng-disabled="!edit" placeholder="Last Name">
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">User Name:</label>
			    <div class="col-sm-10">
			    <input type="text" ng-model="uName" ng-disabled="!edit" placeholder="User Name">
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Email:</label>
			    <div class="col-sm-10">
			    <input type="email" name="email" ng-model="email" placeholder="@email" required>
			    <span style="color:red" ng-show="myForm.email.$dirty && myForm.email.$invalid">
				<span ng-show="myForm.email.$error.required">Email is required.</span>
				<span ng-show="myForm.email.$error.email">Invalid email address.</span>
				</span>
			   </div>
			    
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Password:</label>
			    <div class="col-sm-10">
			    <input type="password" id="pw1" name="pw1"   ng-model="pw1" placeholder="Password" ng-required/>
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Repeat:</label>
			    <div class="col-sm-10">
			    <input type="password" id="pw2" name="pw2" ng-model="pw2" placeholder="Repeat Password" ng-required pw-check="pw1"/>
			    </div>
			    <div class="msg-block" ng-show="myForm.$error">
				  <span style="color:red" class="msg-error" ng-show="myForm.pw2.$error.pwmatch">
				    Passwords don't match.
				  </span>
				</div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Role:</label>
			    <div class="col-sm-10">
			    <input type="text" ng-model="role" placeholder="Role to be added">
			    </div>
			  </div>
			</form>
			
			<hr>
			<button ng-click="submit()" class="btn btn-primary" ng-disabled="error || incomplete">
			<span></span>Submit
			</button>
        </div>

	</div>
<!-- 	<script src="js/wro.js" type="text/javascript"></script>
	<script src="staticjs/angular-bootstrap.js" type="text/javascript"></script> -->
	<script src="staticjs/hello.js"></script>
</body>
</html>