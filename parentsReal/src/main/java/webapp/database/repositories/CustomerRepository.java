package webapp.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.database.*;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{
    @Query("select sum(customer.points) "+
            "from Customer customer ")
    Integer customerMoneyIn();

}
