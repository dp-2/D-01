
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	// Properties

	private String	ticker;
	private String	title;
	private String	description;
	private Date	momentOrganised;
	private boolean	ffinal;


	@NotBlank
	@SafeHtml
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMomentOrganised() {
		return this.momentOrganised;
	}
	public void setMomentOrganised(final Date momentOrganised) {
		this.momentOrganised = momentOrganised;
	}

	public boolean isFfinal() {
		return this.ffinal;
	}
	public void setFfinal(final boolean ffinal) {
		this.ffinal = ffinal;
	}


	//Relationships

	private Brotherhood	brotherhood;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
