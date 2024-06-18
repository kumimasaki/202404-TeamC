package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.UserService;
import rp.com.models.entity.Users;

@Controller
public class UserInfoController {

	@Autowired
	private UserService userService;

	// 情報変更画面を表示するメソッド
	@GetMapping("/user/info/edit/{userId}")
	public String showEditUserInfoForm(@PathVariable Long userId, Model model) {
		// ユーザーIDに基づいてユーザー情報を取得
		Users user = userService.getUserById(userId); 
		if (user != null) {
			// ユーザー情報をモデルに追加
			model.addAttribute("user", user); 
			return "user_edit"; 
		} else {
			// エラーメッセージをモデルに追加
			model.addAttribute("error", "ユーザーが見つかりません"); 
			return "error"; 
		}
	}

	// 情報変更処理を行うメソッド
	@PostMapping("/user/info/update")
	public String updateUserInfo(@RequestParam("userId") Long userId, 
			@RequestParam("username") String userName, 
			@RequestParam("email") String userEmail, 
			@RequestParam("password") String userPassword, 
			@RequestParam("confirmPassword") String confirmPassword, 
			Model model) {

		// パスワード確認
		if (!userPassword.equals(confirmPassword)) {
			model.addAttribute("error", "パスワードが一致しません"); 
			return "user_edit"; 
		}
		// ユーザーIDに基づいてユーザー情報を取得
		Users user = userService.getUserById(userId); 
		if (user != null) {
			user.setUserName(userName); 
			user.setUserEmail(userEmail); 
			user.setUserPassword(userPassword); 
			userService.updateUser(user); 
			// 情報変更画面にリダイレクト
			return "redirect:/user/info/edit/" + userId + "?success"; 
		} else {
			model.addAttribute("error", "ユーザーが見つかりません");
			return "error"; 
		}
	}
}