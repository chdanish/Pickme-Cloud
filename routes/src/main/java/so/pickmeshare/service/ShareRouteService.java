package so.pickmeshare.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import so.pickme.replica.domain.Pickmeup;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickmeshare.response.ShareRouteDTO;

public interface ShareRouteService {

	void sharemyroute(ShareRouteDTO sdto, User activeUser, User toUser,
			Route route) throws ParseException;

    Iterable<Map<String, Object>> mysharedroutes(String username);
}
