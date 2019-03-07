
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Properties

	private String	name;
	private String	pictures;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}
	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}


	// Relationships

	private Brotherhood	brotherhood;


	@Valid
	@OneToOne(optional = true)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
