
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyword;
	private Date	minDate;
	private Date	maxDate;
	private Date	lastUpdate;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMinDate() {
		return this.minDate;
	}

	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Past
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relationships
	private Member				member;
	private Area				area;
	private List<Procession>	processions;


	@Valid
	@NotNull
	@OneToMany
	public List<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final List<Procession> processions) {
		this.processions = processions;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Member getMember() {
		return this.member;
	}
	public void setMember(final Member member) {
		this.member = member;
	}

	@Valid
	@OneToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

}
