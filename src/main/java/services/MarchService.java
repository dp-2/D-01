
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MarchRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.March;
import domain.Member;

@Service
@Transactional
public class MarchService {

	// Managed repository ------------------------------
	@Autowired
	private MarchRepository		marchRepository;

	//Servicios externos(cambiar los repositorios por servicios cuando se creen)

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ActorService		actorService;


	//Constructor----------------------------------------------------------------------------

	public MarchService() {
		super();
	}

	// Simple CRUD methods ------------------------------ (Operaciones básicas, pueden tener restricciones según los requisitos)
	public March create(final int processionId, final int MemberId) {
		March march;

		march = new March();
		march.setMember(this.memberService.findOne(MemberId));
		march.setProcession(this.processionService.findOne(processionId));
		march.setStatus("PENDING");
		final List<Integer> a = new ArrayList<>();
		march.setLocation(a);
		march.setReason("");
		return march;
	}

	public Collection<March> findAll() {
		Collection<March> marchs;

		marchs = this.marchRepository.findAll();
		Assert.notNull(marchs);

		return marchs;
	}

	public March findOne(final int MarchId) {
		March march;
		march = this.marchRepository.findOne(MarchId);
		Assert.notNull(march);

		return march;
	}

	public March save(final March march) {
		//Assert.isTrue(this.checkPrincipal(march) || this.checkPrincipalBro(march));
		Assert.notNull(march);
		final March result;
		final List<Integer> a = new ArrayList<>();
		//	final Collection<March> marchs = this.marchRepository.findAll();
		if (march.getStatus().equals("APPROVED") && march.getLocation() == null)
			march.setLocation(this.isUniqueColumNum());
		else if (march.getLocation() != null) {
			march.setLocation(march.getLocation());
			final Collection<March> marchs = this.marchRepository.findAll();
			final Collection<List<Integer>> locations = new ArrayList<>();
			for (final March m : marchs)
				locations.add(m.getLocation());
			Assert.isTrue(!(locations.contains(march.getLocation())), "errorposition");
		} else
			march.setLocation(a);

		result = this.marchRepository.save(march);

		Assert.notNull(result);
		//Assert.isTrue(marchs.contains(result));
		return result;
	}
	public void delete(final March march) {
		this.checkPrincipal(march);
		Assert.isTrue(march.getStatus().equals("PENDING"));
		this.marchRepository.delete(march);
		final List<March> marchs = this.marchRepository.findAll();
		Assert.isTrue(!(marchs.contains(march)));
	}

	//Other methods
	public Boolean checkPrincipal(final March march) {
		final Member member = march.getMember();
		Assert.isTrue(member.getUserAccount().equals(LoginService.getPrincipal()), "No es su propietario");
		return true;
	}
	public Boolean checkPrincipalBro(final March march) {
		final Brotherhood brotherhood = march.getProcession().getBrotherhood();
		Assert.isTrue(brotherhood.getUserAccount().equals(LoginService.getPrincipal()), "No es su propietario");
		return true;
	}
	public Collection<March> findMarchsByProcession(final int processionId) {
		return this.marchRepository.findMarchsByProcession(processionId);
	}

	public Collection<March> findMarchsByMember(final int memberId) {
		return this.marchRepository.findMarchsByMember(memberId);
	}

	public Double findMatchByProcessionidAndMemberid(final int processionId, final int memberId) {
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		return this.marchRepository.findMatchByProcessionidAndMemberid(processionId, a.getId());
	}

	private List<Integer> generarNum() {
		final List<Integer> result = new ArrayList<>();

		//Consideramos que hay como máximo 23 filas y 3 columnas
		final int i = (int) (Math.random() * 23);
		final int j = (int) (Math.random() * 3);
		result.add(i);
		result.add(j);
		return result;
	}
	public List<Integer> isUniqueColumNum() {
		List<Integer> result = this.generarNum();

		final Collection<March> marchs = this.marchRepository.findAll();
		final Collection<List<Integer>> locations = new ArrayList<>();
		for (final March m : marchs)
			locations.add(m.getLocation());
		while (locations.contains(result))
			result = this.generarNum();

		return result;
	}

	//queryc ratio of request to march grouped by status

	public List<Double> ratioRequestByStatus() {
		List<Double> res;
		res = this.marchRepository.queryC6c();
		return res;
	}

	//The listing of members who have got at least 10% the maximum number of request to march accepted.
	public List<Member> members10PerMarchAccepted() {
		List<Member> members10RequestAccepted = new ArrayList<>();
		members10RequestAccepted = this.marchRepository.members10PerMarchAccepted();
		return members10RequestAccepted;
	}

}
