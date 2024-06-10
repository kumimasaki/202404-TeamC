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
        return "admin_info_change.html";
    }
	

    // パスワード変更処理
    @PostMapping("/admin/password/reset/process")
    public String processPasswordReset(@RequestParam("adminId") Long adminId,
                                       @RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null || !admin.getAdminPassword().equals(oldPassword)) {
            model.addAttribute("errorMessage", "現在のパスワードが正しくありません。");
            return "admin_info_change.html";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "新しいパスワードと確認パスワードが一致しません。");
            return "admin_info_change.html";
        }

        // パスワード変更処理
        admin.setAdminPassword(newPassword);
        adminService.saveAdmin(admin);

        model.addAttribute("successMessage", "パスワードが正常に変更されました。");
        return "admin_pw_changed.html"; 
    }
    
}
