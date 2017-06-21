package webapp.database.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import webapp.database.Eventsgroup;

import java.util.List;

/**
 * Created by thanasis on 21/6/2017.
 */

@Repository
public interface EsEventsgroupRepository extends ElasticsearchRepository<Eventsgroup,Integer>{

    Eventsgroup save(Eventsgroup eventsgroup);
//    void delete(Eventsgroup eventsgroup);
//    Eventsgroup findOne(String id);
//    Iterable<Eventsgroup> findAll();


//    Page<Eventsgroup> findByAuthor(String author, Pageable pageable);

    Page<Eventsgroup> findByDescription(String description, Pageable pageable);
    List<Eventsgroup> findByName(String name);
}
