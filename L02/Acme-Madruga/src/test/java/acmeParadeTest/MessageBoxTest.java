
package acmeParadeTest;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.MessageBoxService;
import services.SponsorService;
import utilities.AbstractTest;
import domain.MessageBox;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageBoxTest extends AbstractTest {

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private SponsorService		sponsorService;


	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreateMessageBox((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testCreateMessageBox(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final MessageBox test = this.messageBoxService.create();
			test.setName("test");
			test.setParentBox(null);
			super.authenticate("sponsor");
			final MessageBox saved = this.messageBoxService.save(test);
			final Collection<MessageBox> listTest = this.messageBoxService.findAll();
			Assert.isTrue(listTest.contains(saved));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreateNegativeMessageBox((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testCreateNegativeMessageBox(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final MessageBox test = this.messageBoxService.create();
			test.setName("in box");
			test.setParentBox(null);
			final MessageBox saved = this.messageBoxService.save(test);
			final Collection<MessageBox> listTest = this.messageBoxService.findAll();
			Assert.isTrue(listTest.contains(saved));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver3() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteMessageBox((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteMessageBox(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Sponsor testSpons = this.sponsorService.findOne(1075);
			super.authenticate("sponsor");
			final MessageBox test = this.messageBoxService.create();
			test.setName("test");
			test.setParentBox(null);
			final MessageBox saved = this.messageBoxService.save(test);
			Collection<MessageBox> listTest = this.messageBoxService.findAll();
			testSpons.getMessageBoxes().add(saved);
			Assert.isTrue(listTest.contains(saved));
			this.messageBoxService.delete(saved);
			listTest = this.messageBoxService.findAll();
			Assert.isTrue(!listTest.contains(saved));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver4() {
		final Object testingData[][] = {
			//	name
			{
				"in box", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteMessageBoxNegative((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteMessageBoxNegative(final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Sponsor testSpons = this.sponsorService.findOne(1075);
			super.authenticate("sponsor");
			final MessageBox test = this.messageBoxService.create();
			test.setName(name);
			test.setParentBox(null);
			final MessageBox saved = this.messageBoxService.save(test);
			Collection<MessageBox> listTest = this.messageBoxService.findAll();
			testSpons.getMessageBoxes().add(saved);
			Assert.isTrue(listTest.contains(saved));
			this.messageBoxService.delete(saved);
			listTest = this.messageBoxService.findAll();
			Assert.isTrue(!listTest.contains(saved));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver5() {
		final Object testingData[][] = {

			{
				0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditMessageBox((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testEditMessageBox(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final MessageBox test = this.messageBoxService.create();
			test.setName("test");
			test.setParentBox(null);
			final MessageBox saved = this.messageBoxService.save(test);
			final Collection<MessageBox> listTest = this.messageBoxService.findAll();
			Assert.isTrue(listTest.contains(saved));
			saved.setName("test3");
			final MessageBox saved2 = this.messageBoxService.update(saved);
			Assert.isTrue(saved2.getName().equals("test3"));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver6() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				1061, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditNegativeMessageBox((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testEditNegativeMessageBox(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final MessageBox test = this.messageBoxService.create();
			test.setName("test");
			test.setParentBox(null);
			super.authenticate("sponsor");
			final MessageBox saved = this.messageBoxService.save(test);
			final Collection<MessageBox> listTest = this.messageBoxService.findAll();
			Assert.isTrue(listTest.contains(saved));
			saved.setName("in box");
			final MessageBox saved2 = this.messageBoxService.save(saved);
			Assert.isTrue(saved2.getName().equals("in box"));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
