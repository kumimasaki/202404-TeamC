package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

@Controller
public class AdminPasswordResetController {

	@Autowired
	private AdminService adminService;

	// パスワード変更画面表示
	@GetMapping("/admin/password/reset")
	public String showPasswordResetForm(Model model) {
		return "admin_change_pw.html";
	}

	// パスワード変更処理
	 @PostMapping("/admin/password/reset/process")
	    public String processPasswordReset(@RequestParam String adminEmail,
	                                       @RequestParam String newPassword,
	                                       Model model) {
	        Admin admin = adminService.findByAdminEmail(adminEmail);
	        
	        //もし、adminが存在しない場合、エラーメッセージを出します
	        if (admin == null) {
	            model.addAttribute("errorMessage", "指定されたメールアドレスには存在しません。");
	            return "admin_change_pw.html";
	        }

	        // パスワード変更処理
	        adminService.updatePassword(admin, newPassword);

	        model.addAttribute("successMessage", "パスワードが変更されました。");
	        return "admin_pw_changed.html"; 
	    }
	 
	 

}
