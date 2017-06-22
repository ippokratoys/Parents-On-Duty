package webapp.searchresult;

import org.elasticsearch.common.unit.Fuzziness;
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

<<<<<<< HEAD
    List<Eventsgroup> getResults(String address){
//        List<Eventsgroup> results=null;


        List<Eventsgroup> results=eventsgroupHandler.findByName(address);
        String allWords[]=address.split(" ");
        for (String oneWord:allWords) {
            results.addAll(eventsgroupHandler.findByNameContaining(oneWord));
=======
    List<Eventsgroup> getResults(String searchterm){


        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchterm, "name^10", "description^1", "type^2")
                .fuzziness(Fuzziness.AUTO)
                .analyzer("standard");
        Iterable<Eventsgroup> resultsIter= esEventsgroupRepository.search(myQuery);
        List <Eventsgroup> results=null;
        ArrayList <Integer> ids=new ArrayList<>();
        for (Eventsgroup aEventsgroup :
                resultsIter) {
            ids.add(aEventsgroup.getIdEventsGroup());
>>>>>>> fb4a62e63668aa83ddd138d00e6b12f2142e83b8
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
