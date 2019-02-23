
package services;

import java.util.List;

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

	public List<Area> findAll() {
		return this.areaRepository.findAll();
	}

	public Area findOne(final Integer id) {
		return this.areaRepository.findOne(id);
	}

}
