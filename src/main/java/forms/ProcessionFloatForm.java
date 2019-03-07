
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.DFloat;
import domain.DomainEntity;
import domain.Procession;

public class ProcessionFloatForm extends DomainEntity {

	private Procession	procession;
	private DFloat		dFloat;


	@NotNull
	@Valid
	public Procession getProcession() {
		return this.procession;
	}
	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	@NotNull
	@Valid
	public DFloat getDFloat() {
		return this.dFloat;
	}
	public void setDFloat(final DFloat dFloat) {
		this.dFloat = dFloat;
	}

}
