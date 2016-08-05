<div class="content  mytitlediv" draggable="false">  
  <p> <h1 style="color: #ffd119; " ><strong>Find Friends</strong></h1> </p>
  
   <form name="myForm">
    <select ng-model="selectedValue" ng-options="x for x in names"></select>
    <input name="myInput" type="text" ng-model="myInput" required>
    <button ng-disabled="myForm.myInput.$invalid" ng-click="finduser()">Search</button>
 	<br></br>
    <span ng-show="myForm.myInput.$touched && myForm.myInput.$invalid">The name is required.</span>
  </form>
  
 
  <!-- Collapse dir -->
  
  <div class="content mycolapse" ng-repeat="Object in results"> 
  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
  			<button type="button" class="btn btn-default" ng-if="!Object.friendstatus" ng-click="sendfriendreq(Object.userID)" >Add Friend</button>
    		<button type="button" class="btn btn-default" ng-if="Object.friendstatus" ng-click="" >View Profile</button>
			<br></br>
			
  </collapse>
</div>

  <!-- Collapse dir -->
</div>