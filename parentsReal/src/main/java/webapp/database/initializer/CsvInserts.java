package webapp.database.initializer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.database.*;
import webapp.database.repositories.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    SimpleDateFormat dateParser = new SimpleDateFormat("dd/mm/yyyy");
    {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/mm/yyyy");
    }

    public String loginCsvInsertions(LoginRepository loginRepository,CustomerRepository customerRepository,String csvFile){
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
//        EntityManager entitymanager = emfactory.createEntityManager();
//        entitymanager.getTransaction( ).begin( );
		/*here will insert to the login*/
		/*read the csv*/
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
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
                    newCustomer.setWallet( new Random().nextInt(500)+20 );
                    newCustomer.setPoints( new Random().nextInt(500)+20 );
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
            br = new BufferedReader(new FileReader(csvFile));
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
            br = new BufferedReader(new FileReader(csvFile));
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
                newEvent.setPrice( Double.parseDouble(arr_in[2]) );
                //instead of this we must add the ellement from the csvs
                newEvent.setTime( new Time(8,1,2) );
                newEvent.setEventsgroup(eventsgroupRepository.findOne( Integer.parseInt(arr_in[4]) ));
                newEvent.setLocation(locationRepository.findOne(Integer.parseInt(arr_in[5])));
                newEvent.setOrganiser(organiserRepository.findOne(eventsgroupRepository.findOne( Integer.parseInt(arr_in[4]) ).getOrganiser().getLogin_email()));
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
                br = new BufferedReader(new FileReader(csvFile));
                br.readLine();
                while ((line = br.readLine()) != null){
				/*use comma as separator*/
                    String[] arr_in = line.split(cvsSplitBy);

				/*print the data*/
//                System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] +" , ROLE = "+arr_in[2+"]");

				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
                    Eventsgroup curEvent=new Eventsgroup();
//                    curEvent.setIdEventsGroup(Integer.parseInt(arr_in[0]));
                    curEvent.setDescription(arr_in[1]);
                    curEvent.setName(arr_in[2]);
                    curEvent.setType(arr_in[3]);
                    curEvent.setOrganiser(organiserRepository.findOne(arr_in[4]));

                    eventsgroupRepository.save(curEvent);

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

    public void locationCsvInsertions(LocationRepository locationRepository,
                                      String csvFile,
                                      OrganiserRepository organiserRepository
    ){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
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
				newLoc.setPostcode(arr_in[1]);
				newLoc.setName(arr_in[2]);
				newLoc.setLocationOwner(organiserRepository.findOne("edutnall3@github.io"));
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
