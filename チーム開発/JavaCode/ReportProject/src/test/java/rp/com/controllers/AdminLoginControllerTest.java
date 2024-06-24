package rp.com.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	private MockHttpSession session;

	@BeforeEach
	public void prepareData() throws Exception {
		session = new MockHttpSession();

		Admin admin = new Admin();
		admin.setAdminEmail("test@example.com");
		admin.setAdminPassword("password123");

		// 正常ログイン
		when(adminService.loginAdmin(eq("test@example.com"), eq("password123"))).thenReturn(admin);

		// 誤うログイン
		when(adminService.loginAdmin(eq("Test3@example.com"), eq("Test3password"))).thenReturn(null);
	}

	// アドミンログイン画面表示テスト
	@Test
	public void testGetAdminLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/login");

		mockMvc.perform(request).andExpect(view().name("admin_login"));
	}

	// 正常のログインテスト
	@Test
	public void testAdminLoginProcess_CorrectInfo_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process")
				.param("adminEmail", "test@example.com").param("adminPassword", "password123").session(session);

		mockMvc.perform(request).andExpect(redirectedUrl("/admin/report/list"))
				.andExpect(request().sessionAttribute("loginAdminInfo", org.hamcrest.Matchers.notNullValue()));
	}

	// ログイン失敗テスト
	@Test
	public void testAdminLoginProcess_IncorrectInfo_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process")
				.param("adminEmail", "Test3@example.com").param("adminPassword", "Test3password").session(session);

		mockMvc.perform(request).andExpect(model().attribute("errorMessage", "メールアドレスまたはパスワードが間違っています"))
				.andExpect(view().name("admin_login"));
	}

	// パスワードが忘れたテスト
	@Test
	public void testGetForgotPasswordPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/forgot-password");

		mockMvc.perform(request).andExpect(view().name("admin_change_pw"));
	}
}
