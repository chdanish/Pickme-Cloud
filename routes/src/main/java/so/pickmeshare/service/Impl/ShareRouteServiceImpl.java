package so.pickmeshare.service.Impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.util.TimeUtil;
import so.pickme.replica.domain.Pickmeup;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.PickmeupRepository;
import so.pickmeshare.response.ShareRouteDTO;
import so.pickmeshare.service.*;
import so.pickmeshare.utils.Pickupreqstatus;

@Service
public class ShareRouteServiceImpl implements  ShareRouteService{
	
	@Autowired
	private PickmeupRepository pickmeupRepository;
	
	TimeZone tz = TimeZone.getDefault();
	final Calendar calendar = Calendar.getInstance();

	@Override
	public void sharemyroute(ShareRouteDTO sdto, User activeUser, User toUser, Route route) throws ParseException {
		
		Pickmeup pmu= new Pickmeup();
		int zoneoffset = (Integer.parseInt(sdto.getTimezone())/60) ;

		pmu.setFrom(activeUser);
		pmu.setRoute(route);
		pmu.setTripstarttime(getDate(sdto.getStartTripDateTime()));
		pmu.setDeadlinetocommit(getDate(sdto.getCommitDeadlineDatetime()));
		pmu.setTripstarttime_long(sdto.getStartTripDateTime());
		pmu.setDeadlinetocommit_long(sdto.getCommitDeadlineDatetime());
		pmu.setTimezone(TimeZone.getTimeZone("Etc/GMT"+getStrOffset(zoneoffset)));
		pmu.pickmerequestto(toUser);
		pmu.setStatus(Pickupreqstatus.ACTIVE);
		System.out.println("pickme object"+pmu);
		pickmeupRepository.save(pmu);
		
	}
	
	public Date getDate(Long millis){
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTimeInMillis(millis);
		System.out.println("GMT time is: "+cal.getTime());
		return cal.getTime();
	}
	
	public String getStrOffset(int offset){
		String str ="";
		if (offset<0){
			return ""+offset;
			
		} else if (offset>0){
			str = "+" + offset;
			return str;
			
		} else if (offset==0){
			return "";
		}
		return null;
	}
	
}
