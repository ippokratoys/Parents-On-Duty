package webapp.database.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import webapp.database.Eventsgroup;

import java.util.Date;
import java.util.List;

/**
 * Created by thanasis on 26/6/2017.
 */

@Repository
public interface EventSearchRepository extends ElasticsearchRepository<EventSearch,Integer> {
    EventSearch save(EventSearch eventSearch);
    Page<EventSearch> findByDescription(String description, Pageable pageable);
    List<EventSearch> findByName(String name);
    List<EventSearch> findEventSearchesByDayAfterAndImportanceAfter(Date date, int importance);
}