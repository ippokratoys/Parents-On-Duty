package stp_back_end;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*for csv files*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Insertions {
	/*from CSV to Login DB table insertion*/
	public static void Login_CSV_Insertion(String csvFile){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		
		/*here will insert to the login*/
		/*read the csv*/
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
				System.out.println("Country [EMAIL = " + arr_in[0] + " , CODE = " + arr_in[1] + "]");
				
				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
				Login log = new Login();
				log.setEmail(arr_in[0]);
				log.setPwd(arr_in[1]);
				entitymanager.persist(log);
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
		/*in the db storing*/
		entitymanager.getTransaction( ).commit( );
		
		/*close the manager*/
		entitymanager.close( );
		emfactory.close( );
	}
	
	/*from CSV to Location DB table insertion*/
	public static void Location_CSV_Inseriton(String csvFile){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		
		/*here will insert to the login*/
		/*read the csv*/
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
				System.out.println("Adress [Addr = " + arr_in[2] + " , Poscode = " + arr_in[1] + " , Name = " + arr_in[0] + " ,OwnerEmail = " + arr_in[3] + "]");
				
				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
				Location loc = new Location();
				loc.setAddress(arr_in[2]);
				loc.setPostcode(arr_in[1]);
				loc.setName(arr_in[0]);
				
				/*here create a locaiton owner for the foreighn key*/
				Locationowner locow = new Locationowner();
				locow.setLogin_email(arr_in[3]);
				String[] name_arr = arr_in[0].split(" ");
				locow.setName(name_arr[0]);
				locow.setSurname(name_arr[1]);
				
				/*refenrece*/
				loc.setLocationowner(locow);
				entitymanager.persist(loc);
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
		/*in the db storing*/
		entitymanager.getTransaction( ).commit( );
		
		/*close the manager*/
		entitymanager.close( );
		emfactory.close( );
	}
	
	/*from CSV to LocationOwner DB table insertion*/
	public static void Locationowner_CSV_Insertion(String csvFile){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		
		/*here will insert to the login*/
		/*read the csv*/
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
				System.out.println("[Name = " + arr_in[0] + " , Surname = " + arr_in[1] + " , EamilOwner = " + arr_in[2] + "]");
				
				/*here create the objects to insert in the db*/
				/*insert the info email,pwd*/
				Locationowner locow = new Locationowner();
				locow.setName(arr_in[0]);
				locow.setSurname(arr_in[1]);
				locow.setLogin_email(arr_in[2]);
				entitymanager.persist(locow);
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
		/*in the db storing*/
		entitymanager.getTransaction( ).commit( );
		
		/*close the manager*/
		entitymanager.close( );
		emfactory.close( );
	}
	
	/*from CSV to Location DB table customer*/
	public static void Cusotmer_CSV_Insertion(String csvFile){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("stp_back_end");
		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		
		Customer cust = new Customer();
		cust.setWallet(0);
		cust.setPoints(0);
		cust.setLogin_email("aaa@hotmail.com");
		entitymanager.persist(cust);
		/*in the db storing*/
		entitymanager.getTransaction( ).commit( );
		/*close the manager*/
		entitymanager.close( );
		emfactory.close( );
	}
	
	
	/*our main function for the insertions executions*/
	public static void main( String[ ] args ) {
		/*inserting in the login*/
		Login_CSV_Insertion("/Users/Giorgos/Desktop/dataset10/login100000.csv");
		
		/*inserting in the owner*/
		//Locationowner_CSV_Insertion("/Users/Giorgos/Desktop/dataset10/owner10.csv");
		
		//Cusotmer_CSV_Insertion("daasdasdasdasdas");
		
		/*inserting in the location*/
		Location_CSV_Inseriton("/Users/Giorgos/Desktop/dataset10/locations10.csv");
	}
}
