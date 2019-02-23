
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Area;
import repositories.AreaRepository;

@Service
@Transactional
public class AreaService {

	//Repository--------------------------------------------------------

	@Autowired
	private AreaRepository areaRepository;


	//Methods-----------------------------------------------------------

	public Area findOne(final Integer id) {
		return this.areaRepository.findOne(id);
	}

}
