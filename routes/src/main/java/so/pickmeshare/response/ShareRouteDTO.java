package so.pickmeshare.response;

import java.util.Date;
import java.util.TimeZone;

public class ShareRouteDTO {
	
	

	long routeID;
	long toID;
	Date tripstarttime; //  (calander date ,time)	
	Date deadlinetocommit; //(calander date ,time)	
	TimeZone timezone;
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
	 * @return the tripstarttime
	 */
	public Date getTripstarttime() {
		return tripstarttime;
	}
	/**
	 * @param tripstarttime the tripstarttime to set
	 */
	public void setTripstarttime(Date tripstarttime) {
		this.tripstarttime = tripstarttime;
	}
	/**
	 * @return the deadlinetocommit
	 */
	public Date getDeadlinetocommit() {
		return deadlinetocommit;
	}
	/**
	 * @param deadlinetocommit the deadlinetocommit to set
	 */
	public void setDeadlinetocommit(Date deadlinetocommit) {
		this.deadlinetocommit = deadlinetocommit;
	}
	/**
	 * @return the timezone
	 */
	public TimeZone getTimezone() {
		return timezone;
	}
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShareRouteDTO [routeID=" + routeID + ", toID=" + toID
				+ ", tripstarttime=" + tripstarttime + ", deadlinetocommit="
				+ deadlinetocommit + ", timezone=" + timezone + "]";
	}
	
	
	
	

}
