
package utilities;

import security.Authority;
import security.LoginService;
import security.UserAccount;

public class CommonUtils {

	/**
	 * Checks if logged actor has the specified authority.
	 * 
	 * @param authority
	 * @return true if the logged actor has the specified authority, false otherwise
	 */
	public static boolean hasAuthority(final String authority) {
		final UserAccount account = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority(authority);
		return account.getAuthorities().contains(au);
	}
}
