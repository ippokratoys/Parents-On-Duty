package webapp.database.repositories;

import org.springframework.data.repository.CrudRepository;
import webapp.database.BookEvent;

/**
 * Created by thanasis on 23/6/2017.
 */
public interface BookEventRepository extends CrudRepository<BookEvent, Integer> {

}
