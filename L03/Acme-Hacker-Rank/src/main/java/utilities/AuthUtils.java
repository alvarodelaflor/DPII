
package utilities;

import security.Authority;
import security.LoginService;

public class AuthUtils {

	/**
	 * Checks logged user authority
	 * 
	 * @param authority
	 * @return true if the logged user has the specified authority, false otherwise
	 */
	public static boolean checkLoggedAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);

		boolean res;
		try {
			res = LoginService.getPrincipal().getAuthorities().contains(au);
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

}
