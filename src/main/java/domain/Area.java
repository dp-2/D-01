
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Properties

	private String			name;
	private Collection<Url>	pictures;


	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection
	@NotNull
	@Valid
	public Collection<Url> getPictures() {
		return this.pictures;
	}
	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}


	// Relationships

	private Brotherhood	brotherhood;


	@Valid
	@OneToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
