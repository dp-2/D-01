
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private BoxService		boxService;
	@Autowired
	private ActorService	actorService;


	// Templates

	public void findOneTest(final Integer messageId, final Class<?> expected) {
		try {
			this.messageService.findOne(messageId);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Collection<Integer> messageIds, final Class<?> expected) {
		try {
			this.messageService.findAll(messageIds);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Class<?> expected) {
		try {
			this.messageService.findAll();
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void createTest(final String username, final Box box, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.messageService.create(box);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void saveTest(final String username, final Message message, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.messageService.save(message);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void deleteTest(final String username, final Message message, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.messageService.delete(message);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	// Tests

	@Test
	public void findOneTest() {
		final Integer boxId = super.getEntityId("inboxSystem");
		this.findOneTest(boxId, null);
	}
	@Test
	public void findOneTestNegativeId() {
		final Integer boxId = -1;
		this.findOneTest(boxId, IllegalArgumentException.class);
	}

	@Test
	public void findAllIdsTest() {
		final Collection<Integer> boxIds = new ArrayList<Integer>();
		boxIds.add(super.getEntityId("inboxSystem"));
		boxIds.add(super.getEntityId("outboxSystem"));
		this.findAllTest(boxIds, null);
	}

	@Test
	public void findAllIdsTestWrongIds() {
		final Collection<Integer> boxIds = new ArrayList<Integer>();
		boxIds.add(super.getEntityId("inboxSystem"));
		boxIds.add(-1);
		this.findAllTest(boxIds, IllegalArgumentException.class);
	}

	@Test
	public void createTest() {
		this.createTest("member1", this.boxService.findOne(super.getEntityId("folder1Member1")), null);
	}
	@Test
	public void createTestUnautheticated() {
		this.createTest(null, this.boxService.findOne(super.getEntityId("folder1Member1")), IllegalArgumentException.class);
	}

	@Test
	public void saveTest() {
		super.authenticate("member1");
		final Message newMessage = this.messageService.create(this.boxService.findOne(super.getEntityId("folder1Member1")));
		newMessage.setBody("bo");
		newMessage.setPriority("HIGH");
		newMessage.setRecipient(this.actorService.findPrincipal());
		newMessage.setSubject("subject");
		newMessage.setTags("tags");
		this.saveTest("member1", newMessage, null);
	}

	@Test
	public void saveTestWrongUser() {
		super.authenticate("brotherhood1");
		final Message newMessage = this.messageService.create(this.boxService.findOne(super.getEntityId("folder1Member1")));
		newMessage.setBody("bo");
		newMessage.setPriority("HIGH");
		newMessage.setRecipient(this.actorService.findPrincipal());
		newMessage.setSubject("subject");
		newMessage.setTags("tags");
		this.saveTest("member1", newMessage, null);
	}

	@Test
	public void updateTest() {
		final Box testMessage = this.boxService.findOne(super.getEntityId("folder5Member1"));
		testBox.setName("Mauricio");
		testBox.setRootBox(this.boxService.findOne(super.getEntityId("folder1Member1")));
		this.saveTest("member1", testBox, IllegalArgumentException.class);
	}

	@Test
	public void updateTestWrongUser() {
		final Box testBox = this.boxService.findOne(super.getEntityId("folder5Member1"));
		testBox.setName("Mauricio");
		testBox.setRootBox(this.boxService.findOne(super.getEntityId("folder1Member1")));
		this.saveTest("member2", testBox, IllegalArgumentException.class);
	}

	@Test
	public void deleteTest() {
		final Box testBox = this.boxService.findOne(super.getEntityId("folder5Member1"));
		this.deleteTest("member1", testBox, IllegalArgumentException.class);
	}

	@Test
	public void deleteTestWrongUser() {
		final Box testBox = this.boxService.findOne(super.getEntityId("folder5Member1"));
		this.deleteTest("member2", testBox, IllegalArgumentException.class);
	}

}
