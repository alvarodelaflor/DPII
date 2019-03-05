
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -------------------	
	@Autowired
	private ConfigurationRepository	configurationRepository;


	public Configuration getConfiguration() {
		return this.configurationRepository.findAll().iterator().next();
	}
}
