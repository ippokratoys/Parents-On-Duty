package webapp.searchresult;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EsEventsgroupRepository;
import webapp.database.repositories.EventsgroupRepository;

import java.util.ArrayList;
import java.util.Iterator;
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
    private EsEventsgroupRepository esEventsgroupRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    List<Eventsgroup> getResults(String searchterm){


        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchterm)
                .fuzziness(Fuzziness.AUTO)
                .field("name", 7)
                .field("description", 1)
                .field("type", 2)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .analyzer("greek");
        Iterable<Eventsgroup> resultsIter= esEventsgroupRepository.search(myQuery);
        List <Eventsgroup> results=null;
        ArrayList <Integer> ids=new ArrayList<>();
        for (Eventsgroup aEventsgroup :
                resultsIter) {
            ids.add(aEventsgroup.getIdEventsGroup());
        }
        results = (List<Eventsgroup>) eventsgroupHandler.findAll(ids);

        return results;
    }

    List getResultsByUser(String address){
        List<Eventsgroup> results=eventsgroupHandler.findByName(address);
        String allWords[]=address.split(" ");
        for (String oneWord:allWords) {
            results.addAll(eventsgroupHandler.findByNameContaining(oneWord));
        }
        return results;
    }
}
