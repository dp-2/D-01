
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.sender.id = ?1")
	Collection<Message> findSendedMessages(int actorId);

	@Query("select m from Message m where m.recipient.id = ?1")
	Collection<Message> findReceivedMessages(int actorId);

	@Query("select m from Message m where m.box.id = ?1")
	Collection<Message> findByBoxId(int boxId);

	@Query("select m from Message m where m.moment = ?1 and m.sender.id = ?2 and " + "m.recipient.id = ?3 and m.subject = ?4")
	Collection<Message> findMessageByMomentSenderReceiverAndSubject(Date moment, int senderId, int recipientId, String subject);

}
