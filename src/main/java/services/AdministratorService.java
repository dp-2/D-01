
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	//Repository--------------------------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	//Metodos-----------------------------------------------------------------------


	//Otros-------------------------------------------------------------------------

	public boolean isPrincipalAdmin() {
		boolean res = false;
		final UserAccount u = LoginService.getPrincipal();
		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		if (u.getAuthorities().contains(a))
			res = true;
		return res;
	}
}
