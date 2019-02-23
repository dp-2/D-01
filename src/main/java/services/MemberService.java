
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Member;
import repositories.MemberRepository;

@Service
@Transactional
public class MemberService {

	//Repository---------------------------------------------------------------------------------------

	@Autowired
	private MemberRepository memberRepository;


	//Methods------------------------------------------------------------------------------------------

	public Member findMemberByUserAcountId(final int userAccountId) {
		return this.memberRepository.findMemberByUserAcountId(userAccountId);
	}

}
