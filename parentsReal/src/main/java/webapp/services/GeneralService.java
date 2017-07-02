package webapp.services;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ilias on 2/7/2017.
 */

@Service
public class GeneralService {

    public String dayOfWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String day ="";
        switch (dayOfWeek){
            case 0:
                day="Κυριακή";
                break;
            case 1:
                day="Δευτέρα";
                break;
            case 2:
                day="Τρίτη";
                break;
            case 3:
                day = "Τετάρτη";
                break;
            case 4:
                day = "Πέμπτη";
                break;
            case 5:
                day = "Παρασκευή";
                break;
            case 6:
                day = "Κυριακή";
                break;
        }

        return day;
    }

    public int dayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        return dayOfMonth;
    }

    public  String shortDate(Date date){
        String shortDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        switch (month){
            case 0:
                shortDate = "Ιανουάριος";
                break;
            case 1:
                shortDate = "Φεβρουάριος";
                break;
            case 2:
                shortDate = "Μάρτιος";
                break;
            case 3:
                shortDate = "Απρίλιος";
                break;
            case 4:
                shortDate = "Μάιος";
                break;
            case 5:
                shortDate = "Ιούνιος";
                break;
            case 6:
                shortDate = "Ιούλιος";
                break;
            case 7:
                shortDate = "Αύγουστος";
                break;
            case 8:
                shortDate = "Σεπτέμβριος";
                break;
            case 9:
                shortDate = "Οκτώβριος";
                break;
            case 10:
                shortDate = "Νοέμβριος";
                break;
            case 11:
                shortDate = "Δεκέμβριος";
                break;
        }

        int year = cal.get(Calendar.YEAR);

        shortDate += ", "+year;

        return shortDate;
    }


}
