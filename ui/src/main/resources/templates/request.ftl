<!-- Authenticated Navbar  not compiled-->
<!-- Collapse dir -->
<div class="content  mytitlediv" >  
  <p> <h1 style="color: #ffd119; " ><strong>Friend Requests</strong></h1></p>
</div>
<!-- Collapse dir -->
<div class="content mycolapse" ng-repeat="Object in results.Status" >  
  <collapse class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
    		<button type="button" class="btn btn-default" ng-click="addfriend(Object.userID)" >Add Friend</button>
    		<button type="button" class="btn btn-default" ng-click="delfriendrequest(Object.userID)" >Delete</button>
			<br></br>
			
  </collapse>
</div>
<!-- Collapse dir -->