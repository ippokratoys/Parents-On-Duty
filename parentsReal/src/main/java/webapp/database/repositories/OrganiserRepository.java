package webapp.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

@Repository
public interface OrganiserRepository extends CrudRepository<Organiser, String>{

}
