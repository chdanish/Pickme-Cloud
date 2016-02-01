app.provider('Map', MapProvider);


function MapProvider() {
  var mapType = "GoogleMaps";

  this.setMapType = function(map){
    mapType = map;
  };

  this.$get = function(){
    if(mapType == "GoogleMaps"){
      return new GoogleMapsService();
    } else {
      return null;
    }
  };

}