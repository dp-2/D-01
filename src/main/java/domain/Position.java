
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	// Properties

	private String	nameEnglish;
	private String	nameSpanish;


	@NotBlank
	public String getNameEnglish() {
		return this.nameEnglish;
	}

	public void setNameEnglish(final String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@NotBlank
	public String getNameSpanish() {
		return this.nameSpanish;
	}

	public void setNameSpanish(final String nameSpanish) {
		this.nameSpanish = nameSpanish;
	}

}
