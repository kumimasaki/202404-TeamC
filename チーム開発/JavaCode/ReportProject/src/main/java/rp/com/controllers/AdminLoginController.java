
package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

// これは管理者のログインに関するコントローラクラスです
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    // AdminServiceを使うための設定です
    @Autowired
    private AdminService adminService;
	// Sessionが使えるように宣言
	@Autowired
	private HttpSession session;
	
    // 管理者ログイン画面を表示するメソッドです
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // 空のAdminオブジェクトを作ってモデルに追加します
        model.addAttribute("admin", new Admin());
        // admin_login.htmlという画面を表示します
        return "admin_login.html";
    }

    // 管理者ログイン処理を行うメソッドです
    @PostMapping("/login/process")
    public String processLogin(@RequestParam String adminEmail, @RequestParam String adminPassword, Model model) {
        Admin admin = adminService.loginAdmin(adminEmail, adminPassword);
        if (admin != null) {
        	session.setAttribute("loginAdminInfo", admin);
            // ログインが成功した場合、レポート画面を表示します
            return "redirect:/admin/report/list";
        } else {
            // ログインに失敗した場合、エラーメッセージを追加してログイン画面を再表示
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが間違っています");
            return "admin_login.html";
        }
    }
}
