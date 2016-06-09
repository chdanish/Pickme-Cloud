package so.pickmeshare.service;

import java.text.ParseException;
import java.util.Date;

import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickmeshare.response.ShareRouteDTO;

public interface ShareRouteService {

	void sharemyroute(ShareRouteDTO sdto, User activeUser, User toUser,
			Route route) throws ParseException;


}
