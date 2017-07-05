package webapp.services;

import jdk.nashorn.internal.parser.DateParser;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            case 1:
                day="Κυριακή";
                break;
            case 2:
                day="Δευτέρα";
                break;
            case 3:
                day="Τρίτη";
                break;
            case 4:
                day = "Τετάρτη";
                break;
            case 5:
                day = "Πέμπτη";
                break;
            case 6:
                day = "Παρασκευή";
                break;
            case 7:
                day = "Σάββατο";
                break;
        }

        return day;
    }

    public int dayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
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

    public String transformDateRange(String badRange){
        SimpleDateFormat greekDateParser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat otherDateParser = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("badRange = [" + badRange + "]");

        if(badRange==null || badRange.trim().equals("") || badRange.equals(" ")){
            return "";
        }

        String[] dates = badRange.split("-");
        Date from=null;
        Date to=null;
        try {
            from = greekDateParser.parse(dates[0]);
            if(dates.length>1){
                to = greekDateParser.parse(dates[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        if(to==null){
            return otherDateParser.format(from);
        }
        System.out.println(otherDateParser.format(from) + " - " + otherDateParser.format(to));
        return otherDateParser.format(from) + " - " + otherDateParser.format(to);
    }
}
