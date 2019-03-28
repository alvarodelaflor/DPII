
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
public class ConfigurationService {

	//Managed Repository -------------------	
	@Autowired
	private ConfigurationRepository	configurationRepository;


	public Configuration getConfiguration() {

		return this.configurationRepository.findAll().iterator().next();
	}
	public Configuration save(final Configuration configuration) {

		Assert.isTrue(configuration.getFair() >= 0 && configuration.getVAT() >= 0, "number.positive.error");
		return this.configurationRepository.save(configuration);
	}
}
