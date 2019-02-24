
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class Url {

	private String	url;


	@URL
	@NotBlank
	public String getUrl() {
		return this.url;
	}
	public void setUrl(final String url) {
		this.url = url;
	}

}
