
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Configuration;
import domain.Procession;
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
	public String generateTicker(final Procession procession) {
		final Date date = procession.getMomentOrganised();
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
		final int length = 5;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 5; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public String isUniqueTicker(final Procession procession) {
		String result = this.generateTicker(procession);

		for (final Procession procession1 : this.processionService.findAll())
			if (procession1.getTicker().equals(result))
				result = this.isUniqueTicker(procession);

		return result;
	}

	public String internacionalizcionWelcome() {
		String res = null;
		final Configuration configuration = this.findOne();

		if (LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en"))
			res = configuration.getWelcomeMessageEN();
		else if (LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es"))
			res = configuration.getWelcomeMessageES();

		return res;
	}

}
