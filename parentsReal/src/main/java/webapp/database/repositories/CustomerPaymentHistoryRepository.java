package webapp.database.repositories;

import org.springframework.data.repository.CrudRepository;
import webapp.database.CustomerPaymentHistory;

/**
 * Created by thanasis on 29/6/2017.
 */
public interface CustomerPaymentHistoryRepository extends CrudRepository<CustomerPaymentHistory,Integer>{

}
