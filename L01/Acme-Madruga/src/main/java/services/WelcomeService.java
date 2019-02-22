
package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class WelcomeService {

	private Integer	phone	= 34;


	public Integer getPhone() {
		return this.phone;
	}

	public void setPhone(final Integer phone) {
		this.phone = phone;
	}

}
