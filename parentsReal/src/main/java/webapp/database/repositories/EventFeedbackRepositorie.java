package webapp.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webapp.database.Event;
import webapp.database.EventFeedback;
import webapp.database.Organiser;

import java.util.List;

/**
 * Created by thanasis on 3/7/2017.
 */

@Repository
public interface EventFeedbackRepositorie extends CrudRepository<EventFeedback,Integer> {

    @Query("select event_feedbacks"
            + " from EventFeedback event_feedbacks, Event event"
            + " where event_feedbacks.event=event and event.organiser=?1")
    public List<EventFeedback> findOrganiserFeedbacks(Organiser organiser);

}
