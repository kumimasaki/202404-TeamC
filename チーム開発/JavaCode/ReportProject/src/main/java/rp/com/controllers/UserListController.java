package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.UserService;
import rp.com.models.entity.Users;

import java.util.List;

@Controller
public class UserListController {

	@Autowired
	private UserService userService;

	// ユーザー一覧ページを表示するメソッド
	@GetMapping("/user/list")
	public String showUserList(Model model) {
		// すべてのユーザーを取得
		List<Users> usersList = userService.getAllUsers();
		// 取得したユーザーリストをモデルに追加
		model.addAttribute("usersList", usersList);
		// user_list.htmlテンプレートを返す
		return "user_list.html";
	}

	// ユーザー検索を処理するメソッド
	@PostMapping("/user/search")
	public String searchUsers(@RequestParam("search") String search, Model model) {
		// 名前またはメールアドレスでユーザーを検索
		List<Users> usersList = userService.searchUsersByNameOrEmail(search);
		// 検索結果をモデルに追加
		model.addAttribute("usersList", usersList);
		// user_list.htmlテンプレートを返す
		return "user_list";
	}

	// ユーザーを削除するメソッド
	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam("userId") Long userId) {
		// 指定されたIDのユーザーを削除
		userService.deleteUser(userId);
		// ユーザー一覧ページにリダイレクトする
		return "redirect:/user/list";
	}
}
