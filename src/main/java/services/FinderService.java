
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Member;
import domain.Procession;
import repositories.FinderRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class FinderService {

	//Repository---------------------------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	//Service------------------------------------------------------------------

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private ServiceUtils			serviceUtils;


	//Methods-------------------------------------------------------------------

	public Finder create() {
		final Finder finder = new Finder();
		final Member member = this.memberService.findMemberByUserAcountId(LoginService.getPrincipal().getId());
		final Date lastUpdate = new Date();
		final List<Procession> processions = new ArrayList<>();

		finder.setMember(member);
		finder.setLastUpdate(lastUpdate);
		finder.setProcessions(processions);

		return finder;
	}

	public Finder crudSave(final Finder finder) {
		Assert.notNull(finder);
		return this.finderRepository.save(finder);
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		if (finder.getMinDate() != null && finder.getMaxDate() != null)
			Assert.isTrue(finder.getMinDate().before(finder.getMaxDate()), "dateError");
		Assert.isTrue(this.checkPrincipal(finder));
		if (finder.getVersion() > 0) {
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
			final List<Procession> processions = this.findProcessionByFinder(finder);
			finder.setProcessions(processions);
		}
		return this.finderRepository.save(finder);
	}

	public Finder findOneByPrincipal() {
		final Member member = this.memberService.findMemberByUserAcountId(LoginService.getPrincipal().getId());
		return this.finderRepository.findFinderByMemberId(member.getId());
	}

	//Other------------------------------------------------------------------------------------------------

	private boolean checkPrincipal(final Finder finder) {
		final Member member = finder.getMember();
		final Member principal = this.memberService.findMemberByUserAcountId(LoginService.getPrincipal().getId());

		Assert.isTrue(member.getId() == principal.getId());

		return true;
	}

	public List<Procession> searchProcessions(final String keyword, final Date dateMin, final Date dateMax) {
		final List<Procession> res = new ArrayList<>();

		final List<Procession> processions = this.processionService.findProcessionsFinal();
		for (final Procession procession : processions)
			if ((procession.getTitle().toLowerCase().contains(keyword.toLowerCase()) || procession.getDescription().toLowerCase().contains(keyword.toLowerCase())) && procession.getMomentOrganised().after(dateMin)
				&& procession.getMomentOrganised().before(dateMax))
				res.add(procession);
		return res;
	}

	public Finder findFinderByMemberId(final int memberId) {
		return this.finderRepository.findFinderByMemberId(memberId);
	}

	public List<Procession> updateCache(final Finder finder) {
		final Integer timeCache = this.configurationService.findOne().getCacheFinder();
		final Date dnow = new Date();

		List<Procession> processions = new ArrayList<>();

		if (finder.getLastUpdate() == null || dnow.getTime() - finder.getLastUpdate().getTime() > (timeCache * 3600000)) {
			processions = this.findProcessionByFinder(finder);
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
			finder.setProcessions(processions);
			this.save(finder);
		} else
			processions = finder.getProcessions();

		return processions;

	}

	public List<Procession> findProcessionByFinder(final Finder f) {
		final Finder finder = new Finder();
		finder.setMaxDate(f.getMaxDate());
		finder.setMinDate(f.getMinDate());
		finder.setKeyword(f.getKeyword());
		finder.setArea(f.getArea());
		List<Procession> res = new ArrayList<>();

		if (finder.getMaxDate() == null) {
			final Date dmax = new Date();
			dmax.setYear(dmax.getYear() + 100);
			finder.setMaxDate(dmax);
		}

		if (finder.getMinDate() == null) {
			final Date dmin = new Date();
			dmin.setYear(dmin.getYear() - 100);
			finder.setMinDate(dmin);
		}

		if (finder.getKeyword() == null)
			finder.setKeyword("");

		if (finder.getArea() == null)
			res = this.searchProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate());
		else {
			final List<Procession> processions = this.searchProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate());
			for (final Procession procession : processions)
				if (finder.getArea().getBrotherhood().getId() == procession.getBrotherhood().getId())
					res.add(procession);
		}

		if (f.getArea() == null && f.getMinDate() == null && f.getMaxDate() == null && f.getMaxDate() == null)
			res = this.processionService.findProcessionsFinal();

		return res;
	}

	public List<Double> finderStats() {
		// 1er dato mínimo
		// 2o dato máximo
		// 3er dato media
		// 4o dato desviación estándar
		// 5º Empty vs not empty finders
		final Double minResultsInFinder = this.finderRepository.minResultsInFinder();
		final Double maxResultsInFinder = this.finderRepository.maxResultsInFinder();
		final Double avgResultsInFinder = this.finderRepository.avgResultsInFinder();
		final Double stdResultsInFinder = this.finderRepository.stdResultsInFinder();
		final Double emptyVSNonEmptyFinder = this.finderRepository.emptyVSNonEmptyFinder();

		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final List<Double> res = new ArrayList<>();
		res.add(minResultsInFinder);
		res.add(maxResultsInFinder);
		res.add(avgResultsInFinder);
		res.add(stdResultsInFinder);
		res.add(emptyVSNonEmptyFinder);
		return res;
	}
}
