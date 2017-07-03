package webapp.database.repositories;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer>{
	
//	@Query("select event_table"
//			+ " from Event event_table, Eventsgroup event_group"
//			+ " where event_table.date>?1 and event_table.date<?2")
//	public List<Event> findEventByDate(Date from, Date to);
	
	@Query("select event"
			+ " from Event event, Eventsgroup event_group"
			+ " where event_group.name=name and event.eventsgroup=event_group.idEventsGroup")
	public List<Event> findEventByName(String name);


}
