/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.InceptionRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InceptionRecordTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private InceptionRecordService	inceptionRecordService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverInceptionRercordEdit() {
		final Object testingData[][] = {
			{
				// Positivo: Editar título
				"brotherhood", super.getEntityId("inceptionRecord1"), "ModificarTitulo", "hola", "http://hola", null
			}, {
				//Positivo: Editar descripción
				"brotherhood", super.getEntityId("inceptionRecord1"), "Carmen", "ModificarDescripcion", "http://hola", null
			}, {
				//Positivo: Editar photos
				"brotherhood", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "http://modificarPhoto", null
			}, {
				//Negativo: Dejar vacío el campo título
				"brotherhood", super.getEntityId("inceptionRecord1"), "", "hola", "http://modificarPhoto", ConstraintViolationException.class
			}, {
				//Negativo: Dejar vacío el campo descripción
				"brotherhood", super.getEntityId("inceptionRecord1"), "Carmen", "", "http://modificarPhoto", ConstraintViolationException.class
			}, {
				//Negativo: Dejar vacío el campo photos
				"brotherhood", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "", ConstraintViolationException.class
			}, {
				//Negativo: Editar inceptionRecord con member
				"member", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "http://modificarPhoto", IllegalArgumentException.class
			}, {
				//Negativo: Editar inceptionRecord con admin
				"admin", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "http://modificarPhoto", IllegalArgumentException.class
			}, {
				//Negativo: Editar inceptionRecord con chapter
				"chapter", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "http://modificarPhoto", IllegalArgumentException.class
			}, {
				//Negativo: Editar inceptionRecord con sponsor
				"sponsor", super.getEntityId("inceptionRecord1"), "Carmen", "hola", "http://modificarPhoto", IllegalArgumentException.class
			}, {
				//Negativo: Editar inceptionRecord que no existe
				"brotherhood", null, "Carmen", "hola", "http://modificarPhoto", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteInceptionRecordEdit((String) testingData[i][0], testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void templeteInceptionRecordEdit(final String userName, final Object inceptionRecord, final String title, final String description, final String photos, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

			final InceptionRecord inceptionRecordFind = this.inceptionRecordService.findOne((int) inceptionRecord);
			inceptionRecordFind.setTitle(title);
			inceptionRecordFind.setDescription(description);
			inceptionRecordFind.setPhotos(photos);
			this.inceptionRecordService.save(inceptionRecordFind);

			this.inceptionRecordService.flush();

			super.unauthenticate();

		} catch (final Exception oops) {
			//oops.printStackTrace();
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
