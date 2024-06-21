package rp.com.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import rp.com.services.AdminService;

@SpringBootTest // SpringBootでテストする
@AutoConfigureMockMvc // 自動にMockMvcを設定する
public class AdminRegisterControllerTest {

	@Autowired
	private MockMvc mockMvc;// MockMvcをプロジェクトに注入する

	@MockBean // AdminServiceはMockMvcの対象とする
	private AdminService adminService;

	@BeforeEach // 最初にデータを規定する
	public void prepareData() throws Exception {
		// AdminServiceのcreateAdmin方法を模倣し、保存の異常を投げる
		doThrow(new IOException("アイコン保存中にエラーが発生しました。")).when(adminService).createAdmin(eq("Test1User"),
				eq("Test1@example.com"), eq("password123"), any(MockMultipartFile.class), eq("password123"));

		// AdminServiceのcreateAdmin方法を模倣し、実行中の異常を投げる
		doThrow(new RuntimeException("登録エラー")).when(adminService).createAdmin(eq("Test2User"), eq("Test2@example.com"),
				eq("password123"), any(MockMultipartFile.class), eq("password123"));
	}

	@Test // アドミン登録画面の表示をテスト
	public void testGetAdminRegisterPage_Succeed() throws Exception {
		// ゲッター請求を構築する
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/register");
		// 画面名称が"admin_register.html"
		mockMvc.perform(request).andExpect(view().name("admin_register.html"));
	}

	@Test // アドミン登録成功テスト
	public void testAdminRegisterProcess_CorrectInfo_Succeed() throws Exception {

		MockMultipartFile adminIcon = new MockMultipartFile("adminIcon", "test_icon.png", "image/png",
				"icon content".getBytes());

		RequestBuilder request = MockMvcRequestBuilders.multipart("/admin/register/process").file(adminIcon)
				.param("adminName", "testUser").param("adminEmail", "test@example.com")
				.param("adminPassword", "password123").param("confirmPassword", "password123");
		// アドミン登録が成功したら、ログイン画面に遷移する
		mockMvc.perform(request).andExpect(redirectedUrl("/admin/login"));
	}

	@Test // アドミン登録の際、保存の例外のテスト
	public void testAdminRegisterProcess_IOException_Fail() throws Exception {

		MockMultipartFile adminIcon = new MockMultipartFile("adminIcon", "test_icon.png", "image/png",
				"icon content".getBytes());

		RequestBuilder request = MockMvcRequestBuilders.multipart("/admin/register/process").file(adminIcon)
				.param("adminName", "Test1User").param("adminEmail", "Test1@example.com")
				.param("adminPassword", "password123").param("confirmPassword", "password123");
		// アドミン登録の際、保存の例外が生じる際、登録画面に戻し、エラーメッセージが表示する
		mockMvc.perform(request).andExpect(model().attribute("errorMessage", "アイコン保存中にエラーが発生しました。"))
				.andExpect(view().name("admin_register.html"));
	}

	@Test // アドミン登録の実行中、エラーが生じるテスト
	public void testAdminRegisterProcess_RuntimeException_Fail() throws Exception {

		MockMultipartFile adminIcon = new MockMultipartFile("adminIcon", "test_icon.png", "image/png",
				"icon content".getBytes());

		RequestBuilder request = MockMvcRequestBuilders.multipart("/admin/register/process").file(adminIcon)
				.param("adminName", "Test2User").param("adminEmail", "Test2@example.com")
				.param("adminPassword", "password123").param("confirmPassword", "password123");
		// アドミン登録の実行中、エラーが生じる場合、登録画面に戻し、エラーメッセージが表示する
		mockMvc.perform(request).andExpect(model().attribute("errorMessage", "登録エラー"))
				.andExpect(view().name("admin_register.html"));
	}
}
