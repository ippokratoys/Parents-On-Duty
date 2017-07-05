package webapp.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import webapp.database.CustomerPaymentHistory;

/**
 * Created by thanasis on 29/6/2017.
 */
public interface CustomerPaymentHistoryRepository extends CrudRepository<CustomerPaymentHistory,Integer>{
    @Query("select sum(payment.moneyPayed) "+
            "from CustomerPaymentHistory payment ")
    Integer customersMoneyPayed();

    @Query("select sum(payment.moneyPayed) - sum(payment.moneyGot) "+
            "from CustomerPaymentHistory payment ")
    Integer customerFromBonus();

}
