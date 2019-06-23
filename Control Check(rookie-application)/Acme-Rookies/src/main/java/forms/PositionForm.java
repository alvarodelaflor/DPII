
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class PositionForm {

	private String	palabra;


	@NotBlank
	public String getPalabra() {
		return this.palabra;
	}

	public void setPalabra(final String palabra) {
		this.palabra = palabra;
	}

}
