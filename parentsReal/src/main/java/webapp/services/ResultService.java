package webapp.services;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventsgroupRepository;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by thanasis on 22/6/2017.
 */

@Service
public class ResultService {

    @Autowired
    EventsgroupRepository eventsgroupHandler;

    @Autowired
    private EventSearchRepository esEventSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<EventSearch> getResults(String searchterm){

        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchterm)
                .fuzziness(Fuzziness.AUTO)
                .field("name", 7)
                .field("description", 1)
                .field("type", 2)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .analyzer("greek");
        Iterable<EventSearch> resultsIter= esEventSearchRepository.search(myQuery);
        List <EventSearch> results= new ArrayList<EventSearch>();
        for (EventSearch aEventSearch :
                resultsIter) {
            results.add(aEventSearch);
        }
        System.out.println(results);
        return results;
    }

    public List<EventSearch> getResultsByUser(String searchterm, Customer customer){
        return getResults(searchterm);
    }
}
