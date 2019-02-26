
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Brotherhood;
import domain.DFloat;
import domain.Finder;
import domain.Procession;
import forms.ProcessionForm;
import repositories.ProcessionRepository;
import security.LoginService;

@Service
@Transactional
public class ProcessionService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private ProcessionRepository	processionRepository;

	//Service--------------------------------------------------------------------

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private DFloatService			dFloatService;

	//	@Autowired
	//	private Validator				validator;


	//Methods--------------------------------------------------------------------

	public Procession create() {
		final Procession procession = new Procession();
		final String ticker = "ticker";
		final Brotherhood brotherhood = this.brotherhoodService.findBrotherhoodByUserAcountId(LoginService.getPrincipal().getId());

		procession.setBrotherhood(brotherhood);
		procession.setFfinal(false);
		procession.setTicker(ticker);

		return procession;

	}

	public List<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession findOne(final Integer id) {
		return this.processionRepository.findOne(id);
	}

	public Procession save(final Procession procession) {
		Assert.notNull(procession);
		this.checkMoment(procession);

		if (procession.getId() != 0) {
			this.checkPrincipal(procession);
			this.checkNoFinalMode(procession);
		} else
			procession.setTicker(this.configurationService.isUniqueTicker(procession));

		final Procession saved = this.processionRepository.save(procession);
		return saved;
	}

	public void delete(final Procession procession) {
		this.checkPrincipal(procession);
		this.checkNoFinalMode(procession);
		this.deleteDFloatsOfProcession(procession);
		this.processionRepository.delete(procession);
	}

	//Other Methods---------------------------------------------------------------

	private boolean checkPrincipal(final Procession procession) {
		final Brotherhood brotherhood = procession.getBrotherhood();
		final Brotherhood principal = this.brotherhoodService.findBrotherhoodByUserAcountId(LoginService.getPrincipal().getId());

		Assert.isTrue(brotherhood.getId() == principal.getId(), "noOwner");

		return true;
	}

	private boolean checkNoFinalMode(final Procession procession) {
		Assert.isTrue(procession.isFfinal() == false, "noFinal");

		return true;
	}

	private boolean checkMoment(final Procession procession) {
		final Date now = new Date();
		Assert.isTrue(now.before(procession.getMomentOrganised()), "noFuture");
		return true;
	}

	public Procession reconstructToProceesion(final ProcessionForm processionForm, final BindingResult bindingResult) {
		Procession procession;
		final Brotherhood brotherhood = this.brotherhoodService.findOne(processionForm.getBrotherhoodId());

		if (processionForm.getId() == 0)
			procession = this.create();
		else {
			procession = this.findOne(processionForm.getId());
			procession.setTicker(processionForm.getTicker());
			procession.setTitle(processionForm.getTitle());
			procession.setDescription(processionForm.getDescription());
			procession.setFfinal(processionForm.isFfinal());
			procession.setBrotherhood(brotherhood);
			if (processionForm.getFinderId() != 0) {
				final Finder finder = this.finderService.findOne(processionForm.getFinderId());
				procession.setFinder(finder);
			}

			//			this.validator.validate(procession, bindingResult);
		}

		return procession;

	}

	public List<Procession> findProcessionsFinal() {
		return this.processionRepository.findProcessionsFinal();
	}

	public List<Procession> findProcessionsFinalByBrotherhoodId(final int brotherhoodId) {
		return this.processionRepository.findProcessionsFinalByBrotherhoodId(brotherhoodId);
	}

	public List<Procession> findProcessionsByBrotherhoodId(final int brotherhoodId) {
		return this.processionRepository.findProcessionsByBrotherhoodId(brotherhoodId);
	}

	public List<Procession> findProcessionOfMember(final int memberId) {
		return this.processionRepository.findProcessionOfMember(memberId);
	}

	private void deleteDFloatsOfProcession(final Procession procession) {
		final List<DFloat> dFloats = this.dFloatService.findDFloatsByProcessionId(procession.getId());

		if (!dFloats.isEmpty())
			for (final DFloat dFloat : dFloats)
				this.dFloatService.delete(dFloat);
	}
}
