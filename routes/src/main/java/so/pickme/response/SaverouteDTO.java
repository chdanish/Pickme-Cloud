package so.pickme.response;

import java.io.Serializable;

public class SaverouteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8265599762784788203L;
	
	String tName;
	String dName;
	String stpName;
	String dstpName;
	String stp_LAT;
	String stp_LNG;
	String dstp_LAT;
	String dstp_LNG;
	
	long duration;
	long distance;
	/**
	 * @return the tName
	 */
	public String gettName() {
		return tName;
	}
	/**
	 * @param tName the tName to set
	 */
	public void settName(String tName) {
		this.tName = tName;
	}
	/**
	 * @return the dName
	 */
	public String getdName() {
		return dName;
	}
	/**
	 * @param dName the dName to set
	 */
	public void setdName(String dName) {
		this.dName = dName;
	}
	/**
	 * @return the stpName
	 */
	public String getStpName() {
		return stpName;
	}
	/**
	 * @param stpName the stpName to set
	 */
	public void setStpName(String stpName) {
		this.stpName = stpName;
	}
	/**
	 * @return the dstpName
	 */
	public String getDstpName() {
		return dstpName;
	}
	/**
	 * @param dstpName the dstpName to set
	 */
	public void setDstpName(String dstpName) {
		this.dstpName = dstpName;
	}
	/**
	 * @return the stp_LAT
	 */
	public String getStp_LAT() {
		return stp_LAT;
	}
	/**
	 * @param stp_LAT the stp_LAT to set
	 */
	public void setStp_LAT(String stp_LAT) {
		this.stp_LAT = stp_LAT;
	}
	/**
	 * @return the stp_LNG
	 */
	public String getStp_LNG() {
		return stp_LNG;
	}
	/**
	 * @param stp_LNG the stp_LNG to set
	 */
	public void setStp_LNG(String stp_LNG) {
		this.stp_LNG = stp_LNG;
	}
	/**
	 * @return the dstp_LAT
	 */
	public String getDstp_LAT() {
		return dstp_LAT;
	}
	/**
	 * @param dstp_LAT the dstp_LAT to set
	 */
	public void setDstp_LAT(String dstp_LAT) {
		this.dstp_LAT = dstp_LAT;
	}
	/**
	 * @return the dstp_LNG
	 */
	public String getDstp_LNG() {
		return dstp_LNG;
	}
	/**
	 * @param dstp_LNG the dstp_LNG to set
	 */
	public void setDstp_LNG(String dstp_LNG) {
		this.dstp_LNG = dstp_LNG;
	}
	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * @return the distance
	 */
	public long getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(long distance) {
		this.distance = distance;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SaverouteDTO [tName=" + tName + ", dName=" + dName + ", stpName=" + stpName + ", dstpName=" + dstpName
				+ ", stp_LAT=" + stp_LAT + ", stp_LNG=" + stp_LNG + ", dstp_LAT=" + dstp_LAT + ", dstp_LNG=" + dstp_LNG
				+ ", duration=" + duration + ", distance=" + distance + "]";
	}

}
