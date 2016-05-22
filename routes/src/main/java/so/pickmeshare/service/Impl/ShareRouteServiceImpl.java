package so.pickmeshare.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import so.pickme.replica.domain.Pickmeup;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.PickmeupRepository;
import so.pickmeshare.response.ShareRouteDTO;
import so.pickmeshare.service.*;

@Service
public class ShareRouteServiceImpl implements  ShareRouteService{
	
	@Autowired
	private PickmeupRepository pickmeupRepository;

	@Override
	public void sharemyroute(ShareRouteDTO sdto, User activeUser, User toUser, Route route) {
		
		Pickmeup pmu= new Pickmeup();
		pmu.setFrom(activeUser);
		pmu.setRoute(route);
		pmu.setTripstarttime(sdto.getTripstarttime());
		pmu.setDeadlinetocommit(sdto.getDeadlinetocommit());
		pmu.pickmerequestto(toUser);
		pickmeupRepository.save(pmu);
		
	}

	

}
