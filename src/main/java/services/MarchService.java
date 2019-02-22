
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MarchRepository;
import repositories.MemberRepository;
import repositories.ProcessionRepository;
import security.LoginService;
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
		this.checkPrincipal(march);
		Assert.notNull(march);
		March result;
		if (march.getStatus().equals("APPROVED")) {
			march.setRowAtributte(this.isUniqueRowNum());
			march.setColumnAtributte(this.isUniqueColumNum());
		}
		result = this.marchRepository.save(march);
		Assert.notNull(result);
		final List<March> marchs = this.marchRepository.findAll();
		Assert.isTrue(marchs.contains(result));
		return result;
	}

	public void delete(final March march) {
		this.checkPrincipal(march);
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
	public Collection<March> findMarchsByProcession(final int processionId) {
		return this.marchRepository.findMarchsByProcession(processionId);
	}

	public Collection<March> findMarchsByMember(final int memberId) {
		return this.marchRepository.findMarchsByMember(memberId);
	}

	private int generarNum() {

		return num;
	}

	public int isUniqueColumNum() {
		int result = this.generarNum();
		final Collection<March> marchs = this.marchRepository.findAll();
		final ArrayList<Integer> columA = new ArrayList<>();

		for (final March m : marchs)
			columA.add(m.getColumnAtributte());

		if (columA.contains(result))
			result = this.isUniqueColumNum();

		return result;
	}

	public int isUniqueRowNum() {
		int result = this.generarNum();
		final Collection<March> marchs = this.marchRepository.findAll();
		final ArrayList<Integer> rowA = new ArrayList<>();

		for (final March m : marchs)
			rowA.add(m.getRowAtributte());

		if (rowA.contains(result))
			result = this.isUniqueColumNum();

		return result;
	}
}
