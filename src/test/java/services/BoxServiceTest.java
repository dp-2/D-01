
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	private BoxService		boxService;
	@Autowired
	private ActorService	actorService;


	// Templates

	public void findOneTest(final Integer boxId, final Class<?> expected) {
		try {
			this.boxService.findOne(boxId);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Collection<Integer> boxIds, final Class<?> expected) {
		try {
			this.boxService.findAll(boxIds);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Class<?> expected) {
		try {
			this.boxService.findAll();
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void createTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		try {
			final Actor principal = this.actorService.findPrincipal();
			this.boxService.create(principal);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void saveTest(final String username, final Box box, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.boxService.save(box);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void deleteTest(final String username, final Box box, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.boxService.delete(box);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	// Tests

}
