package webapp.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import webapp.database.OrganiserPaymentHistory;

/**
 * Created by thanasis on 29/6/2017.
 */
public interface OrganiserPaymentHistoryRepository extends CrudRepository<OrganiserPaymentHistory,Integer>{
    @Query("select sum(payment.moneyPayed) "+
            "from OrganiserPaymentHistory payment "+
            "where payment.message like '%to promote the event with id%'")
    Integer organisersMoneyIn();

    @Query("select sum(payment.moneyPayed) "+
            "from OrganiserPaymentHistory payment "+
            "where payment.moneyPayed<0")
    Integer organisersMoneyGiven();


}
