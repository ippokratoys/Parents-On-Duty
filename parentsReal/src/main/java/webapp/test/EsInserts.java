package webapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Event;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.*;

import java.util.List;

/**
 * Created by thanasis on 23/6/2017.
 */
@Controller
public class EsInserts {

    @Autowired
    private EventSearchRepository eventSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping("/test/add")
    public String testAddPage(){
        List<Event> allEvents= (List<Event>) eventRepository.findAll();
        for (Event aEvent: allEvents){
            EventSearch eventSearch= new EventSearch(aEvent);
            eventSearchRepository.save(eventSearch);
        }
        return "redirect:/";
    }

    @RequestMapping("/test/search")
    public String testSearchPage(@RequestParam("name") String key){
        System.out.println(eventSearchRepository);
        List<EventSearch> restult= eventSearchRepository.findByName(key);
        for (EventSearch aEvent: restult) {
            System.out.print(aEvent.getName()+"\t");
            System.out.print(aEvent.getId()+"\t");
            System.out.print(aEvent.getType()+"\t");
            System.out.println("--------------------");
        }
        return "index";
    }

    @RequestMapping("/test/init")
    public String testPage(){
        return "index";
    }

}
