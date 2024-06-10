package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.UserService;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Users;

@Controller
public class UserCreateController {

	// UserServiceのインスタンスを自動的に注入します
	@Autowired
	private UserService userService;

	// ユーザー登録画面を表示するメソッド
	@GetMapping("/admin/user/create")
	public String showCreateUserForm(Model model) {
		// ユーザーオブジェクトをモデルに追加してフォームにバインドします
		model.addAttribute("user", new Users());
		return "user_create";
	}

	// ユーザー登録処理を行うメソッド
	@PostMapping("/admin/user/create/process")
	public String createUser(
			@RequestParam("username") String userName, 
			@RequestParam("email") String userEmail,
			@RequestParam("password") String userPassword, 
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("adminId") Long adminId, Model model) {

		// パスワード確認
		if (!userPassword.equals(confirmPassword)) {
			model.addAttribute("error", "パスワードが一致しません");
			return "user_create";
		}

		// 管理者情報の取得（ここでは簡単にAdminのインスタンスを生成していますが、実際にはデータベースから取得する必要があります）
		Admin admin = new Admin(); // 実際のアプリケーションでは、adminIdを使用してデータベースから管理者を取得します
		admin.setAdminId(adminId);

		// 新しいユーザーの作成
		Users newUser = new Users(userEmail, userName, userPassword, admin);

		// ユーザーの登録
		if (userService.createUser(newUser)) {
			return "redirect:/user_list";
		} else {
			model.addAttribute("error", "ユーザーの登録に失敗しました。メールアドレスが既に存在しています。");
			return "user_create";
		}
	}
}