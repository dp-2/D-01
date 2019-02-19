
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	name;
	private boolean	isSystem;


	@NotBlank
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
	private Collection<Box>	childrenBox;
	private Box				rootBox;
	private Actor			actor;


	@NotNull
	@Valid
	@OneToMany
	public Collection<Box> getChildrenBox() {
		return this.childrenBox;
	}

	public void setChildrenBox(final Collection<Box> childrenBox) {
		this.childrenBox = childrenBox;
	}

	@Valid
	@ManyToOne(optional = false)
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
