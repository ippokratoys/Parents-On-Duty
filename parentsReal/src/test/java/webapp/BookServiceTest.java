package webapp;

/**
 * Created by thanasis on 2/7/2017.
 */
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import webapp.searchresult.ResultController;
import webapp.services.ResultService;

//@RunWith(SpringRunner.class)
//@WebMvcTest(value = ResultService.class , controllers = ResultController.class)
//public class BookServiceTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ResultService service;
//
//    @Test
//    public void resultRandomStringSearch() throws Exception {
//
////        when(service
////                .getResults("asdfsfd","1/1/2017","1/2/2017",-1,5,-1))
////                .thenReturn(null);
////        this.mockMvc.perform(get("/results")).andDo(print()).andExpect(status().isOk())
////                .andExpect(content().string(containsString("Αποτελέσματα")));
//
//    }
//}