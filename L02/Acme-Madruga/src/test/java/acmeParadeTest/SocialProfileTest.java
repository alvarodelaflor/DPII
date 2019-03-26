
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
				876, null
			}, {
				871, null
			}, {
				812, null
			}, {
				792, null
			}, {
				798, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListRegisterSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListRegisterSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 876) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));

			}
			if (id == 871) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 812) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 792) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
			}
			if (id == 798) {
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
	//				876, null
	//			}, {
	//				871, null
	//			}, {
	//				812, null
	//			}, {
	//				792, null
	//			}, {
	//				798, null
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
	//			if (id == 876) {
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
	//			if (id == 871) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Administrator test = this.administratorService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 812) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Brotherhood test = this.brotherhoodService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 792) {
	//				final SocialProfile newSo = this.socialProfileService.create();
	//				newSo.setLink("http://google.es");
	//				newSo.setName("try");
	//				newSo.setNick("tryNick");
	//				final Chapter test = this.chapterService.findOne(id);
	//				super.authenticate(test.getUserAccount().getUsername());
	//				final SocialProfile save = this.socialProfileService.save(newSo);
	//				Assert.isTrue(test.getSocialProfiles().contains(save));
	//			}
	//			if (id == 798) {
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
			//idActor,nick,link,name //876,871,812,792,798
			{
				876, 0, 0, 1, ConstraintViolationException.class
			}, {
				876, 0, 1, 0, ConstraintViolationException.class
			}, {
				876, 0, 1, 1, ConstraintViolationException.class
			}, {
				876, 1, 0, 0, ConstraintViolationException.class
			}, {
				876, 1, 0, 1, ConstraintViolationException.class
			}, {
				876, 1, 1, 0, ConstraintViolationException.class
			}, {
				876, 1, 1, 1, ConstraintViolationException.class
			}, {
				871, 0, 0, 1, ConstraintViolationException.class
			}, {
				871, 0, 1, 0, ConstraintViolationException.class
			}, {
				871, 0, 1, 1, ConstraintViolationException.class
			}, {
				871, 1, 0, 0, ConstraintViolationException.class
			}, {
				871, 1, 0, 1, ConstraintViolationException.class
			}, {
				871, 1, 1, 0, ConstraintViolationException.class
			}, {
				871, 1, 1, 1, ConstraintViolationException.class
			}, {
				812, 0, 0, 1, ConstraintViolationException.class
			}, {
				812, 0, 1, 0, ConstraintViolationException.class
			}, {
				812, 0, 1, 1, ConstraintViolationException.class
			}, {
				812, 1, 0, 0, ConstraintViolationException.class
			}, {
				812, 1, 0, 1, ConstraintViolationException.class
			}, {
				812, 1, 1, 0, ConstraintViolationException.class
			}, {
				812, 1, 1, 1, ConstraintViolationException.class
			}, {
				792, 0, 0, 1, ConstraintViolationException.class
			}, {
				792, 0, 1, 0, ConstraintViolationException.class
			}, {
				792, 0, 1, 1, ConstraintViolationException.class
			}, {
				792, 1, 0, 0, ConstraintViolationException.class
			}, {
				792, 1, 0, 1, ConstraintViolationException.class
			}, {
				792, 1, 1, 0, ConstraintViolationException.class
			}, {
				792, 1, 1, 1, ConstraintViolationException.class
			}, {
				798, 0, 0, 1, ConstraintViolationException.class
			}, {
				798, 0, 1, 0, ConstraintViolationException.class
			}, {
				798, 0, 1, 1, ConstraintViolationException.class
			}, {
				798, 1, 0, 0, ConstraintViolationException.class
			}, {
				798, 1, 0, 1, ConstraintViolationException.class
			}, {
				798, 1, 1, 0, ConstraintViolationException.class
			}, {
				798, 1, 1, 1, ConstraintViolationException.class
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

			if (id == 876) {
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 871) {
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 812) {
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 792) {
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
			}
			if (id == 798) {
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
				876, null
			}, {
				871, null
			}, {
				812, null
			}, {
				792, null
			}, {
				798, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 876) {
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
			if (id == 871) {
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
			if (id == 812) {
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
			if (id == 792) {
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
			if (id == 798) {
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
				876, IllegalArgumentException.class
			}, {
				871, IllegalArgumentException.class
			}, {
				812, IllegalArgumentException.class
			}, {
				792, IllegalArgumentException.class
			}, {
				798, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteNegativeSocialProfile((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteNegativeSocialProfile(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			if (id == 876) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Sponsor test = this.sponsorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				super.unauthenticate();
				final Administrator test1 = this.administratorService.findOne(871);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));

			}
			if (id == 871) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Administrator test = this.administratorService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Sponsor test1 = this.sponsorService.findOne(876);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 812) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Brotherhood test = this.brotherhoodService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Chapter test1 = this.chapterService.findOne(792);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 792) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Chapter test = this.chapterService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Brotherhood test1 = this.brotherhoodService.findOne(812);
				super.authenticate(test1.getUserAccount().getUsername());
				this.socialProfileService.delete(save);
				Assert.isTrue(!test.getSocialProfiles().contains(save));
			}
			if (id == 798) {
				final SocialProfile newSo = this.socialProfileService.create();
				newSo.setLink("http://google.es");
				newSo.setName("try");
				newSo.setNick("tryNick");
				final Member test = this.memberService.findOne(id);
				super.authenticate(test.getUserAccount().getUsername());
				final SocialProfile save = this.socialProfileService.save(newSo);
				Assert.isTrue(test.getSocialProfiles().contains(save));
				final Chapter test1 = this.chapterService.findOne(792);
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
