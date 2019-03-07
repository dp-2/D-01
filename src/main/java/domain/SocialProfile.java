
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	// Identification ---------------------------------------------------------
	//ATRIBUTOS
	private String	nick;
	private String	nameSN;
	private String	linkSN;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNick() {
		return this.nick;
	}
	public void setNick(final String nick) {
		this.nick = nick;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNameSN() {
		return this.nameSN;
	}

	public void setNameSN(final String nameSN) {
		this.nameSN = nameSN;
	}
	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLinkSN() {
		return this.linkSN;
	}

	public void setLinkSN(final String linkSN) {
		this.linkSN = linkSN;
	}


	// Relationships ---------------------------------------------------------
	private Actor	actor;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
