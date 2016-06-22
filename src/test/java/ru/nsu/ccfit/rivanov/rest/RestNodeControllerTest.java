package ru.nsu.ccfit.rivanov.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.nsu.ccfit.rivanov.Application;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.jpa.NodeRepository;

import java.math.BigInteger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class RestNodeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RestNodeController nodeController;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void getNodeReturnsNode() throws Exception {
        NodeRepository nodeRepository = mock(NodeRepository.class);
        Node node = new Node();
        node.setId(new BigInteger("1"));
        node.setUser("testuser");
        when(nodeRepository.findOne(new BigInteger("1"))).thenReturn(node)
        mockMvc.perform(get("/nodes/1"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath())
    }

    @Test
    public void saveNode() throws Exception {

    }

    @Test
    public void deleteNode() throws Exception {

    }

    @Test
    public void updateNode() throws Exception {

    }

}