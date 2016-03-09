<!-- Authenticated Navbar  not compiled-->
<div  data-role="navbar" ng-show="authenticated" >
</div><!-- /navbar -->

<!-- Collapse dir -->
<div class="content" ng-repeat="myroute in myroutes" style="background-color: #eeeeee; top-padding=5px; height: 100%; border-right: solid 1px #e1e1e1;">  
  <collapse title="{{myroute.title}}">
    <p>{{myroute.discription}}</p>
    <p>Starting Address: {{myroute.startpointaddress}}</p>
    <p>Destination Address: {{myroute.destinationpointaddress}}</p>
    		<button type="button" class="btn btn-default" >Edit</button>
			<button type="button" class="btn btn-default" ng-click="deleteroute(myroute.id)" >Delete</button>
			<button type="button" class="btn btn-default" >Share</button><br></br>
			
  </collapse>
</div>
<!-- Collapse dir -->


<!-- <div ng-show="authenticated" style="background-color: #eeeeee; width: 250px; height: 100%; border-right: solid 1px #e1e1e1;">
<h1>Route Testing</h1>
	<p>The ID is {{routedata.id}}</p>
	<p>The content is {{routedata.content}}</p>
</div>
<div  ng-show="!authenticated">
	<p>Login to see your greeting</p>
</div>

 -->

    <!-- Save Modal -->
    
  <div class="modal fade" ng-controller="route"  id="mySaveModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-body">
          <h1>Please fill below data to save route</h1>
          <form class="form-horizontal" name="mySaveForm" novalidate>
          
            <div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Tittle:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.tName" ng-disabled="!edit" placeholder="Route Name" ng-required>
			    </div>
			</div>
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Start address:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.stpName"  ng-disabled="!edit" placeholder="description">
			    </div>
			</div>
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Destination address:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.dstpName"  ng-disabled="!edit" placeholder="description">
			    </div>
			</div>
			
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Description:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.dName"  ng-disabled="!edit" placeholder="description">
			    </div>
			</div> 
          
          </form>
     
        </div>
         <div class="modal-footer">
			<button type="button" class="btn btn-default" marker-watch ng-disabled="disable()" data-dismiss="modal"  ng-click="save()">Save</button>
			<button type="button" class="btn btn-default" data-dismiss="modal"  data-toggle="modal" href="#myMapModal">Edit route</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
      </div>
      
    </div>
  </div>

  <!-- Modal Ends Here -->
  
