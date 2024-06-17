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
  
            return "admin_register.html";
    
        
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
            adminService.createAdmin(adminName, adminEmail, adminPassword, adminIcon, confirmPassword);
            return "redirect:/admin/login";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "アアイコン保存中にエラーが発生しました。");
            return "admin_register.html";
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "admin_register.html";
        }
    }
}