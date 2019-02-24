
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;
import domain.DomainEntity;

public class MessageForm extends DomainEntity {

	private Actor	recipient;
	private String	subject;
	private String	body;
	private String	priority;
	private String	tags;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^((HIGH)|(NEUTRAL)|(LOW))$", message = "The priority only can be 'LOW', 'NEUTRAL' or 'HIGH'.")
	public String getPriority() {
		return this.priority;
	}
	public void setPriority(final String priority) {
		this.priority = priority;
	}

	public String getTags() {
		return this.tags;
	}
	public void setTags(final String tags) {
		this.tags = tags;
	}

	@NotNull
	@Valid
	public Actor getRecipient() {
		return this.recipient;
	}
	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
