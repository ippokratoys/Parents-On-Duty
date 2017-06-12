package webapp.database.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

@Repository
public interface EventsgroupRepository extends CrudRepository<Eventsgroup, Integer>{

	List<Eventsgroup> findByName(String param);
	List<Eventsgroup> findByNameContaining(String param);
}