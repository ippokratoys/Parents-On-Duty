package webapp;

import webapp.database.*;
import webapp.database.initializer.Insertions;
import webapp.database.repositories.LoginRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages="webapp.database.*")
@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	@Autowired
	private static LoginRepository loginRepository;
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(Application.class);
    }
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot");

        // String[] beanNames = ctx.getBeanDefinitionNames();
        // Arrays.sort(beanNames);
        // for (String beanName : beanNames) {
        //     System.out.println(beanName);
        // }
        
//		/*inserting in the login*/
//        Insertions.Login_CSV_Insertion("../stp_back_end/login100000.csv");
//
//		/*inserting in the location*/
//        Insertions.Location_CSV_Inseriton("../stp_back_end/locations10.csv");

		
    }

}
