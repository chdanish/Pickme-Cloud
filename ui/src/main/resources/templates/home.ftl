
<div class="content relative  mytitlediv" draggable="false">  
 
		<div class="contentabsolute" > 
		 <p> <h1 style="color: #ffd119; " ><strong>Hello <#if model["Username"]??>${model["Username"]}<#else>User</#if></strong></h1> </p><h1>Greeting! Have a nice day</h1>
		</div>
<div class="leftabsolute">Online users list</div>
<div class="contentabsolute">Right check

		 <div class="content mycolapse" > 
		  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
		  			<button type="button" class="btn btn-default" ng-if="!Object.friendstatus" ng-click="sendfriendreq(Object.userID)" >Add Friend</button>
		    		<button type="button" class="btn btn-default" ng-if="Object.friendstatus" ng-click="" >View Profile</button>
					<br></br>
					
		  </collapse>
		</div>
		 <div class="content mycolapse" > 
		  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
		  			<button type="button" class="btn btn-default" ng-if="!Object.friendstatus" ng-click="sendfriendreq(Object.userID)" >Add Friend</button>
		    		<button type="button" class="btn btn-default" ng-if="Object.friendstatus" ng-click="" >View Profile</button>
					<br></br>
					
		  </collapse>
		</div>



</div>




</div>                                                                                                                                                                                                           