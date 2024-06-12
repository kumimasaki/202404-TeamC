
package rp.com.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String processRegister(
            @RequestParam String adminName,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword,
            @RequestParam("adminIcon") MultipartFile adminIcon,
            @RequestParam String confirmPassword,
            Model model) {
        
        try {
            // 创建 Admin 对象并设置属性
            Admin admin = new Admin();
            admin.setAdminName(adminName);
            admin.setAdminEmail(adminEmail);
            admin.setAdminPassword(adminPassword);
            admin.setConfirmPassword(confirmPassword);
            
            // 使用 saveAdminWithIcon 方法保存管理员信息和头像
            adminService.saveAdminWithIcon(adminName, adminEmail, adminPassword, adminIcon, confirmPassword);
            // 注册成功，重定向到登录页面
            return "redirect:/admin/login";
        } catch (IOException e) {
            e.printStackTrace();
            // 注册失败，返回注册页面并显示错误消息
            model.addAttribute("errorMessage", "アイコン保存中にエラーが発生しました。");
            return "admin_register.html";
        }
    }
}
