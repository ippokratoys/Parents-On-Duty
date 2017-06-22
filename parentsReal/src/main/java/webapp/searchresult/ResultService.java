package webapp.searchresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EsEventsgroupRepository;
import webapp.database.repositories.EventsgroupRepository;

import java.util.List;

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

    List<Eventsgroup> getResults(String address){
//        List<Eventsgroup> results=null;


        List<Eventsgroup> results=eventsgroupHandler.findByName(address);
        String allWords[]=address.split(" ");
        for (String oneWord:allWords) {
            results.addAll(eventsgroupHandler.findByNameContaining(oneWord));
        }
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
