
package services;

import java.util.Collection;

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

	public Collection<Position> findAll() {
		final Collection<Position> positions;

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

	public Collection<Position> findPositionES() {
		final Collection<Position> pos = this.positionRepository.findPositionES();
		return pos;
	}

	public Collection<Position> findPositionEN() {
		final Collection<Position> pos = this.positionRepository.findPositionEN();
		return pos;
	}
	//
	//	public Collection<Position> findPositionByLanguage(final String lng) {
	//		final Collection<Position> pos = this.positionRepository.findPositionByLanguage(lng);
	//		return pos;
	//	}

}
