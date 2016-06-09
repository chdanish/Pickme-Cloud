<!-- Authenticated Navbar  not compiled-->
<div  data-role="navbar" ng-show="authenticated" >
</div><!-- /navbar -->
<div class="content mytitlediv"  >  
  
 <p> <h1 style="color: #ffd119; " ><strong>MyRoutes</strong></h1></p>
 
 <!-- Collapse dir -->
<div class="content mycolapse" ng-repeat="myroute in myroutes"  >  
  <collapse class="Titan-One" title="{{myroute.title}}">
   
  <div class="row">
	  <div class="col-md-8 col-xs-8 col-sm-8 ">
	  		<p>{{myroute.discription}}</p>
		    <p>Starting Address: {{myroute.startpointaddress}}</p>
		    <p>Destination Address: {{myroute.destinationpointaddress}}</p>
    		<button type="button" class="btn btn-default" >Edit</button>
			<button type="button" class="btn btn-default" ng-click="deleteroute(myroute.id)" >Delete</button>
			<button type="button" class="btn btn-default" data-dismiss="modal"  data-toggle="modal" ng-click="setsharerouteID(myroute.id)" href="#myRouteShareModal" >Share</button><br></br>
	  		<span></span>
	  </div>
	  <div class="col-md-4 col-xs-4 col-sm-4">
	  		<!-- <img alt="Test map" src="https://maps.googleapis.com/maps/api/staticmap?size=200x200&path=weight:3%7Ccolor:blue%7geodesic:%7Cenc:g`}dHkoneAwj@f~@_Na@{DBcVuDiSaa@tkGs_MpEhA|EtAqMoD{HtC&key=AIzaSyBGRivLqFvv2O8SOCDbXgs2o4YaRlzIZOw"> -->
	  		  <map-th mapid="{{myroute.id}}" dlat="{{myroute.destpointLAT}}" dlong="{{myroute.destpointLONG}}" slat="{{myroute.startpointLAT}}" slong="{{myroute.startpointLONG}}"></map-th>
	  </div>
  </div>
						
  </collapse>
</div>
<!-- Collapse dir -->
</div>




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
          <form class="form-horizontal" name="mySaveForm" validate>
          
            <div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Tittle:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" name="routename" ng-model="saverouteDTO.tName" ng-disabled="!edit" placeholder="Route Name" style="width: inherit;" required>
			    </div>
			</div>
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Start address:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.stpName"  ng-disabled="!edit" placeholder="description" style="width: inherit;">
			    </div>
			</div>
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Destination address:</label>
			    <div class="col-sm-10 col-md-4">
			    <input type="text" ng-model="saverouteDTO.dstpName"  ng-disabled="!edit" placeholder="description" style="width: inherit;">
			    </div>
			</div>
			
			<div class="form-group">
			    <label class="col-sm-2 col-md-4 control-label">Description:</label>
			    <div class="col-sm-10 col-md-4">
			    <textarea type="text" ng-model="saverouteDTO.dName"  ng-disabled="!edit" placeholder="description" style="width: inherit;"></textarea>
			    </div>
			</div> 
			<div class="im-centered">
			  <div class="row">
			  	<div class="form-group">
				    <label class="col-sm-2 col-md-4 control-label">Duration:</label>
				    <div class="col-sm-10 col-md-4">
				    <duration-picker class="col-md-4" duration="{{saverouteDTO.duration}}" days="durationPicker.days" hours="durationPicker.hours" mins="durationPicker.mins" secs="durationPicker.secs"></duration-picker>
			    	</div>
				</div>
				<div class="form-group">
				    <label class="col-sm-2 col-md-4 control-label">Distance(m):</label>
				    <div class="col-sm-10 col-md-4">
				    <textarea name="distance" rows="1" cols="4" readonly="readonly" required>{{saverouteDTO.distance}}</textarea>
			    	</div>
				</div>  
			  </div>
			</div>
          
          </form>
     
        </div>
         <div class="modal-footer">
         	<button type="button" class="btn btn-default" ng-disabled="!mySaveForm.routename.$valid " data-dismiss="modal"  ng-click="save()">Save</button>
			<!-- <button type="button" class="btn btn-default" ng-disabled="!mySaveForm.routename.$valid || !mySaveForm.duration.$valid" data-dismiss="modal"  ng-click="save()">Save</button> -->
			<button type="button" class="btn btn-default" data-dismiss="modal"  data-toggle="modal" href="#myMapModal">Edit route</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
      </div>
      
    </div>
  </div>

  <!-- Modal Ends Here -->
  
  <!-- Route Share Modal -->
  <div class="modal fade" id="myRouteShareModal" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header mytitlediv" ">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <a class="navbar-brand" ><span><strong style="color: #ffd119; font-size: 3em;">pickmeUP</strong></span></a>
        </div>
        <div class="modal-body">
         <!-- datetime picker -->
              <div class="row">
              <div class="col-md-4">
              <div class="row alert">
         		Date: <clock format="yyyy-MM-dd"></clock>
         		Time: <clock format="HH:mm:ss Z"></clock>
         	  </div>
         		<div class="row alert">** Select trip start time atleast 1 hour from currnet time.</div>
              </div>
				      <div class="col-md-4">
				        <form name="mystartForm" >
						    <label for="exampleInput">Slecet trip start  time:</label>
						    <input type="datetime-local" id="exampleInput" name="input" ng-model="start.value" placeholder="yyyy-MM-ddTHH:mm:ss" min="{{mintime}}" " required />
						    <div role="alert">
						      <span class="error" ng-show="mystartForm.input.$error.required">
						
						        Required!</span>
						      <span class="error" ng-show="mystartForm.input.$error.datetimelocal">
						
						        Not a valid date!</span>
						    </div>
						  </form>
				      </div>
				
				      <div class="col-md-4">
				      	<form name="myendForm" >
						    <label for="exampleInput">Select deadline to commit trip:</label>
						    <input type="datetime-local" id="exampleInput" name="input" ng-model="end.value" placeholder="yyyy-MM-ddTHH:mm:ss" min="{{mintime}}" max="{{start.value}}" required />
						    <div role="alert">
						      <span class="error" ng-show="myendForm.input.$error.required">
						
						        Required!</span>
						      <span class="error" ng-show="myendForm.input.$error.datetimelocal">
						
						        Not a valid date!</span>
						    </div>
						  </form>
				        
				      </div>
				    </div>
          <!-- datetime picker  -->
        
          <p>Find:<input type="text" ng-model="test"></p>
          <div ng-repeat="frnd in friendlist | orderBy:'fname' | filter : test">
          <hr></hr>
          <p>{{frnd.fname}}</p>
          
        		
        		<div class="row">
        			<div class="col-md-8">  <p>{{frnd.fname}}{{frnd.userID}}</p> </div>
        			<div class="col-md-4">  <button type="button" class="btn btn-default" ng-disabled="!myendForm.input.$valid || !mystartForm.input.$valid " ng-click="pickmereq(frnd.userID)">Request pickmeUP</button> </div>
        		
        		</div>
         
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <!-- Route Share Modal Ends here -->
  
