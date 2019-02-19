
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"id", "row", "column"
	})
})
public class March extends DomainEntity {

	// Properties

	private String	status;
	private String	reason;
	private Integer	row;
	private Integer	column;


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
	public Integer getRow() {
		return this.row;
	}
	public void setRow(final Integer row) {
		this.row = row;
	}

	@Min(0)
	public Integer getColumn() {
		return this.column;
	}
	public void setColumn(final Integer column) {
		this.column = column;
	}

	// Relationships

}
