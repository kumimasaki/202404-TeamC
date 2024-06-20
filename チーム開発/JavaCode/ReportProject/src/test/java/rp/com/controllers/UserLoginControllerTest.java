package rp.com.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import rp.com.services.UserService;
import rp.com.models.entity.Users;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // サービスクラスを使ったデータを作成
    @BeforeEach
    public void prepareData() {
        // ログイン成功：email "test@example.com", password "password123" ⇨Usersオブジェクト
        Users user = new Users();
        user.setUserEmail("test@example.com");
        user.setUserPassword("password123");
        when(userService.loginCheck("test@example.com", "password123")).thenReturn(user);

        // ログイン失敗：email "wrong@example.com", password "wrongpass" ⇨null
        when(userService.loginCheck(eq("wrong@example.com"), eq("wrongpass"))).thenReturn(null);
    }

    // ログイン画面を正しく取得するテスト
    @Test
    public void testGetUserLoginPage_Succeed() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/login");

        mockMvc.perform(request)
            .andExpect(view().name("user_login.html"));
    }

    // ログインが成功した場合のテスト
    @Test
    public void testUserLoginProcess_CorrectInfo_Succeed() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .post("/user/login/process")
            .param("userEmail", "test@example.com")
            .param("userPassword", "password123");

        // ログインが成功したらリダイレクトされることと、セッションにユーザー情報が保存されていることをテスト
        mockMvc.perform(request)
            .andExpect(view().name("redirect:/user/report/list"));
    }

    // ログインが失敗した場合のテスト
    @Test
    public void testUserLoginProcess_IncorrectInfo_Fail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .post("/user/login/process")
            .param("userEmail", "wrong@example.com")
            .param("userPassword", "wrongpass");

        // ログインが失敗したらエラーメッセージが表示され、ログイン画面に留まることをテスト
        mockMvc.perform(request)
            .andExpect(model().attribute("errorMessage", "メールアドレスまたはパスワードが正しくありません"))
            .andExpect(view().name("user_login.html"));
    }
}