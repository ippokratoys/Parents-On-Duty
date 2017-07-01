package webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LoginRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import webapp.database.repositories.OrganiserRepository;

@EnableJpaRepositories(basePackages="webapp.database.*")
@ComponentScan("webapp.database.initializer.*")
@EnableElasticsearchRepositories("webapp.database.elasticsearch.*")
@ComponentScan("webapp.*")
@SpringBootApplication
@EnableWebSecurity
public class Application extends WebMvcConfigurerAdapter{

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot");
        // String[] beanNames = ctx.getBeanDefinitionNames();
        // Arrays.sort(beanNames);
        // for (String beanName : beanNames) {
        //     System.out.println(beanName);
        // }
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/user/wallet").setViewName("profile/parent/wallet");
//        registry.addViewController("/user/wallet").setViewName("profile/parent/wallet");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//        
////        registry.addViewController("/hello").setViewName("helloworld");
////        registry.addRedirectViewController("/home", "/hello");
////        registry.addStatusController("/detail", HttpStatus.BAD_REQUEST);        
//    }
}
