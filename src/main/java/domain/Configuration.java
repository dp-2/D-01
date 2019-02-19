
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Properties

	private String	nameSys;
	private String	banner;
	private String	welcomeMessageEN;
	private String	spamWordsEN;
	private String	negativeWordsEN;
	private String	positiveWordsEN;
	private String	positionsEN;
	private String	welcomeMessageES;
	private String	spamWordsES;
	private String	negativeWordsES;
	private String	positiveWordsES;
	private String	positionsES;
	private int		numResults;
	private int		cacheFinder;


	@NotNull
	@NotBlank
	public String getNameSys() {
		return this.nameSys;
	}
	public void setNameSys(final String nameSys) {
		this.nameSys = nameSys;
	}

	@NotNull
	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotNull
	@NotBlank
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}
	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotNull
	@NotBlank
	public String getSpamWordsEN() {
		return this.spamWordsEN;
	}
	public void setSpamWordsEN(final String spamWordsEN) {
		this.spamWordsEN = spamWordsEN;
	}

	@NotNull
	@NotBlank
	public String getNegativeWordsEN() {
		return this.negativeWordsEN;
	}
	public void setNegativeWordsEN(final String negativeWordsEN) {
		this.negativeWordsEN = negativeWordsEN;
	}

	@NotNull
	@NotBlank
	public String getPositiveWordsEN() {
		return this.positiveWordsEN;
	}
	public void setPositiveWordsEN(final String positiveWordsEN) {
		this.positiveWordsEN = positiveWordsEN;
	}

	@NotNull
	@NotBlank
	public String getPositionsEN() {
		return this.positionsEN;
	}
	public void setPositionsEN(final String positionsEN) {
		this.positionsEN = positionsEN;
	}

	@NotNull
	@NotBlank
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}
	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@NotNull
	@NotBlank
	public String getSpamWordsES() {
		return this.spamWordsES;
	}
	public void setSpamWordsES(final String spamWordsES) {
		this.spamWordsES = spamWordsES;
	}

	@NotNull
	@NotBlank
	public String getNegativeWordsES() {
		return this.negativeWordsES;
	}
	public void setNegativeWordsES(final String negativeWordsES) {
		this.negativeWordsES = negativeWordsES;
	}

	@NotNull
	@NotBlank
	public String getPositiveWordsES() {
		return this.positiveWordsES;
	}
	public void setPositiveWordsES(final String positiveWordsES) {
		this.positiveWordsES = positiveWordsES;
	}

	@NotNull
	@NotBlank
	public String getPositionsES() {
		return this.positionsES;
	}
	public void setPositionsES(final String positionsES) {
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

}
