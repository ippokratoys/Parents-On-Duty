package webapp.database.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import webapp.database.Location;
import webapp.database.Organiser;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * Created by thanasis on 23/6/2017.
 */
@Document(indexName = "events_search")
public class EventSearch implements Serializable {
    private static final long serialVersionUID = 1L;

    //this annotation is for the elastic search key used in repositories
    @org.springframework.data.annotation.Id
    private int id;

    private Date day;

    private Time time;

    private int price;

    private BigDecimal lat;

    private BigDecimal lon;

    private int importance;

    private String name;

    private String type;

    private String description;

}
