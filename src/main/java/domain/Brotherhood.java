
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	// ATRIBUTOS
	private String		title;
	private Date		establishedMoment;
	private List<Url>	pictures;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getEstablishedMoment() {
		return this.establishedMoment;
	}

	public void setEstablishedMoment(final Date establishedMoment) {
		this.establishedMoment = establishedMoment;
	}

	@ElementCollection
	@Valid
	@NotNull
	public List<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<Url> pictures) {
		this.pictures = pictures;
	}

	// Relationships ---------------------------------------------------------

}
