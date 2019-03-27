
package acmeParadeTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AdministratorService;
import services.BrotherhoodService;
import services.ChapterService;
import services.MemberService;
import services.SocialProfileService;
import services.SponsorService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.SocialProfile;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialProfileTest extends AbstractTest {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MemberService			memberService;


	@Test
	public void driver1() {
		final Object testingData[][] = {
			//idActor,
			{
				1068, null
			}, {
				1062, null
			}, {
				1004, null
			}, {
				978, null
			}, {
				990, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListRegisterSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListRegisterSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 1068) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));

			}
			if (id == 1062) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 1004) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 978) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 990) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Member test = this.memberService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}

			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	//	@Test
	//	public void driver2() {
	//		final Object testingData[][] = {
	//			//idActor,
	//			{
	//				1068, null
	//			}, {
	//				1062, null
	//			}, {
	//				1004, null
	//			}, {
	//				978, null
	//			}, {
	//				990, null
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.testListSocialProfileNegative((int) testingData[i][0], (Class<?>) testingData[i][1]);
	//
	//	}
	//
	//	public void testListSocialProfileNegative(final int id, final Class<?> expected) {
	//		Class<?> caught = null;
	//
	//		try {
	//			this.startTransaction();
	//
	//			if (id == 1068) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Sponsor test = this.sponsorService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//
	//			}
	//			if (id == 1062) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Administrator test = this.administratorService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 1004) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Brotherhood test = this.brotherhoodService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 978) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Chapter test = this.chapterService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 990) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Member test = this.memberService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//
	//			this.sponsorService.flush();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		} finally {
	//			this.rollbackTransaction();
	//		}
	//		this.checkExceptions(expected, caught);
	//	}

	@Test
	public void driver3() {
		final Object testingData[][] = {
			//idActor,nick,link,name //1068,1062,1004,978,990
			{
				1068, 0, 0, 1, ConstraintViolationException.class
			}, {
				1068, 0, 1, 0, ConstraintViolationException.class
			}, {
				1068, 0, 1, 1, ConstraintViolationException.class
			}, {
				1068, 1, 0, 0, ConstraintViolationException.class
			}, {
				1068, 1, 0, 1, ConstraintViolationException.class
			}, {
				1068, 1, 1, 0, ConstraintViolationException.class
			}, {
				1068, 1, 1, 1, ConstraintViolationException.class
			}, {
				1062, 0, 0, 1, ConstraintViolationException.class
			}, {
				1062, 0, 1, 0, ConstraintViolationException.class
			}, {
				1062, 0, 1, 1, ConstraintViolationException.class
			}, {
				1062, 1, 0, 0, ConstraintViolationException.class
			}, {
				1062, 1, 0, 1, ConstraintViolationException.class
			}, {
				1062, 1, 1, 0, ConstraintViolationException.class
			}, {
				1062, 1, 1, 1, ConstraintViolationException.class
			}, {
				1004, 0, 0, 1, ConstraintViolationException.class
			}, {
				1004, 0, 1, 0, ConstraintViolationException.class
			}, {
				1004, 0, 1, 1, ConstraintViolationException.class
			}, {
				1004, 1, 0, 0, ConstraintViolationException.class
			}, {
				1004, 1, 0, 1, ConstraintViolationException.class
			}, {
				1004, 1, 1, 0, ConstraintViolationException.class
			}, {
				1004, 1, 1, 1, ConstraintViolationException.class
			}, {
				978, 0, 0, 1, ConstraintViolationException.class
			}, {
				978, 0, 1, 0, ConstraintViolationException.class
			}, {
				978, 0, 1, 1, ConstraintViolationException.class
			}, {
				978, 1, 0, 0, ConstraintViolationException.class
			}, {
				978, 1, 0, 1, ConstraintViolationException.class
			}, {
				978, 1, 1, 0, ConstraintViolationException.class
			}, {
				978, 1, 1, 1, ConstraintViolationException.class
			}, {
				990, 0, 0, 1, ConstraintViolationException.class
			}, {
				990, 0, 1, 0, ConstraintViolationException.class
			}, {
				990, 0, 1, 1, ConstraintViolationException.class
			}, {
				990, 1, 0, 0, ConstraintViolationException.class
			}, {
				990, 1, 0, 1, ConstraintViolationException.class
			}, {
				990, 1, 1, 0, ConstraintViolationException.class
			}, {
				990, 1, 1, 1, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterNegativeSocialProfile((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	public void testRegisterNegativeSocialProfile(final int id, final int nick, final int link, final int name, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final SocialProfile newSo = this.socialProfileService.create();
			newSo.setLink("http://google.es");
			newSo.setName("try");
			newSo.setNick("tryNick");

			if (nick != 0)
				newSo.setNick(null);
			if (link != 0)
				newSo.setLink(null);
			if (name != 0)
				newSo.setName(null);

			if (id == 1068) {
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 1062) {
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 1004) {
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 978) {
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 990) {
				final Member test = this.memberService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}

			this.socialProfileService.flush();

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
			//idActor,
			{
				1068, null
			}, {
				1062, null
			}, {
				1004, null
			}, {
				978, null
			}, {
				990, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 1068) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));

			}
			if (id == 1062) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 1004) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 978) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 990) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Member test = this.memberService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}

			this.sponsorService.flush();

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
			//idActor,
			{
				1068, IllegalArgumentException.class
			}, {
				1062, IllegalArgumentException.class
			}, {
				1004, IllegalArgumentException.class
			}, {
				978, IllegalArgumentException.class
			}, {
				990, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteNegativeSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteNegativeSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 1068) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				super.unauthenticate();
				final Administrator test1 = this.administratorService.findOne(1062);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));

			}
			if (id == 1062) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Sponsor test1 = this.sponsorService.findOne(1068);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 1004) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Chapter test1 = this.chapterService.findOne(978);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 978) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Brotherhood test1 = this.brotherhoodService.findOne(1004);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 990) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Member test = this.memberService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Chapter test1 = this.chapterService.findOne(978);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}

			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
