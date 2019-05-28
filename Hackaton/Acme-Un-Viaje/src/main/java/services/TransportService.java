
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Transport;
import domain.Transporter;
import forms.TransportForm;
import repositories.TransportRepository;
import security.Authority;
import security.LoginService;
import utilities.CommonUtils;

@Service
@Transactional
public class TransportService {

	@Autowired
	private TransportRepository	transportRepository;

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private Validator			validator;


	// ---------- public class methods
	public Transport create() {
		final Transport res = new Transport();
		return res;
	}

	public Transport save(final Transport t) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER), "Logged user is not a transporter");
		Transport res = null;

		if (t.getId() == 0) {
			res = t;
			res.setTransporter(this.transporterService.getLoggedTransporter());
		} else {
			Assert.isTrue(this.loggedIsTransportOwner(t), "Transporter is not the transport owner");
			res = t;
		}

		res = this.transportRepository.save(res);

		return res;
	}

	public Collection<Transport> getLoggedTransporterTransports() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final int transporterId = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Transport> res = this.transportRepository.getTransporterTransports(transporterId);

		return res;
	}

	public Collection<Transport> getLoggedTransporterTransportsFromCurrentDate() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final int transporterId = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Transport> res = this.transportRepository.getTransporterTransportsFromDate(transporterId, new Date());

		return res;
	}

	public Transport getLoggedTransporterTransport(final int transportId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final Transport transport = this.transportRepository.findOne(transportId);
		Assert.isTrue(this.loggedIsTransportOwner(transport));

		return transport;
	}

	public Transport reconstruct(final Transport transport, final BindingResult binding) {
		Transport res = null;
		if (transport.getId() == 0)
			res = this.newTransport(transport);
		else
			res = this.editTransport(transport);

		this.validator.validate(res, binding);
		this.injectDateOneYearLaterError(res.getDate(), "date", binding);
		return res;
	}
	public void validateTransportForm(final TransportForm transportForm, final BindingResult binding) {
		this.validator.validate(transportForm, binding);
		try {
			this.injectDateOneYearLaterError(transportForm.getEndDate(), "endDate", binding);
			if (transportForm.getEndDate().before(transportForm.getInitDate()))
				binding.rejectValue("endDate", "transportForm.error.dateRange");
		} catch (final Throwable oops) {
		}
	}

	public void saveMultiple(final TransportForm transportForm) {
		final Collection<Transport> transports = new ArrayList<Transport>();

		Transport t = null;
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(transportForm.getInitDate());

		Date d = transportForm.getInitDate();
		final Transporter transporter = this.transporterService.getLoggedTransporter();
		final Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(transportForm.getEndDate());
		endCalendar.add(Calendar.DATE, 1);
		while (endCalendar.getTime().after(d)) {
			t = this.create();
			t.setDate(calendar.getTime());
			t.setDestination(transportForm.getDestination());
			t.setNumberOfPlaces(transportForm.getNumberOfPlaces());
			t.setOrigin(transportForm.getOrigin());
			t.setPrice(transportForm.getPrice());
			t.setReservedPlaces(0);
			t.setVehicleType(transportForm.getVehicleType());
			t.setTransporter(transporter);
			transports.add(t);
			calendar.add(Calendar.DATE, 1);
			d = calendar.getTime();
		}

		this.transportRepository.save(transports);
	}

	// ---------- Inner class methods
	private boolean loggedIsTransportOwner(final Transport transport) {
		final Transporter transporter = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
		return transport.getTransporter().equals(transporter);
	}

	private Transport newTransport(final Transport t) {
		final Transport res = t;
		return res;
	}

	private Transport editTransport(final Transport t) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));
		final Transport dbTransport = this.transportRepository.findOne(t.getId());
		Assert.isTrue(this.loggedIsTransportOwner(dbTransport));
		Assert.isTrue(dbTransport.getReservedPlaces() == 0);

		final Transport res = t;
		res.setId(dbTransport.getId());
		res.setVersion(dbTransport.getVersion());
		res.setTransporter(dbTransport.getTransporter());
		return res;
	}

	private void injectDateOneYearLaterError(final Date date, final String error, final BindingResult binding) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1);
		if (calendar.getTime().before(date))
			binding.rejectValue(error, "transportForm.error.tooLate");
	}

	public Collection<Transport> findAll() {
		return this.transportRepository.findAll();
	}

	public Transport findOne(final int id) {
		return this.transportRepository.findOne(id);
	}

	public Transport getLoggedTransporterTransportForEdit(final Integer transportId) {
		final Transport res = this.getLoggedTransporterTransport(transportId);
		Assert.isTrue(res.getReservedPlaces() == 0);
		return res;
	}

	public void delete(final Integer transportId) {
		final Transport t = this.transportRepository.findOne(transportId);
		Assert.isTrue(this.loggedIsTransportOwner(t));

		Assert.isTrue(t.getReservedPlaces() == 0);

		this.transportRepository.delete(transportId);

	}

	public List<Transporter> getTransportersByCustomerId(final int id) {

		final List<Transporter> res = new ArrayList<>();
		res.addAll(this.transportRepository.getTransportersByCustomerId(id));
		return res;
	}

	public Transporter findByUserAccountId(final int userAccountId) {

		return this.transportRepository.findByUserAccountId(userAccountId);
	}

	public void deleteAllByHost(final Transporter transporter) {
		final Collection<Transport> items = this.getLoggedTransporterTransports();
		if (items != null && !items.isEmpty())
			for (final Transport item : items)
				this.delete(item.getId());
	}
}
