package webapp.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by thanasis on 4/7/2017.
 */
@Entity
public class AdminTable implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    int id;

    int givenPoints;

    int ourPointsFromEvents;

    int ourPointsFromPromotion;

    public AdminTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGivenPoints() {
        return givenPoints;
    }

    public void setGivenPoints(int givenPoints) {
        this.givenPoints = givenPoints;
    }

    public int getOurPointsFromEvents() {
        return ourPointsFromEvents;
    }

    public void setOurPointsFromEvents(int ourPointsFromEvents) {
        this.ourPointsFromEvents = ourPointsFromEvents;
    }

    public int getOurPointsFromPromotion() {
        return ourPointsFromPromotion;
    }

    public void setOurPointsFromPromotion(int ourPointsFromPromotion) {
        this.ourPointsFromPromotion = ourPointsFromPromotion;
    }
}

