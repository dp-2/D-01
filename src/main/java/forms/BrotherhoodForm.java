
package forms;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;
import domain.Url;

public class BrotherhoodForm extends DomainEntity {

	private String		name;
	private String		middleName;
	private String		surname;
	private String		photo;
	private String		email;
	private String		phone;
	private String		address;
	private String		title;
	private List<Url>	pictures;
	private String		username;
	private String		password;
	private String		confirmPassword;
	private boolean		accept;


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

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
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

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@ElementCollection
	@Valid
	public List<Url> getPictures() {
		return this.pictures;
	}
	public void setPictures(final List<Url> pictures) {
		this.pictures = pictures;
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

}
