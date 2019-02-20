
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"id", "rowAtributte", "columnAtributte"
	})
})
public class March extends DomainEntity {

	// Properties

	private String	status;
	private String	reason;
	private Integer	rowAtributte;
	private Integer	columnAtributte;


	@NotNull
	@NotBlank
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^REJECTED$")
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	public String getReason() {
		return this.reason;
	}
	public void setReason(final String reason) {
		this.reason = reason;
	}

	@Min(0)
	public Integer getRowAtributte() {
		return this.rowAtributte;
	}
	public void setRowAtributte(final Integer rowAtributte) {
		this.rowAtributte = rowAtributte;
	}

	@Min(0)
	public Integer getColumnAtributte() {
		return this.columnAtributte;
	}
	public void setColumnAtributte(final Integer columnAtributte) {
		this.columnAtributte = columnAtributte;
	}


	// Relationships

	private Member		member;
	private Procession	procession;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

}
