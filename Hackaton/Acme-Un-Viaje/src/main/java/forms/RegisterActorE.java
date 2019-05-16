
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class RegisterActorE extends RegisterActor{

	private String city;

	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
