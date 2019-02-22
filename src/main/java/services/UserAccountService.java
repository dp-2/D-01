
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	//------------Constructors-----------------------
	public UserAccountService() {
		super();
	}

	public UserAccount create(final String s) {
		UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(s);
		userAccount.addAuthority(authority);
		userAccount = this.userAccountRepository.save(userAccount);
		return userAccount;
	}

}
