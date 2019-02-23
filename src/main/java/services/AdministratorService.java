
package services;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Configuration;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	//--------------Managed repository---------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//-------------- Supporting Services-----------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;


	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		result.setUserAccount(this.userAccountService.create("ADMIN"));
		result.setBanned(false);
		result.setSpammer(false);
		//establezco ya su tipo de userAccount porque no va a cambiar

		return result;

	}

	public Administrator save(final Administrator administrator) {
		//comprobamos que el customer que nos pasan no sea nulo
		Assert.notNull(administrator);
		Boolean isCreating = null;

		//Si el admin que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos
		if (administrator.getId() == 0) {
			isCreating = true;
			administrator.setSpammer(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			//this.serviceUtils.checkNoActor();

		} else {
			isCreating = false;
			//comprobamos que su id no sea negativa por motivos de seguridad
			this.serviceUtils.checkIdSave(administrator);

			//este admin será el que está en la base de datos para usarlo si estamos ante un admin que ya existe
			Administrator adminDB;
			Assert.isTrue(administrator.getId() > 0);

			//cogemos el admin de la base de datos
			adminDB = this.administratorRepository.findOne(administrator.getId());

			administrator.setSpammer(adminDB.getSpammer());
			administrator.setUserAccount(adminDB.getUserAccount());

			//Comprobamos que el actor sea un admin
			this.serviceUtils.checkAuthority("ADMIN");
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			this.serviceUtils.checkActor(administrator);

		}
		if ((!administrator.getPhone().startsWith("+")) && StringUtils.isNumeric(administrator.getPhone()) && administrator.getPhone().length() > 3) {
			final Configuration configuration = this.configurationService.findOne();
			administrator.setPhone(configuration.getCountryCode() + administrator.getPhone());
		}
		Administrator res;
		//le meto al resultado final el admin que he ido modificando anteriormente
		res = this.administratorRepository.save(administrator);
		this.flush();
		if (isCreating)
			this.boxService.createIsSystemBoxs(res);
		return res;
	}

	//----------------------------

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	// -------------------------Other business methods ------------------------------

	public void flush() {
		this.administratorRepository.flush();
	}

	public void banActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(true);
		this.administratorRepository.save(a);

	}

	public void unBanActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(false);
		this.administratorRepository.save(a);

		//	//Ban actor
		//	public Boolean banActor(final Administrator a) {
		//		Boolean banned = false;
		//		Assert.notNull(a);
		//		//if (checkBan(a)){
		//		a.setBanned(true);
		//		//}
		//		this.save(a);
		//		return banned;
		//	}
		//
		//	//unban actor
		//	public Boolean unbanActor(final Administrator a) {
		//		Boolean banned = true;
		//		Assert.notNull(a);
		//		Assert.isTrue(a.getBanned());
		//		a.setBanned(false);
		//		this.save(a);
		//		return banned;
		//	}
		//
		//	//comprueba que tenga spam,
		//
		////	public Boolean checkBan(final Actor a){
		////		Boolean res= false;
		////
		////	}
		//
		//	public
	}

	public boolean isPrincipalAdmin() {
		boolean res = false;
		final UserAccount u = LoginService.getPrincipal();
		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		if (u.getAuthorities().contains(a))
			res = true;
		return res;
	}

}
