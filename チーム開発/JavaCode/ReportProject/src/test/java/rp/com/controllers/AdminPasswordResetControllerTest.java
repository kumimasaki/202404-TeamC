package rp.com.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminPasswordResetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	private Admin admin;

	@BeforeEach
	public void setUp() {
		admin = new Admin();
		admin.setAdminEmail("admin@example.com");
		admin.setAdminPassword("old_password");
		admin.setAdminName("Admin");
	}

	// パスワードリセット画面が表示するテスト
	@Test
	public void testShowPasswordResetForm() throws Exception {
		mockMvc.perform(get("/admin/password/reset")).andExpect(status().isOk())
				.andExpect(view().name("admin_change_pw.html"));
	}

	// パスワードリセット処理が成功するテスト
	@Test
	public void testProcessPasswordReset_Success() throws Exception {
		when(adminService.findByAdminEmail(anyString())).thenReturn(admin);
		doNothing().when(adminService).updatePassword(any(Admin.class), anyString());

		mockMvc.perform(post("/admin/password/reset/process").param("adminEmail", "admin@example.com")
				.param("newPassword", "new_password")).andExpect(status().isOk())
				.andExpect(view().name("admin_pw_changed.html")).andExpect(model().attributeExists("successMessage"));
	}

	// 指定されたメールアドレスの管理者が見つからない場合のテスト
	@Test
	public void testProcessPasswordReset_AdminNotFound() throws Exception {
		when(adminService.findByAdminEmail(anyString())).thenReturn(null);

		mockMvc.perform(post("/admin/password/reset/process").param("adminEmail", "unknown@example.com")
				.param("newPassword", "new_password")).andExpect(status().isOk())
				.andExpect(view().name("admin_change_pw.html")).andExpect(model().attributeExists("errorMessage"));
	}
}
