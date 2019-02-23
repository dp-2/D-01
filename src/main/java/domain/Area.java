
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Properties

	private String	name;
	private String	pictures;


	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@NotBlank
	public String getPictures() {
		return this.pictures;
	}
	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}


	// Relationships

	private Brotherhood	brotherhood;
	private Finder		finder;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}
	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
