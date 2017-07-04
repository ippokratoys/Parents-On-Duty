package webapp.database.initializer;

import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.database.*;
import webapp.database.repositories.*;
import webapp.services.EventService;

import java.io.*;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by thanasis on 10/6/2017.
 */

@Service
public class CsvInserts {
//    @Autowired
//    PasswordEncoder passwordEncoder;

//    @Autowired
//    private static LoginRepository loginRepository;
//    @Autowired
//    private static CustomerRepository customerRepository;
//    @Autowired
//    private static OrganiserRepository organiserRepository;
    SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
    {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String loginCsvInsertions(LoginRepository loginRepository,CustomerRepository customerRepository,String csvFile){
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
//        EntityManager entitymanager = emfactory.createEntityManager();
//        entitymanager.getTransaction( ).begin( );
		/*here will insert to the login*/
		/*read the csv*/
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String line = "";
        String cvsSplitBy = ",";
        BufferedReader br = null;
        try {
//            br = new BufferedReader(new FileReader(csvFile,"utf-8"));
            Login adminlog = new Login();
            adminlog.setEmail("admin@admin.com");
            adminlog.setPwd(passwordEncoder.encode("123456"));
            adminlog.setRole("ADMIN");
            adminlog.setActive(true);
            loginRepository.save(adminlog);

            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(csvFile), "UTF8"));

            br.readLine();
            while ((line = br.readLine()) != null) {
				/*use comma as separator*/
                String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2]+"]");

				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
                Login log = new Login();
                log.setEmail(arr_in[0].trim());
                log.setPwd(passwordEncoder.encode(arr_in[1].trim()));
                log.setRole(arr_in[2].trim());
                log.setActive(true);
                if(log.getRole().equals("PARENT")){
                    Customer newCustomer=new Customer();
                    newCustomer.setLogin_email(log.getEmail());
                    newCustomer.setWallet( (new Random().nextInt(500)+75)*100 );
                    newCustomer.setPoints( (new Random().nextInt(500)+20)*100 );
                    newCustomer.setLat(new BigDecimal("38.018433") );
                    newCustomer.setLon(new BigDecimal("23.848057") );

                    if(customerRepository==null){
                        System.out.println("It's null customer");
                        System.out.println(customerRepository);
                        continue;
                    }
                    customerRepository.save(newCustomer);
                    System.out.println("A new Parent added");
                }
                System.out.println(log.toString());
                System.out.println(loginRepository.toString());
                loginRepository.save(log);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "/";
    }

    public void organiserCsvInsertions(LoginRepository loginRepository,OrganiserRepository organiserRepository,String csvFile){
		/*here will insert to the login*/
		/*read the csv*/
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(csvFile), "UTF8"));
            br.readLine();
            while ((line = br.readLine()) != null) {
				/*use comma as separator*/
                String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
//                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2+"]");

				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
                Login log = new Login();
                log.setEmail(arr_in[0]);
                log.setPwd(passwordEncoder.encode("123456"));
                log.setRole("ORGANISER");
                log.setActive(true);

                Organiser newOrganiser=new Organiser();
                newOrganiser.setLogin_email(arr_in[0]);
                try {
                    newOrganiser.setBirthdate( dateParser.parse(arr_in[1]) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newOrganiser.setName(arr_in[2]);
                newOrganiser.setTaxpayerId(arr_in[3]);
                newOrganiser.setSurname(arr_in[4]);

                organiserRepository.save(newOrganiser);
                loginRepository.save(log);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void eventsCsvInsertions(EventRepository eventRepository,
                                           String csvFile,
                                           OrganiserRepository organiserRepository,
                                           EventsgroupRepository eventsgroupRepository,
                                           LocationRepository locationRepository
                                           ){
        System.out.println(organiserRepository);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(csvFile), "UTF8"));
            br.readLine();
            while ((line = br.readLine()) != null) {
				/*use comma as separator*/
                String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
//                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2+"]");

				/*here create the objects to insert in the db*/
                Event newEvent=new Event();
                newEvent.setSpots( Math.abs(new Random().nextInt()%50)+10);
//                newEvent.setIdEvents(Integer.parseInt(arr_in[0]));
                try {
                    newEvent.setDay( dateParser.parse(arr_in[1]) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newEvent.setPrice( Integer.parseInt(arr_in[2].replace(".","") ) );
                //instead of this we must add the ellement from the csv
                String[] splitTime = arr_in[3].split(":");
                newEvent.setTime( new Time(Integer.parseInt(splitTime[0]),Integer.parseInt(splitTime[1]),0) );
                newEvent.setEventsgroup(eventsgroupRepository.findOne( Integer.parseInt(arr_in[4]) ));
                newEvent.setLocation(locationRepository.findOne(Integer.parseInt(arr_in[5])));
                newEvent.setOrganiser(organiserRepository.findOne(eventsgroupRepository.findOne( Integer.parseInt(arr_in[4]) ).getOrganiser().getLogin_email()));
                newEvent.setImportance(Integer.parseInt(arr_in[7]));
                newEvent.setSpots(Integer.parseInt(arr_in[8]));
                newEvent.setPrice( (int) (100 * Double.parseDouble(arr_in[9])) );

                eventRepository.save(newEvent);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void eventsgroupCsvInsertions(EventsgroupRepository eventsgroupRepository, String csvFile,
                                                OrganiserRepository organiserRepository
                                                ){
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";
            try {

                 br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(csvFile), "UTF8"));

                br.readLine();
                while ((line = br.readLine()) != null){
				/*use comma as separator*/
                    String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
//                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2+"]");

				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
//                    System.out.println(line);
                    Eventsgroup curEvent=new Eventsgroup();
                    curEvent.setIdEventsGroup(Integer.parseInt(arr_in[0]));
                    curEvent.setDescription(arr_in[1].trim());
                    curEvent.setName(arr_in[2].trim());
                    curEvent.setType(arr_in[3].trim());
                    curEvent.setOrganiser(organiserRepository.findOne(arr_in[4].trim()));
                    curEvent.setAgeFrom(3);
                    curEvent.setAgeTo(9);
                    Eventsgroup curEventdb= eventsgroupRepository.save(curEvent);
                    curEventdb.setImagePath("/file/event/"+String.valueOf(curEventdb.getIdEventsGroup())+".png");

                    eventsgroupRepository.save(curEventdb);
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (br != null) {
                    try {
                        br.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    public void bookSomeEvents(LoginRepository loginRepository,
                               CustomerRepository customerRepository,
                               EventRepository eventRepository,
                               BookEventRepository bookEventRepository,
                               EventService eventService
    ){
        Iterable<Customer> customerList = customerRepository.findAll();
        for (Customer oneCustomer :
                customerList){
            for (int i = 0; i < 100; i++) {
                Event event = eventRepository.findOne(i);
                try {
                    eventService.bookEvent(event,oneCustomer,1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void locationCsvInsertions(LocationRepository locationRepository,
                                      String csvFile,
                                      OrganiserRepository organiserRepository
    ){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(csvFile), "UTF8"));
            br.readLine();
            int i=0;
            while ((line = br.readLine()) != null) {
                i++;
				/*use comma as separator*/
                String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
//                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2+"]");

				/*here create the objects to insert in the db*/

				Location newLoc=new Location();
//				newLoc.setId(i);
				newLoc.setAddress(arr_in[0]);
				newLoc.setImagePath("/file/location/"+i+".png");
				newLoc.setCertificatePath("/file/certificate/"+i+".png");
				newLoc.setPostcode(arr_in[1]);
				newLoc.setName(arr_in[2]);
				newLoc.setLocationOwner(organiserRepository.findOne("edutnall3@github.io"));
				newLoc.setLat( new BigDecimal(arr_in[4]) );
				newLoc.setLon( new BigDecimal(arr_in[5]) );
				if( i==8 ){
                    newLoc.setValidUntil(null);
    				newLoc.setCertificateChecked(false);
                }else{
                    newLoc.setValidUntil(new Date());
                    newLoc.setCertificateChecked(true);
                }
				locationRepository.save(newLoc);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doInserts(){
//        loginCsvInsertions("../stp_back_end/parents.csv");
//        organiserCsvInsertions("../stp_back_end/organiser.csv");
//        Location_CSV_Inseriton("/Users/Giorgos/Desktop/dataset10/locations10.csv");
    }

}
