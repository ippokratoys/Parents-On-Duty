package webapp;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import webapp.homepage.HomePage;
import webapp.services.ResultService;

/**
 * Created by thanasis on 2/7/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstTest {

    @Autowired
    private HomePage homePageController;

    @Test
    public void contexLoads() throws Exception{
        Assertions.assertThat(homePageController).isNotNull();
    }
}
