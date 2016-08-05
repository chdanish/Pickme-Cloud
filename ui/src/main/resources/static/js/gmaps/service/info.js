"use strict";
app.service('infoService', infoService);


function infoService() {
	
	var distance,duration,encodedpath;
	
	return {
		encodedpath:encodedpath,
		distance:distance,
		duration:duration,		
	}
}