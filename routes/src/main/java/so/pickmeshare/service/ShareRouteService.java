package so.pickmeshare.service;

import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickmeshare.response.ShareRouteDTO;

public interface ShareRouteService {

	void sharemyroute(ShareRouteDTO sdto, User activeUser, User toUser,
			Route route);

}
