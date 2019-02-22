
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class Url {

	private Url	url;


	@URL
	@NotBlank
	public Url getUrl() {
		return this.url;
	}
	public void setUrl(final Url url) {
		this.url = url;
	}

}
