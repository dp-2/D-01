
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import domain.Box;
import domain.DomainEntity;

@Access(AccessType.PROPERTY)
public class BoxForm extends DomainEntity {

	private String	name;
	private Box		rootBox;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@Valid
	public Box getRootBox() {
		return this.rootBox;
	}
	public void setRootBox(final Box rootBox) {
		this.rootBox = rootBox;
	}

}
