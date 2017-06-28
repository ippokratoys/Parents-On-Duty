package webapp.services;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventsgroupRepository;
import org.elasticsearch.search.sort.SortBuilders;
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

    int fromPrice(int price)
    {
        if(price == 1)
            return 0;
        else if (price == 2)
            return 100;
        else if (price == 3)
            return 500;
        else if (price == 4)
            return 1000;
        else if (price == 5)
            return 2000;
        else
            return 3000;
    }
    int toPrice(int price)
    {
        if(price == 1)
            return 0;
        else if (price == 2)
            return 500;
        else if (price == 3)
            return 1000;
        else if (price == 4)
            return 2000;
        else if (price == 5)
            return 3000;
        else
            return 999999999;
    }
    @Autowired
    EventsgroupRepository eventsgroupHandler;
    @Autowired
    private EventSearchRepository esEventSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<EventSearch> getResults(String searchTerm, String fromDate, String toDate, int price, int age, int dist){

        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchTerm)
                .field("name^3")
                .field("description^1")
                .field("type^2")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .fuzziness(Fuzziness.TWO)
                .zeroTermsQuery(MatchQueryBuilder.ZeroTermsQuery.ALL)
                .analyzer("greek");
        BoolQueryBuilder finalQuery = new BoolQueryBuilder();


        /*
        if(fromDate != null) {
            RangeQueryBuilder rangeDate = null;
            try {
                rangeDate = QueryBuilders.rangeQuery("day").from(dateParser.parse(fromDate)).to(dateParser.parse(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finalQuery = finalQuery.filter(rangeDate);
        }
        */


        int fromP = fromPrice(price);
        int toP = toPrice(price);

       if(fromP > 0){
            RangeQueryBuilder rangePrice = QueryBuilders.rangeQuery("price").from(fromP).to(toP);
            finalQuery = finalQuery.filter(rangePrice);
        }

        /*
       QueryBuilder qb = QueryBuilders.geoDistanceQuery("lat,lon")
                .point(37.97945, 23.71622)
                .distance(dist, DistanceUnit.KILOMETERS);
       finalQuery.must(qb);
       */

       SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(myQuery)
//                .withFilter(finalQuery)
                .build();


       Iterable<EventSearch> resultsIter = elasticsearchTemplate.queryForList(searchQuery,EventSearch.class);

       List <EventSearch> results= new ArrayList<EventSearch>();
       for (EventSearch aEventSearch :
                resultsIter) {
            if(age >= 0  ){
                if( aEventSearch.getAgeTo()>=age && aEventSearch.getAgeFrom()<=age){
                    results.add(aEventSearch);
                }
            }else {
                results.add(aEventSearch);
            }
        }
       System.out.println(results);
       return results;
    }


    public List<EventSearch> getResultsByUser(String searchTerm, String fromDate, String toDate, int price, int age, int distance, Customer customer){
        return getResults(searchTerm, fromDate,toDate,price,age, distance);
    }
}
