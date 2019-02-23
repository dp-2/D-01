
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Configuration;
import repositories.ConfigurationRepository;

@Service
@Transactional
public class ConfigurationService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Services-------------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	//Metodos--------------------------------------------------------------------

	public Configuration findOne() {
		final List<Configuration> configurations = new ArrayList<>(this.configurationRepository.findAll());
		return configurations.get(0);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(this.administratorService.isPrincipalAdmin(), "noAdmin");

		final Configuration saved = this.configurationRepository.save(configuration);

		return saved;
	}

	//Otros-----------------------------------------------------------------------

}
