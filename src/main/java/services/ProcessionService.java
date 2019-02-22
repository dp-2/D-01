
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ProcessionRepository	ProcessionRepository;


	//Constructor----------------------------------------------------------------------------

	public ProcessionService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Procession create(final int actorId) {
		final Procession procession = new Procession();

		// Ticker Unico
		final String ticker = this.isUniqueTickerEx();

		procession.setFfinal(false);
		procession.setTicker(ticker);
		return procession;
	}

	public Collection<Procession> findAll() {
		Collection<Procession> processions;

		processions = this.ProcessionRepository.findAll();
		Assert.notNull(processions);

		return processions;
	}
	public Procession findOne(final int processionId) {
		Procession procession;
		procession = this.ProcessionRepository.findOne(processionId);
		Assert.notNull(processionId);

		return procession;
	}

	public Procession save(final Procession procession) {
		Assert.notNull(procession);
		Procession result;

		result = this.ProcessionRepository.save(procession);

		return result;
	}
	public void delete(final Procession procession) {

		Assert.notNull(procession);
		this.ProcessionRepository.delete(procession);
	}
	//Other Methods-----------------------------------------------------------------

	@SuppressWarnings("deprecation")
	public String generateTickerEx() {
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

		return day + "/" + month + "/" + year + "-" + this.generateStringAux();
	}

	private String generateStringAux() {
		final int length = 6;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < length; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public String isUniqueTickerEx() {
		String result = this.generateTickerEx();
		final Collection<Procession> Processions = this.findAll();
		final ArrayList<String> tickers = new ArrayList<>();

		for (final Procession Procession : Processions)
			tickers.add(Procession.getTicker());

		if (Processions.contains(result))
			result = this.isUniqueTickerEx();

		return result;
	}

	public int diferenciaMeses(final int ProcessionId) {
		int meses = 0;
		final Procession Procession = this.findOne(ProcessionId);

		final Date now = new Date();
		final Date fechaProcession = Procession.getMomentOrganised();

		final int start = (now.getYear() + 1900) * 12 + now.getMonth();
		final int end = (fechaProcession.getYear() + 1900) * 12 + fechaProcession.getMonth();

		meses = start - end;
		return meses;
	}
}
