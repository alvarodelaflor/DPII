
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import services.ChapterService;
import services.WelcomeService;
import domain.Area;
import security.LoginService;

@Controller
@RequestMapping("/area/chapter")
public class AreaChapterController {

	@Autowired
	private AreaService			areaService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private WelcomeService		welcomeService;
	@Autowired
	private ChapterService		chapterService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;

		try {
			final Collection<Area> areas = this.areaService.unassignedAreas();

			res = new ModelAndView("area/chapter/list");
			res.addObject("areas", areas);
			res.addObject("requestURI", "area/chapter/list.do");
			res.addObject("checkChapter", this.areaService.checkChapterHasNoArea());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			res = new ModelAndView("redirect:index.do");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "areaId", defaultValue = "-1") final int areaId) {

		ModelAndView res;

		try {
			final Area area = this.areaService.findOne(areaId);
			final Collection<String> pictures = area.getPictures();

			res = new ModelAndView("area/chapter/show");

			res.addObject("area", area);
			res.addObject("pictures", pictures);
			final int currentlyUsed = this.brotherhoodService.findByAreaId(areaId).size();
			res.addObject("currentlyUsed", currentlyUsed);
			res.addObject("requestURI", "area/administrator/show.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam(value = "areaId", defaultValue = "-1") final int areaId) {

		ModelAndView res;

		try {
			Assert.isTrue(this.areaService.checkChapterHasNoArea(), "chapter has area");
			this.areaService.assignChapter(areaId);

			res = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			res = new ModelAndView("redirect:list.do");
			if (e.getMessage() == null)
				res.addObject("message", "Ha habido un error");
			if (e.getMessage() != null && e.getMessage().equals("areaExist.error"))
				res.addObject("message", "areaExist.error");
			if (e.getMessage() != null && e.getMessage().equals("areaExistsChapter.error"))
				res.addObject("message", "areaExist.error");
			if (e.getMessage() != null && e.getMessage().equals("areaSameChapter.error"))
				res.addObject("message", "areaSameChapter.error");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

}
