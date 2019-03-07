
package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Brotherhood;
import domain.DFloat;
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
	private ActorService			actorService;
	@Autowired
	private FinderService			finderService;

	@Autowired
	private DFloatService			dFloatService;
	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private MessageService			messageService;


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
		//this.deleteDFloatsOfProcession(procession);
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
		final Procession processionBD = this.findOne(procession.getId());
		Assert.isTrue(processionBD.isFfinal() == false, "noFinal");

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

	//	private void deleteDFloatsOfProcession(final Procession procession) {
	//		final List<DFloat> dFloats = this.dFloatService.findDFloatsByProcessionId(procession.getId());
	//
	//		if (!dFloats.isEmpty())
	//			for (final DFloat dFloat : dFloats)
	//				this.dFloatService.delete(dFloat);
	//	}

	public List<Actor> getActorsByProcession(final int processionId) {
		final Collection<Actor> res = new ArrayList<Actor>();
		final Collection<Actor> actors = this.actorService.findAll();
		final Procession proces = this.findOne(processionId);
		for (final Actor a : actors) {
			final List<Procession> pros = this.findProcessionOfMember(a.getId());
			for (final Procession pr : pros)
				if (pros.contains(proces))
					res.add(a);
		}
		return (List<Actor>) res;
	}

	public List<Procession> findProcessionsIn30Days() throws ParseException {
		//creo el formato de fecha para convertirlo luego
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Creo la fecha sumándole directamente un mes y pasándola por el formato y conviritiendola a string para que la parsee a modo fecha
		final Date date = dateFormat.parse(LocalDate.now().plusMonths(1).toString());
		return this.processionRepository.findProcessionsIn30Days(date);
	}

	public List<Procession> findProcessionsForRemove(final DFloat dfloat) {
		this.serviceUtils.checkObject(dfloat);
		return this.processionRepository.findProcessionsForRemove(dfloat.getId());
	}

	public List<Procession> findProcessionsForAdd(final DFloat dfloat) {
		this.serviceUtils.checkObject(dfloat);
		return this.processionRepository.findProcessionsForAdd(dfloat.getId());
	}

}
