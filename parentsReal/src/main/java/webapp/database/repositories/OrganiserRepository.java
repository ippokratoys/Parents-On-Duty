package webapp.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

@Repository
public interface OrganiserRepository extends CrudRepository<Organiser, String>{
    @Query("select sum(organiser.points)"+
            "from Organiser organiser")
    Integer organisersMoney();
}
