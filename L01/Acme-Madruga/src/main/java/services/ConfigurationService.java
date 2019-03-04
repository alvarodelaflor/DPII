
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConfigurationRepository;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -------------------	
	@Autowired
	private ConfigurationRepository	configurationRepository;

}
