package forms;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class HistoryFinderForm {

	private String	title, name;

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
