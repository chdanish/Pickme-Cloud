<!-- Authenticated Navbar  not compiled-->
<div class="content  mytitlediv"  >  
      <p> <h1 style="color: #ffd119; " ><strong>Find a friend</strong></h1></p>
  <div>
  Find by:<select ng-model='selectedValue' ng-options="x for x in names">
</select>

  <input type="text" name="fname" ng-model="textvalue" autofocus>
  <button type="button" class="btn btn-default" ng-click="finduser()" >find</button>
  </div>
  <p></p>
  <!-- <p ng-repeat="Object in results">{{Object.fname}}" "{{Object.lname}}</p> -->
  <!-- Collapse dir -->
<div class="content mycolapse" ng-repeat="Object in results" >   
  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
    		<button type="button" class="btn btn-default" ng-if="!Object.friendstatus" ng-click="addfriendreq(Object.id)" >Add Friend</button>
    		<button type="button" class="btn btn-default" ng-if="Object.friendstatus"  ng-click="addfriendreq()" >Veiw Profile</button>
			<br></br>
  </collapse>
</div>
<!-- Collapse dir -->
</div>
