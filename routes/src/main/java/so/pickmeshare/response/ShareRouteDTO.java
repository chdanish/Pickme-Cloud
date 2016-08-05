package so.pickmeshare.response;

import java.util.Date;
import java.util.TimeZone;

public class ShareRouteDTO {
	
	

	long routeID;
	long toID;
	long startTripDateTime;  // planned trip start time
	long commitDeadlineDatetime; // deadline to commit trip
	long tripEstDur; //trip estimate duration from google
	//Pickupreq Status; // Status  Active, Expire
	String timezone;
	/**
	 * @return the routeID
	 */
	public long getRouteID() {
		return routeID;
	}
	/**
	 * @param routeID the routeID to set
	 */
	public void setRouteID(long routeID) {
		this.routeID = routeID;
	}
	/**
	 * @return the toID
	 */
	public long getToID() {
		return toID;
	}
	/**
	 * @param toID the toID to set
	 */
	public void setToID(long toID) {
		this.toID = toID;
	}
	/**
	 * @return the startTripDateTime
	 */
	public long getStartTripDateTime() {
		return startTripDateTime;
	}
	/**
	 * @param startTripDateTime the startTripDateTime to set
	 */
	public void setStartTripDateTime(long startTripDateTime) {
		this.startTripDateTime = startTripDateTime;
	}
	/**
	 * @return the commitDeadlineDatetime
	 */
	public long getCommitDeadlineDatetime() {
		return commitDeadlineDatetime;
	}
	/**
	 * @param commitDeadlineDatetime the commitDeadlineDatetime to set
	 */
	public void setCommitDeadlineDatetime(long commitDeadlineDatetime) {
		this.commitDeadlineDatetime = commitDeadlineDatetime;
	}
	/**
	 * @return the tripEstDur
	 */
	public long getTripEstDur() {
		return tripEstDur;
	}
	/**
	 * @param tripEstDur the tripEstDur to set
	 */
	public void setTripEstDur(long tripEstDur) {
		this.tripEstDur = tripEstDur;
	}
	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShareRouteDTO [routeID=" + routeID + ", toID=" + toID + ", startTripDateTime=" + startTripDateTime
				+ ", commitDeadlineDatetime=" + commitDeadlineDatetime + ", tripEstDur=" + tripEstDur + ", timezone="
				+ timezone + "]";
	}
	
	
	
}
