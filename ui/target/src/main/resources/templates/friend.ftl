
<!-- Authenticated Navbar  not compiled-->

<div class="content  mytitlediv" >  
  <p> <h1 style="color: #ffd119; " ><strong>Friends</strong></h1></p>
  <!-- Collapse dir -->
<div class="content mycolapse" ng-repeat="Object in results.Status">  
  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
  			<button type="button" class="btn btn-default" ng-click="" >Veiw Profile</button>
    		<button type="button" class="btn btn-default" ng-click="delx(Object.userID)" >Delete Friend</button>
			<br></br>
			
  </collapse>
</div>
<!-- Collapse dir -->
</div>
