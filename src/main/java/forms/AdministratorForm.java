
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class AdministratorForm extends ActorForm {

	private String	email;


	@Override
	@NotBlank
	@Pattern(regexp = "^(\\w+@(\\w+(\\.\\w*)*)?)|(\\w+( \\w+)* <\\w+@(\\w+(\\.\\w*)*)?>)$")
	public String getEmail() {
		return this.email;
	}
	@Override
	public void setEmail(final String email) {
		this.email = email;
	}

}
