
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarningRepository;
import domain.Warning;

@Service
@Transactional
public class WarningService {

	//------------Service-------------------------------------------------
	@Autowired
	private WarningRepository	warningRepository;


	public Collection<Warning> findAll() {
		return this.warningRepository.findAll();
	}

	public Warning findOne(final int warningId) {
		return this.warningRepository.findOne(warningId);
	}

	public Warning save(final Warning warning) {
		Warning res = null;
		Assert.notNull(warning);

		res = this.warningRepository.save(warning);
		return res;

	}

	public Warning setWarningTrue() {
		Warning res = null;
		res = this.warningRepository.findAll().get(0);
		Assert.notNull(res);
		res.setIsWarning(true);

		return res;

	}

	public Warning setWarningFalse() {
		Warning res = null;
		res = this.warningRepository.findAll().get(0);
		Assert.notNull(res);
		res.setIsWarning(false);
		this.save(res);
		return res;

	}
	public Warning giveWarning() {
		Warning res = null;
		res = this.warningRepository.findAll().get(0);
		return res;
	}

}
