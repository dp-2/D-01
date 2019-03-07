
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"name", "actor"
	})
})
public class Box extends DomainEntity {

	// ATRIBUTOS
	private String	name;
	private boolean	isSystem;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(final boolean isSystem) {
		this.isSystem = isSystem;
	}


	// Relationships ---------------------------------------------------------

	private Box		rootBox;
	private Actor	actor;


	@Valid
	@ManyToOne(optional = true)
	public Box getRootBox() {
		return this.rootBox;
	}

	public void setRootBox(final Box rootBox) {
		this.rootBox = rootBox;
	}

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
