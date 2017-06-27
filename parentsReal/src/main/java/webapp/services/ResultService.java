package webapp.services;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventsgroupRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by thanasis on 22/6/2017.
 */

@Service
public class ResultService {

    SimpleDateFormat dateParser = new SimpleDateFormat("dd/mm/yyyy");
    {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/mm/yyyy");
    }

    @Autowired
    EventsgroupRepository eventsgroupHandler;

    @Autowired
    private EventSearchRepository esEventSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<EventSearch> getResults(String searchterm, String fromDate, String toDate, int price, int age, int distance){

        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchterm)
                .field("name^3")
                .field("description^1")
                .field("type^2")
                .fuzziness(Fuzziness.TWO)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .zeroTermsQuery(MatchQueryBuilder.ZeroTermsQuery.ALL)
                .analyzer("greek");


        RangeQueryBuilder rangeAge = QueryBuilders.rangeQuery("price").from(100).to(1500);
        RangeQueryBuilder rangeDate = null;

        try {
            rangeDate = QueryBuilders.rangeQuery("day")
                    .from( dateParser.parse("1/1/2017") )
                    .to( dateParser.parse("15/1/2017") );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BoolQueryBuilder finalQuery = new BoolQueryBuilder()
                .must(rangeAge)
                .must(rangeDate);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(myQuery)
                .withFilter(finalQuery)
                .build();

        Iterable<EventSearch> resultsIter = elasticsearchTemplate.queryForList(searchQuery,EventSearch.class);

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
