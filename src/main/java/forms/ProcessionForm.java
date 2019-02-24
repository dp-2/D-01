
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ProcessionForm {

	private int		id;
	private int		version;

	private String	ticker;
	private String	title;
	private String	description;
	private Date	momentOrganised;
	private boolean	ffinal;

	private int		finderId;
	private int		brotherhoodId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
	@NotBlank
	@Pattern(regexp = "^\\d{6}-[A-Z]{6}$")
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	public Date getMomentOrganised() {
		return this.momentOrganised;
	}
	public void setMomentOrganised(final Date momentOrganised) {
		this.momentOrganised = momentOrganised;
	}

	public boolean isFfinal() {
		return this.ffinal;
	}
	public void setFfinal(final boolean ffinal) {
		this.ffinal = ffinal;
	}

	public int getFinderId() {
		return this.finderId;
	}

	public void setFinderId(final int finderId) {
		this.finderId = finderId;
	}

	public int getBrotherhoodId() {
		return this.brotherhoodId;
	}

	public void setBrotherhoodId(final int brotherhoodId) {
		this.brotherhoodId = brotherhoodId;
	}

}
