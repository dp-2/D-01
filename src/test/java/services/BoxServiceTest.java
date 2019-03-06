
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
			this.boxService.flush();
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
			this.boxService.flush();
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
		this.createTest("brotherhood1", null);
	}

	@Test
	public void createTestUnautheticated() {
		this.createTest(null, IllegalArgumentException.class);
	}

	@Test
	public void saveTest() {
		super.authenticate("brotherhood1");
		final Box newBox = this.boxService.create(this.actorService.findPrincipal());
		newBox.setName("Ludovico");
		this.saveTest("brotherhood1", newBox, null);
	}

	@Test
	public void saveTestWrongUser() {
		super.authenticate("brotherhood1");
		final Box newBox = this.boxService.create(this.actorService.findPrincipal());
		newBox.setName("Ludovico");
		this.saveTest("brotherhood2", newBox, IllegalArgumentException.class);
	}

	@Test
	public void saveTestUniqueName() {
		super.authenticate("brotherhood1");
		final Box newBox = this.boxService.create(this.actorService.findPrincipal());
		newBox.setName("testBox");
		newBox.setRootBox(this.boxService.findOne(super.getEntityId("folder1Brotherhood1")));
		this.saveTest("brotherhood1", newBox, DataIntegrityViolationException.class);
	}

	@Test
	public void saveTestNonUniqueName() {
		super.authenticate("brotherhood1");
		final Box newBox = this.boxService.create(this.actorService.findPrincipal());
		newBox.setName("inBox");
		newBox.setRootBox(this.boxService.findOne(super.getEntityId("folder1Brotherhood1")));
		this.saveTest("brotherhood1", newBox, DataIntegrityViolationException.class);
	}

	@Test
	public void updateTest() {
		final Box testBox = this.boxService.findOne(super.getEntityId("folder5Member1"));
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
