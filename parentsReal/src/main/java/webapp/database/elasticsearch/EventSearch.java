package webapp.database.elasticsearch;

import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.format.annotation.DateTimeFormat;
import webapp.database.Event;
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
//@Document(indexName = "events_index" , type = "eventsgroup")
public class EventSearch implements Serializable {
    private static final long serialVersionUID = 1L;

    public EventSearch(){

    }

    public EventSearch(Event event) {
        this.id=event.getIdEvents();
        this.day=event.getDay();
        this.time=event.getTime();
        this.price=event.getPrice();
        this.lat=event.getLocation().getLat();
        this.lon=event.getLocation().getLon();
        this.location= new GeoPoint(lat.doubleValue(),lon.doubleValue());
        this.importance=event.getImportance();
        this.name=event.getEventsgroup().getName();
        this.type=event.getEventsgroup().getType();
        this.description=event.getEventsgroup().getDescription();
        this.ageFrom=event.getEventsgroup().getAgeFrom();
        this.ageTo=event.getEventsgroup().getAgeTo();
        this.address=event.getLocation().getAddress();
        this.imagePath=event.getEventsgroup().getImagePath();
    }

    //this annotation is for the elastic search key used in repositories
    @org.springframework.data.annotation.Id
    private int id;

    @Field(type = FieldType.Date)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date day;

    private Time time;

    @Field(type = FieldType.Integer)
    private int price;

    @GeoPointField
    GeoPoint location;

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    private BigDecimal lat;

    private BigDecimal lon;

    private int importance;

    @Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "greek", searchAnalyzer = "greek" )
    private String name;

    @Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "greek", searchAnalyzer = "greek" )
    private String type;

    @Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "greek", searchAnalyzer = "greek" )
    private String description;

    private int ageFrom;

    private int ageTo;

    private String address;

    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath= imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
