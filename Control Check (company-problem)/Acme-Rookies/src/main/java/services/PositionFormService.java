
package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import forms.PositionForm;

@Service
@Transactional
public class PositionFormService {

	public PositionForm create() {
		final PositionForm res = new PositionForm();
		return res;
	}
}
