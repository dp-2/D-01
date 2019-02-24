
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;
import domain.Procession;

@Service
@Transactional
public class ConfigurationService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Services-------------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ProcessionService		processionService;


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

	@SuppressWarnings("deprecation")
	public String generateTicker() {
		final Date date = new Date();
		final Integer s1 = date.getDate();
		String day = s1.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer s2 = date.getMonth() + 1;
		String month = s2.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer s3 = date.getYear();
		final String year = s3.toString().substring(1);

		return year + month + day + "-" + this.generateStringAux();
	}

	private String generateStringAux() {
		final int length = 6;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public String isUniqueTicker() {
		String result = this.generateTicker();

		for (final Procession procession : this.processionService.findAll())
			if (procession.getTicker().equals(result))
				result = this.isUniqueTicker();

		return result;
	}

	public Configuration findConfigurations() {
		return this.configurationRepository.findConfigurations();
	}

}
