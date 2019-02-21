
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Properties

	private String			nameSys;
	private String			banner;
	private List<String>	positionsEN;
	private List<String>	positionsES;
	private String			welcomeMessageES;
	private String			welcomeMessageEN;
	private List<String>	spamWordsEN;
	private List<String>	spamWordsES;
	private List<String>	negativeWordsEN;
	private List<String>	positiveWordsEN;
	private List<String>	negativeWordsES;
	private List<String>	positiveWordsES;
	private String			legalTextEN;
	private String			legalTextES;
	private int				numResults;
	private int				cacheFinder;


	@NotBlank
	public String getNameSys() {
		return this.nameSys;
	}
	public void setNameSys(final String nameSys) {
		this.nameSys = nameSys;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}
	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotBlank
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}
	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@NotNull
	@ElementCollection
	public List<String> getSpamWordsEN() {
		return this.spamWordsEN;
	}
	public void setSpamWordsEN(final List<String> spamWordsEN) {
		this.spamWordsEN = spamWordsEN;
	}

	@NotNull
	@ElementCollection
	public List<String> getNegativeWordsEN() {
		return this.negativeWordsEN;
	}
	public void setNegativeWordsEN(final List<String> negativeWordsEN) {
		this.negativeWordsEN = negativeWordsEN;
	}

	@NotNull
	@ElementCollection
	public List<String> getPositiveWordsEN() {
		return this.positiveWordsEN;
	}
	public void setPositiveWordsEN(final List<String> positiveWordsEN) {
		this.positiveWordsEN = positiveWordsEN;
	}

	@NotBlank
	@ElementCollection
	public List<String> getPositionsEN() {
		return this.positionsEN;
	}
	public void setPositionsEN(final List<String> positionsEN) {
		this.positionsEN = positionsEN;
	}

	@NotBlank
	@ElementCollection
	public List<String> getSpamWordsES() {
		return this.spamWordsES;
	}
	public void setSpamWordsES(final List<String> spamWordsES) {
		this.spamWordsES = spamWordsES;
	}

	@NotBlank
	@ElementCollection
	public List<String> getNegativeWordsES() {
		return this.negativeWordsES;
	}
	public void setNegativeWordsES(final List<String> negativeWordsES) {
		this.negativeWordsES = negativeWordsES;
	}

	@NotBlank
	@ElementCollection
	public List<String> getPositiveWordsES() {
		return this.positiveWordsES;
	}
	public void setPositiveWordsES(final List<String> positiveWordsES) {
		this.positiveWordsES = positiveWordsES;
	}

	@NotBlank
	@ElementCollection
	public List<String> getPositionsES() {
		return this.positionsES;
	}
	public void setPositionsES(final List<String> positionsES) {
		this.positionsES = positionsES;
	}

	@Range(min = 10, max = 100)
	public int getNumResults() {
		return this.numResults;
	}
	public void setNumResults(final int numResults) {
		this.numResults = numResults;
	}

	@Range(min = 1, max = 24)
	public int getCacheFinder() {
		return this.cacheFinder;
	}
	public void setCacheFinder(final int cacheFinder) {
		this.cacheFinder = cacheFinder;
	}

	@NotBlank
	public String getLegalTextEN() {
		return this.legalTextEN;
	}

	public void setLegalTextEN(final String legalTextEN) {
		this.legalTextEN = legalTextEN;
	}

	@NotBlank
	public String getLegalTextES() {
		return this.legalTextES;
	}

	public void setLegalTextES(final String legalTextES) {
		this.legalTextES = legalTextES;
	}

}
