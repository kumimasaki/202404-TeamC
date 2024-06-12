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
		Users user = userService.getUserById(userId); // ユーザーIDに基づいてユーザー情報を取得
		if (user != null) {
			model.addAttribute("user", user); // ユーザー情報をモデルに追加
			return "user_edit"; // 情報変更画面を返す
		} else {
			model.addAttribute("error", "ユーザーが見つかりません"); // エラーメッセージをモデルに追加
			return "error"; // エラーページを返す
		}
	}

	// 情報変更処理を行うメソッド
	@PostMapping("/user/info/update")
	public String updateUserInfo(@RequestParam("userId") Long userId, // ユーザーIDをリクエストパラメータから取得
			@RequestParam("username") String userName, // ユーザー名をリクエストパラメータから取得
			@RequestParam("email") String userEmail, // ユーザーメールをリクエストパラメータから取得
			@RequestParam("password") String userPassword, // パスワードをリクエストパラメータから取得
			@RequestParam("confirmPassword") String confirmPassword, // パスワード確認をリクエストパラメータから取得
			Model model) {

		// パスワード確認
		if (!userPassword.equals(confirmPassword)) {
			model.addAttribute("error", "パスワードが一致しません"); // エラーメッセージをモデルに追加
			return "user_edit"; // 情報変更画面を再表示
		}

		Users user = userService.getUserById(userId); // ユーザーIDに基づいてユーザー情報を取得
		if (user != null) {
			user.setUserName(userName); // ユーザー名を更新
			user.setUserEmail(userEmail); // ユーザーメールを更新
			user.setUserPassword(userPassword); // パスワードを更新
			userService.updateUser(user); // ユーザー情報を更新
			return "redirect:/user/info/edit/" + userId + "?success"; // 情報変更画面にリダイレクト
		} else {
			model.addAttribute("error", "ユーザーが見つかりません"); // エラーメッセージをモデルに追加
			return "error"; // エラーページを返す
		}
	}
}