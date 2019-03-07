
package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.Authority;
import domain.DomainEntity;

public class ActorForm extends DomainEntity {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photo;
	private String	phone;
	private String	address;
	private String	username;
	private String	password;
	private String	confirmPassword;
	private boolean	accept;
	private String	authority;
	private String	email;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	public String getConfirmPassword() {
		return this.confirmPassword;
	}
	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean getAccept() {
		return this.accept;
	}
	public void setAccept(final boolean accept) {
		this.accept = accept;
	}

	public String getMiddleName() {
		return this.middleName;
	}
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMIN + "|" + Authority.BANNED + "|" + Authority.BROTHERHOOD + "|" + Authority.MEMBER + "$")
	public String getAuthority() {
		return this.authority;
	}
	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	@NotBlank
	@Pattern(regexp = "^(\\w+@(\\w+(\\.\\w*)*)?)|(\\w+( \\w+)* <\\w+@(\\w+(\\.\\w*)*)?>)$")
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

}