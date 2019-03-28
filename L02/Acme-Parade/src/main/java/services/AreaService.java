
package services;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * FRAN 19/02/2019 11:36 CREACI�N DE LA CLASE
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import domain.Area;
import domain.Chapter;

@Service
@Transactional
public class AreaService {

	//Managed Repository -------------------	
	@Autowired
	private AreaRepository			areaRepository;

	//Supporting services ------------------
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ChapterService			chapterService;


	//Simple CRUD Methods ------------------

	public Area create() {

		// "Check that an Admin is creating the new Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");
		// "New Area Creation"
		final Area area = new Area();
		// "Return new Area"
		return area;
	}

	public Area save(final Area area) {

		// Name:
		Assert.isTrue(area.getName() != "");

		if (area.getPictures().size() != 0) {
			final List<String> s = new ArrayList<String>(area.getPictures());
			final String[] pictures = s.get(0).split("'");
			final List<String> s2 = Arrays.asList(pictures);

			for (final String photo : s2)
				Assert.isTrue(this.checkPhotos(photo), "error.url");

			area.setPictures(s2);
		}

		// "Check that an Admin is saving the new Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");

		return this.areaRepository.save(area);
	}

	public Boolean checkPhotos(final String photo) {
		Boolean res = true;
		try {

			new URL(photo).toURI();
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public Area update(final Area area) {

		// "Check that an Admin is updating the Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");
		// "Return new Area"
		return this.update(area);
	}

	//Other Methods ------------------

	public Collection<Area> findAll() {

		final List<Area> res = this.areaRepository.findAll();
		return res;
	}

	public Area findOne(final int areaId) {

		final Area res = this.areaRepository.findOne(areaId);
		return res;
	}

	public void delete(final int areaId) {

		// findOne Area with areaId and check it for null
		final Area area = this.findOne(areaId);
		Assert.notNull(area, "areaExist.error");
		// check if Area has a Brotherhood settled
		Assert.isTrue(this.brotherhoodService.findByAreaId(areaId).size() == 0, "areaUsed.error");
		// delete the Area
		this.areaRepository.delete(areaId);
	}

	public Area findAreaChapter(final Chapter chapter) {
		System.out.println("servicio");
		final Area a = this.areaRepository.findAreaChapter(chapter.getId());
		System.out.println("area" + a);
		return a;
	}

	public Float ratioAreaNoCoordinate() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		final Float res = (float) (this.areaRepository.AreaNoChapter() / (float) this.areaRepository.AreaALL());
		return res;
	}

	//Ferrete

	public Collection<Area> unassignedAreas() {
		Assert.notNull(this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId()));
		return this.areaRepository.UnassignedAreas();
	}

	public void assignChapter(final Integer id) {
		final Chapter chapter = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
		System.out.println("SERVICIO:  " + chapter);
		Assert.notNull(this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId()));
		Assert.isNull(this.areaRepository.findAreaChapter(LoginService.getPrincipal().getId()));
		final Area area = this.findOne(id);
		System.out.println("SERVICIO:  " + area);
		Assert.notNull(area, "areaExist.error");
		Assert.isNull(area.getChapter(), "areaExistsChapter.error");
		System.out.println("CHAPTER DEL AREA: " + area.getChapter());
		Assert.isTrue(this.checkChapterHasNoArea(), "chapter has area");
		area.setChapter(chapter);
		this.areaRepository.save(area);
		this.areaRepository.flush();

	}
	public Boolean checkChapterHasNoArea() {
		final Boolean res = this.areaRepository.findAreaChapter(this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId()).getId()) == null ? true : false;
		return res;
	}
}
