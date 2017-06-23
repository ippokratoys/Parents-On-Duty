package webapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EsEventsgroupRepository;
import webapp.database.repositories.*;

import java.util.List;

/**
 * Created by thanasis on 23/6/2017.
 */
@Controller
public class EsInserts {

    @Autowired
    private EsEventsgroupRepository esEventsgroupRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EventsgroupRepository eventsgroupHandler;

    @RequestMapping("/test/add")
    public String testAddPage(){
        List<Eventsgroup> allEvents= (List<Eventsgroup>) eventsgroupHandler.findAll();
        for (Eventsgroup aEvent: allEvents) {
            //i make it null because else i get stack overflow(because of one to one with event)
            aEvent.setOrganiser(null);
            aEvent.setEvents(null);
            esEventsgroupRepository.save(aEvent);
        }
        return "redirect:/";
    }

    @RequestMapping("/test/search")
    public String testSearchPage(@RequestParam("name") String key){
        System.out.println(esEventsgroupRepository);
        List<Eventsgroup> restult= esEventsgroupRepository.findByName(key);
        for (Eventsgroup aEvent: restult) {
            System.out.print(aEvent.getName()+"\t");
            System.out.print(aEvent.getIdEventsGroup()+"\t");
            System.out.print(aEvent.getType()+"\t");
            System.out.println("--------------------");
        }
        return "index";
    }

    @RequestMapping("/test/init")
    public String testPage(){
//		Eventsgroup eventsgroup=new Eventsgroup(1,"Paidikh mou xara","asteio","eee ta exoume pei na xame na legame kai na xame na paoume ",null,null);
//		System.out.println(eventsgroup);
//		System.out.println(eventsgroup.toString());
//		esEventsgroupRepository.save(eventsgroup);
        return "index";
    }

}
