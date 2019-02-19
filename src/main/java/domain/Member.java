
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	// ATRIBUTOS
	private String	position;


	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	// Relationships ---------------------------------------------------------

}
