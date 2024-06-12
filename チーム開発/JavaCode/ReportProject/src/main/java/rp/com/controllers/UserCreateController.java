package rp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import rp.com.services.AdminService;
import rp.com.services.UserService;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Users;

@Controller
public class UserCreateController {

	@Autowired
	private AdminService adminService;

	// UserServiceのインスタンスを自動的に注入します
	@Autowired
	private UserService userService;

	// ユーザー登録画面を表示するメソッド
	@GetMapping("/admin/user/create")
	public String showCreateUserForm(Model model) {
		// ユーザーオブジェクトをモデルに追加してフォームにバインドします
		model.addAttribute("user", new Users());
		return "user_create.html";
	}

	// ユーザー登録処理を行うメソッド
	@PostMapping("/admin/user/create/process")
	public String createUser(@RequestParam("username") String userName, @RequestParam("email") String userEmail,
			@RequestParam("password") String userPassword, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("userIcon") MultipartFile userIcon, @RequestParam("adminId") Long adminId, Model model) {
		// アイコンを保存する
		String fileName = null;
		if (!userIcon.isEmpty()) {
			try {
				String originalFilename = userIcon.getOriginalFilename();
				fileName = new SimpleDateFormat("yyyy-MM-dd-HH-").format(new Date()) + originalFilename;
				Files.copy(userIcon.getInputStream(), Path.of("src/main/resources/static/uploads/" + fileName));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// admin情報を取得する
		Admin admin = adminService.getAdminById(adminId);
		// adminが存在しない場合
		if (admin == null) {
			model.addAttribute("error", "管理者が存在しません");
			return "user_create.html";
		}
		// パスワード確認
		if (!userPassword.equals(confirmPassword)) {
			model.addAttribute("error", "パスワードが一致しません");
			return "user_create.html";
		}

		// 新しいユーザー作成
		Users newUser = new Users(userEmail, userName, userPassword, admin);
		newUser.setUserIcon(fileName);

		// 新しいユーザー保存
		if (userService.createUser(newUser)) {
			return "redirect:/user_list";
		} else {
			model.addAttribute("error", "ユーザー追加が失敗しました。");
			return "user_create.html";
		}
	}
}