
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Transport;
import domain.Transporter;
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


	public Collection<Transport> getLoggedTransporterTransports() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final int transporterId = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Transport> res = this.transportRepository.getTransporterTransports(transporterId);

		return res;
	}

	public Transport getLoggedTransporterTransport(final int transportId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final Transport transport = this.transportRepository.findOne(transportId);
		Assert.isTrue(this.isTransportOwner(transport));

		return transport;
	}

	public Collection<Transport> findAll() {
		return this.transportRepository.findAll();
	}

	public Transport findOne(final int id) {
		return this.transportRepository.findOne(id);
	}

	// -- Inner class methods
	private boolean isTransportOwner(final Transport transport) {
		final Transporter transporter = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
		return transport.getTransporter().equals(transporter);
	}

}
