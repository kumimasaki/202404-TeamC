package rp.com.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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

	private MockHttpSession session;

	// テスト前に実行されるセットアップ
	@BeforeEach
	public void setUp() {
		session = new MockHttpSession();
		// 仮の管理者情報をセッションに追加
		session.setAttribute("loginAdminInfo", new Object());
	}

	// 管理者ログアウトのテスト
	@Test
	public void testAdminLogout() throws Exception {
		// セッションを持ってログアウトURLにアクセス
		mockMvc.perform(get("/admin/logout").session(session))
				// ログインページにリダイレクトする
				.andExpect(redirectedUrl("/admin/login"));
	}
}
