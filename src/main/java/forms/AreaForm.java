
package forms;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Box;
import domain.DomainEntity;

public class AreaForm extends DomainEntity {

	private String	name;
	private Box		rootBox;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
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
