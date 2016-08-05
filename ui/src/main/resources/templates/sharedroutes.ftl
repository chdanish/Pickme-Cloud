<div class="content  mytitlediv" draggable="false">  
  <p> <h1 style="color: #ffd119; " ><strong>Shared Routes</strong></h1> </p>
  
 
 
 
 

  <!-- Collapse dir -->
<!--   
  <div class="content mycolapse" ng-repeat="Object in results"> 
  <collapse  class="Titan-One" title="{{Object.fname}} {{Object.lname}}">
  			<button type="button" class="btn btn-default" ng-if="!Object.friendstatus" ng-click="sendfriendreq(Object.userID)" >Add Friend</button>
    		<button type="button" class="btn btn-default" ng-if="Object.friendstatus" ng-click="" >View Profile</button>
			<br></br>
			
  </collapse>
</div> -->

  <!-- Collapse dir -->
</div>

<div class="gridListdemoBasicUsage" ng-repeat="x  in results">
  <md-grid-list
        md-cols-xs="1" md-cols-sm="4" md-cols-md="8" md-cols-gt-md="6"
        md-row-height-gt-md="1:1" md-row-height="2:2"
        md-gutter="12px" md-gutter-gt-sm="8px" >
   <md-grid-tile class="red" md-colspan="8" md-rowspan="2" md-colspan-sm="2" >
	  <md-grid-tile-header >
	    <h3>This is a header</h3>
	  </md-grid-tile-header>{{x.id}}
	  <md-grid-tile-footer>
	    <h3>This is a footer</h3>
	  </md-grid-tile-footer>
	</md-grid-tile>
  </md-grid-list>
</div>	
