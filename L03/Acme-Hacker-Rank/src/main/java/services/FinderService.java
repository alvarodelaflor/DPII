
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	@Autowired
	FinderRepository	finderRepository;
}
