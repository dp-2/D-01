
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Box;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;


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

}
