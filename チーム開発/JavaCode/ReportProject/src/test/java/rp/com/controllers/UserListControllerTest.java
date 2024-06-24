package rp.com.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import rp.com.models.entity.Users;
import rp.com.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserListControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserListController userListController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userListController).build();
	}

	// ユーザーリストの表示テスト
	@Test
	public void testShowUserList() throws Exception {
		when(userService.getAllUserList()).thenReturn(Collections.singletonList(new Users()));

		mockMvc.perform(get("/user/list")).andExpect(status().isOk()).andExpect(view().name("user_list"))
				.andExpect(model().attributeExists("usersList")).andExpect(model().attributeExists("users"));
	}

	// ユーザー検索のテスト
	@Test
	public void testSearchUsers() throws Exception {
		when(userService.searchUsersByNameOrEmail(anyString())).thenReturn(Collections.singletonList(new Users()));

		mockMvc.perform(post("/user/search").param("search", "test")).andExpect(status().isOk())
				.andExpect(view().name("user_list")).andExpect(model().attributeExists("usersList"));
	}

	// ユーザー削除のテスト
	@Test
	public void testDeleteUser() throws Exception {
		doNothing().when(userService).deleteUser(anyLong());

		mockMvc.perform(post("/user/delete").param("userId", "1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));
	}
}
