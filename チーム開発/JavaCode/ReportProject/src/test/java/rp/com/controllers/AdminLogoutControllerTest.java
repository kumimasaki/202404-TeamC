package rp.com.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLogoutControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// Sessionが存在する際のログアウトテスト
	@Test
	public void testLogoutSuccessWithSession() throws Exception {
		// 新しいセッションを作成する
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loginAdminInfo", new Object());
		// アドミンログイン画面にリダイレクトする
		mockMvc.perform(post("/admin/logout").session(session)).andExpect(redirectedUrl("/admin/login"));
	}

}
