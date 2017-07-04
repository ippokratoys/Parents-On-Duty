package webapp.services;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Range;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventsgroupRepository;
import org.elasticsearch.search.sort.SortBuilders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Filter;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by thanasis on 22/6/2017.
 */

@Service
public class ResultService {

    SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
    {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
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
                .fuzziness(Fuzziness.ONE)
                .zeroTermsQuery(MatchQueryBuilder.ZeroTermsQuery.ALL)
                .analyzer("greek");
        BoolQueryBuilder finalQuery = new BoolQueryBuilder();


        RangeQueryBuilder rangeDate = null;
        if(fromDate != null) {

            try {
                rangeDate = QueryBuilders.rangeQuery("day").from(dateParser.parse(fromDate)).to(dateParser.parse(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else{
            Date date = new Date();
            rangeDate = QueryBuilders.rangeQuery("day").from(date);
        }

        finalQuery = finalQuery.filter(rangeDate);


        int fromP = fromPrice(price);
        int toP = toPrice(price);

       if(fromP > 0){
            RangeQueryBuilder rangePrice = QueryBuilders.rangeQuery("price").from(fromP).to(toP);
            finalQuery = finalQuery.filter(rangePrice);
        }


        if(dist>0) {
//            CriteriaQuery distanceCriteria= new CriteriaQuery(
//                    new Criteria("location").within(
//                            new GeoPoint(37.974535, 23.775358),String.valueOf(dist)+"km"));
            QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
                    .point(37.97945, 23.71622)
                    .distance(dist, DistanceUnit.KILOMETERS);
            finalQuery.must(qb);
        }


       SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(myQuery)
                .withPageable(new PageRequest(0, 50))
                .withFilter(finalQuery)
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

    public Iterable<EventSearch> recommendedEvents(){
        Iterable <EventSearch> recommended;

        Date date = new Date();
        System.out.println(date.toString());
        BoolQueryBuilder finalQuery = new BoolQueryBuilder();

        RangeQueryBuilder rangeDate = null;
        rangeDate = QueryBuilders.rangeQuery("day").from(date);
        finalQuery = finalQuery.must(rangeDate);

        RangeQueryBuilder importance = null;
        importance = QueryBuilders.rangeQuery("importance").from(2);
        finalQuery = finalQuery.must(importance);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(finalQuery)
                .withSort(SortBuilders.fieldSort("importance").order(SortOrder.DESC).ignoreUnmapped(true))
                .build();

        recommended = elasticsearchTemplate.queryForList(searchQuery, EventSearch.class);
        System.out.println(recommended.toString());
        System.out.println(recommended.toString());
        for (EventSearch aResult :
                recommended) {
            System.out.println(aResult.getName());
        }
        return recommended;
    }

    public List<EventSearch> getResultsByUser(String searchTerm, String fromDate, String toDate, int price, int age, int dist, double lat, double lon){

        QueryBuilder myQuery= QueryBuilders.multiMatchQuery(searchTerm)
                .field("name^3")
                .field("description^1")
                .field("type^2")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .fuzziness(Fuzziness.TWO)
                .zeroTermsQuery(MatchQueryBuilder.ZeroTermsQuery.ALL)
                .analyzer("greek");
        BoolQueryBuilder finalQuery = new BoolQueryBuilder();



        if(fromDate != null) {
            RangeQueryBuilder rangeDate = null;
            try {
                rangeDate = QueryBuilders.rangeQuery("day").from(dateParser.parse(fromDate)).to(dateParser.parse(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finalQuery = finalQuery.filter(rangeDate);
        }



        int fromP = fromPrice(price);
        int toP = toPrice(price);

        if(fromP > 0){
            RangeQueryBuilder rangePrice = QueryBuilders.rangeQuery("price").from(fromP).to(toP);
            finalQuery = finalQuery.filter(rangePrice);
        }

        if(dist>0) {
//            CriteriaQuery distanceCriteria= new CriteriaQuery(
//                    new Criteria("location").within(
//                            new GeoPoint(37.974535, 23.775358),String.valueOf(dist)+"km"));
            QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
                    .point(lat, lon)
                    .distance(dist, DistanceUnit.KILOMETERS);
            finalQuery.must(qb);
        }


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(myQuery)
                .withPageable(new PageRequest(0, 50))
                .withFilter(finalQuery)
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
}
