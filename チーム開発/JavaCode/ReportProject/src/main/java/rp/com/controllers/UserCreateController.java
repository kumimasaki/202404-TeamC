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

import jakarta.servlet.http.HttpSession;
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

	@Autowired
	private HttpSession session;

	// ユーザー登録画面を表示するメソッド
	@GetMapping("/admin/user/create")
	public String showCreateUserForm(Model model) {
	    Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	    if (admin != null) {
	        model.addAttribute("adminId", admin.getAdminId());
	        return "user_create.html";
	    } else {
	        return "redirect:/admin/login";
	    }
	}


	// ユーザー登録処理を行うメソッド
	@PostMapping("/admin/user/create/process")
	public String createUser(
	    @RequestParam String userName,
	    @RequestParam String userEmail,
	    @RequestParam String userPassword, 
	    @RequestParam("userIcon") MultipartFile userIcon,
	    @RequestParam("adminId") Long adminId,
	    Model model) {
	    
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        if (admin != null) {
            try {
            	
                userService.saveUserWithIcon(userName, userEmail, userPassword, userIcon, admin.getAdminId());
                return "redirect:/user/list";
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        } else {
            // 处理 admin 对象未初始化的情况
            return "redirect:/admin/login";
	    }
	}

	
	
	} 
