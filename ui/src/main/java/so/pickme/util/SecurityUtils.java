package so.pickme.util;

import java.security.Principal;

public class SecurityUtils {
    public static String getLoggedInUserName(Principal principal) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        return username;
    }
}
