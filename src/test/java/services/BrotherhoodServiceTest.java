
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Brotherhood;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodServiceTest extends AbstractTest {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Templates

	public void findOneTest(final Integer brotherhoodId, final Class<?> expected) {
		try {
			this.brotherhoodService.findOne(brotherhoodId);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Collection<Integer> brotherhoodIds, final Class<?> expected) {
		try {
			this.brotherhoodService.findAll(brotherhoodIds);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void findAllTest(final Class<?> expected) {
		try {
			this.brotherhoodService.findAll();
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
	}

	public void createTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.brotherhoodService.create();
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void saveTest(final String username, final Brotherhood brotherhood, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.brotherhoodService.save(brotherhood);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	public void deleteTest(final String username, final Brotherhood brotherhood, final Class<?> expected) {
		super.authenticate(username);
		try {
			this.brotherhoodService.delete(brotherhood);
		} catch (final Throwable t) {
			final Class<?> caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.authenticate(null);
	}

	// Tests

}
