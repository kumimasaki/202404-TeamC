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
import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminRegisterController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpSession session;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        if (admin == null) {
            model.addAttribute("admin", new Admin());
            return "admin_register.html";
        } else {
            return "redirect:/admin/login";
        }
    }

    @PostMapping("/register/process")
    public String processRegister(
            @RequestParam String adminName,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword,
            @RequestParam("adminIcon") MultipartFile adminIcon,
            @RequestParam String confirmPassword,
            Model model) {
        
        try {
            adminService.saveAdminWithIcon(adminName, adminEmail, adminPassword, adminIcon, confirmPassword);
            return "redirect:/admin/login";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "アイコン保存中にエラーが発生しました。");
            return "admin_register.html";
        }
    }
}