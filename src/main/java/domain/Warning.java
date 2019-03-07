
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Warning extends DomainEntity {

	public Boolean	isWarning;


	public Boolean getIsWarning() {
		return this.isWarning;
	}

	public void setIsWarning(final Boolean isWarning) {
		this.isWarning = isWarning;
	}

}
