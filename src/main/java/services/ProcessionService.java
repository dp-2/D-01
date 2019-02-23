
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProcessionRepository;

@Service
@Transactional
public class ProcessionService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	//
}
