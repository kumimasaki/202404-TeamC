package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

// これは管理者の登録に関するコントローラクラスです
@Controller
@RequestMapping("/admin")
public class AdminRegisterController {

	// AdminServiceを使うための設定です
	@Autowired
	private AdminService adminService;

	// 管理者登録画面を表示するメソッドです
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		// 新しい管理者オブジェクトを作ってモデルに追加します
		model.addAttribute("admin", new Admin());
		// admin_register.htmlという画面を表示します
		return "admin_register.html";
	}

	// 管理者登録処理を行うメソッドです
	@PostMapping("/register/process")
	public String processRegister(@ModelAttribute("admin") Admin admin, Model model) {
		try {
			// メールアドレスが既に存在するかチェックします
			if (adminService.emailExists(admin.getAdminEmail())) {
				throw new IllegalArgumentException("メールアドレスは既に使用されています");
			}
			// 管理者を登録します
			adminService.registerAdmin(admin);
			// 登録が成功したら、ログイン画面を表示します
			model.addAttribute("successMessage", "登録が成功しました。ログインしてください。");
			model.addAttribute("admin", new Admin());
			return "admin_login.html";
		} catch (IllegalArgumentException e) {
			// メールアドレス重複によるエラーの場合、エラーメッセージをモデルに追加して登録画面を再表示します
			model.addAttribute("errorMessage", e.getMessage());
			return "admin_register.html";
		} catch (Exception e) {
			// その他のエラーが発生した場合、エラーメッセージをモデルに追加して登録画面を再表示します
			model.addAttribute("errorMessage", "登録中にエラーが発生しました");
			return "admin_register.html";
		}
	}
}
