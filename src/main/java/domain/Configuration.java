
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Properties

	private String			nameSys;
	private String			banner;
	private String			welcomeMessageEN;
	private List<String>	spamWordsEN;
	private List<String>	negativeWordsEN;
	private List<String>	positiveWordsEN;
	private String			welcomeMessageES;
	private List<String>	spamWordsES;
	private List<String>	negativeWordsES;
	private List<String>	positiveWordsES;
	private int				numResults;
	private int				cacheFinder;
	private int				countryCode;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNameSys() {
		return this.nameSys;
	}
	public void setNameSys(final String nameSys) {
		this.nameSys = nameSys;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}
	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getSpamWordsEN() {
		return this.spamWordsEN;
	}
	public void setSpamWordsEN(final List<String> spamWordsEN) {
		this.spamWordsEN = spamWordsEN;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getNegativeWordsEN() {
		return this.negativeWordsEN;
	}
	public void setNegativeWordsEN(final List<String> negativeWordsEN) {
		this.negativeWordsEN = negativeWordsEN;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getPositiveWordsEN() {
		return this.positiveWordsEN;
	}
	public void setPositiveWordsEN(final List<String> positiveWordsEN) {
		this.positiveWordsEN = positiveWordsEN;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}
	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getSpamWordsES() {
		return this.spamWordsES;
	}
	public void setSpamWordsES(final List<String> spamWordsES) {
		this.spamWordsES = spamWordsES;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getNegativeWordsES() {
		return this.negativeWordsES;
	}
	public void setNegativeWordsES(final List<String> negativeWordsES) {
		this.negativeWordsES = negativeWordsES;
	}

	@NotEmpty
	@ElementCollection
	public List<String> getPositiveWordsES() {
		return this.positiveWordsES;
	}
	public void setPositiveWordsES(final List<String> positiveWordsES) {
		this.positiveWordsES = positiveWordsES;
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

	@Range(min = 1, max = 999)
	public int getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final int countrtCode) {
		this.countryCode = countrtCode;
	}

}
