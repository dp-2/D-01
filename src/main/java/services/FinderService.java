
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Area;
import domain.Configuration;
import domain.Finder;
import domain.Member;
import domain.Procession;
import repositories.FinderRepository;
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

	public Finder save(Finder finder) {
		Assert.notNull(finder);
		finder.setLastUpdate(this.updateTime());
		final List<Procession> processions = this.finalFilter(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getArea());

		if (finder.getId() != 0)
			this.checkPrincipal(finder);

		finder = this.updateFinder(finder);
		finder.setProcessions(processions);

		final Finder saved = this.finderRepository.save(this.updateFinder(finder));
		return saved;
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

	public boolean checkCache(final Finder finder) {
		boolean res = false;
		if (finder != null) {
			final long now = new Date().getTime();
			final long last = finder.getLastUpdate().getTime();
			final long cache = this.configurationService.findOne().getCacheFinder() * 3600000;

			if ((now - last) >= cache)
				res = true;
		}
		return res;
	}

	public List<Procession> searchProcessions(final String keyword, final Date dateMin, final Date dateMax) {
		return this.finderRepository.searchProcessions(keyword, dateMin, dateMax);
	}

	public List<Procession> finalFilter(final String keyword, final Date dateMin, final Date dateMax, final Area area) {
		List<Procession> res = new ArrayList<>();

		if ((!keyword.isEmpty() || dateMax == null || dateMin == null) && area == null)
			res = this.searchProcessions(keyword, dateMin, dateMax);
		else if ((!keyword.isEmpty() || dateMax == null || dateMin == null) && area != null)
			for (final Procession procession : res)
				if (area.getBrotherhood().getId() == procession.getBrotherhood().getId())
					res.add(procession);
		return res;
	}

	public Finder updateFinder(final Finder finder) {
		this.checkPrincipal(finder);
		final Finder result = this.checkPrincipalFinder(finder);

		final Configuration configuration = this.configurationService.findOne();

		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getCacheFinder() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(currentDate.getTime() - 1000);

		if (!finder.getLastUpdate().after(updateFinder))
			result.setLastUpdate(lastUpdate);
		return result;
	}

	private Finder checkPrincipalFinder(final Finder f) {
		Finder result;

		final Date currentDate = new Date();

		if (f.getKeyword() == null)
			f.setKeyword("");

		if (f.getMinDate() == null)
			f.setMinDate(currentDate);

		if (f.getMaxDate() == null)
			f.setMaxDate(new Date(currentDate.getTime() + 315360000000L * 2));// 315360000000L
																			// son
																				// 10
																				// a�os
																				// en
																				// milisegundos

		result = f;

		return result;
	}

	private Date updateTime() {
		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - this.configurationService.findOne().getCacheFinder() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(updateFinder.getTime() - 1000);

		return lastUpdate;
	}

	public Finder findFinderByMemberId(final int memberId) {
		return this.finderRepository.findFinderByMemberId(memberId);
	}

}
