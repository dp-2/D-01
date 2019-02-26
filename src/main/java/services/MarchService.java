
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MarchRepository;
import repositories.MemberRepository;
import repositories.ProcessionRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.March;
import domain.Member;

@Service
@Transactional
public class MarchService {

	// Managed repository ------------------------------
	@Autowired
	private MarchRepository			marchRepository;

	//Servicios externos(cambiar los repositorios por servicios cuando se creen)

	@Autowired
	private ProcessionRepository	processionRepository;

	@Autowired
	private MemberRepository		memberRepository;


	//Constructor----------------------------------------------------------------------------

	public MarchService() {
		super();
	}

	// Simple CRUD methods ------------------------------ (Operaciones básicas, pueden tener restricciones según los requisitos)
	public March create(final int processionId, final int MemberId) {
		March march;

		march = new March();
		march.setMember(this.memberRepository.findOne(MemberId));
		march.setProcession(this.processionRepository.findOne(processionId));
		march.setStatus("PENDING");
		final Map<Integer, Integer> a = new HashMap<>();
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
		final Map<Integer, Integer> a = new HashMap<>();
		final Collection<March> marchs = this.marchRepository.findAll();
		if (march.getStatus().equals("APPROVED"))
			march.setLocation(this.isUniqueColumNum());
		else
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

	private Map<Integer, Integer> generarNum() {
		final Map<Integer, Integer> result = new HashMap<>();

		//Consideramos que hay como máximo 23 filas y 3 columnas
		final int i = (int) Math.random() * 25;
		final int j = (int) Math.random() * 3;
		result.put(i, j);
		return result;
	}
	public Map<Integer, Integer> isUniqueColumNum() {
		Map<Integer, Integer> result = this.generarNum();

		final Collection<March> marchs = this.marchRepository.findAll();
		final Collection<Map<Integer, Integer>> locations = new ArrayList<>();
		for (final March m : marchs)
			locations.add(m.getLocation());
		if (locations.contains(result))
			result = this.generarNum();

		return result;
	}
}
