package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class adminLogoutController {

    @Autowired
    private HttpSession session;
    
    //logout処理
    @GetMapping("/admin/logout")
    public String adminLogout() {
        //sessionの無効化
        session.invalidate();
        return "redirect:/admin/login";
    }
}