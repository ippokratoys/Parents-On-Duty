package webapp.database.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{
    List<Location> findAllByCertificateChecked(boolean certificateChecked);

    List<Location> findAllByValidUntilNotNull();

}
