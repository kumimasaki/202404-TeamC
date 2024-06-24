package rp.com.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;
import rp.com.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AdminService adminService;

    // サービスクラスを使ったデータを作成
    @BeforeEach
    public void prepareData() {
        // 管理者情報をモックする
//        Admin admin = 
        		
        		
    }
}