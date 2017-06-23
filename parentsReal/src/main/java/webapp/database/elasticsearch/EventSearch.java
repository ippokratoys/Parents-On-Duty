package webapp.database.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * Created by thanasis on 23/6/2017.
 */

public class EventSearch implements Serializable {
    private static final long serialVersionUID = 1L;

//    @GeneratedValue(strategy= GenerationType.AUTO)
    //this annotation is for the elastic search key used in repositories
    @org.springframework.data.annotation.Id
    int id;
}
