
package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import domain.Position;

@Service
@Transactional
public class PositionService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private PositionRepository	positionRepository;


	//	@Autowired
	//	private EnrollService		enrollService;

	//Constructor----------------------------------------------------------------------------

	public PositionService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Position create() {
		final Position p = new Position();
		return p;
	}

	public List<Position> findAll() {
		final List<Position> positions;

		positions = this.positionRepository.findAll();
		Assert.notNull(positions);

		return positions;
	}
	public Position findOne(final int positionId) {
		final Position position = this.positionRepository.findOne(positionId);
		Assert.notNull(positionId);

		return position;
	}

	public Position save(final Position position) {
		Assert.notNull(position);
		Position result;
		result = this.positionRepository.save(position);

		return result;
	}
	public void delete(final Position position) {

		Assert.notNull(position);
		this.positionRepository.delete(position);
	}

	//	public Collection<Position> findPositionES() {
	//		final Collection<Position> pos = this.positionRepository.findPositionES();
	//		return pos;
	//	}
	//
	//	public Collection<Position> findPositionEN() {
	//		final Collection<Position> pos = this.positionRepository.findPositionEN();
	//		return pos;
	//	}

	public Map<String, Double> computeStatistics() {
		Map<String, Double> result;
		double countTotal, countPresident, countVicepresident, countSecretary, countTreasurer, countHistorian, countOfficer, countVocal;

		countTotal = this.positionRepository.countTotal();
		countPresident = this.positionRepository.countPresident();
		countVicepresident = this.positionRepository.countVicepresident();
		countSecretary = this.positionRepository.countSecretary();
		countTreasurer = this.positionRepository.countTreasurer();
		countHistorian = this.positionRepository.countHistorian();
		countOfficer = this.positionRepository.countOfficer();
		countVocal = this.positionRepository.countVocal();

		result = new HashMap<String, Double>();
		result.put("count.total", countTotal);
		result.put("count.president", countPresident);
		result.put("count.vicepresident", countVicepresident);
		result.put("count.secretary", countSecretary);
		result.put("count.treasurer", countTreasurer);
		result.put("count.historian", countHistorian);
		result.put("count.officer", countOfficer);
		result.put("count.vocal", countVocal);

		return result;
	}

}
