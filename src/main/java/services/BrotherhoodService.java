
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Brotherhood;
import repositories.BrotherhoodRepository;

@Service
@Transactional
public class BrotherhoodService {

	//Repository---------------------------------------------------------------

	@Autowired
	private BrotherhoodRepository brotherhoodRepository;


	//Methods------------------------------------------------------------------

	public Brotherhood findBrotherhoodByUserAcountId(final int userAccountId) {
		return this.brotherhoodRepository.findBrotherhoodByUserAcountId(userAccountId);
	}
}
