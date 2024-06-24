package rp.com.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

// これは管理者の情報を変更するためのクラスです
@Controller
@RequestMapping("/admin")
public class AdminEditController {

    // AdminServiceを使うための準備をします
    @Autowired
    private AdminService adminService;

	@Autowired
	private HttpSession session;

	// 管理者の情報変更画面を表示するためのメソッドです
	// URLは /admin/info/edit/{adminId} です
	@GetMapping("/info/edit/{adminId}")
	public String showEditForm(@PathVariable("adminId") Long adminId, Model model) {

		Admin admin = (Admin) session.getAttribute("loginAdminInfo");

		// 管理者が見つかった場合
		if (admin != null) {
			// 管理者の情報をモデルに追加します
			model.addAttribute("admin", admin);
			model.addAttribute("adminId", adminId);
			String adminIconPath = "/uploads/" + admin.getAdminIcon();
			model.addAttribute("adminIconPath", adminIconPath);
			// admin_info_change.htmlという画面を見せます
			return "admin_info_change.html";
		} else {
			// 管理者が見つからなかった場合
			model.addAttribute("errorMessage", "管理者が見つかりません");
			// admin_register.htmlという登録画面を見せます
			return "admin_register.html";
		}
	}

	// 管理者の情報を更新するためのメソッドです
	// URLは /admin/info/update です
	@PostMapping("/info/update")
	public String updateAdminInfo(@RequestParam Long adminId, @RequestParam String adminName,
			@RequestParam String adminEmail, @RequestParam String adminPassword,
			@RequestParam("adminIcon") MultipartFile adminIcon, Model model) {

		try {
			Admin admin = adminService.getAdminById(adminId);
			if (admin != null) {
				admin.setAdminName(adminName);
				admin.setAdminEmail(adminEmail);
				admin.setAdminPassword(adminPassword);

				adminService.updateAdminInfoWithIcon(admin, adminIcon); // 更新管理员信息和头像

				model.addAttribute("successMessage", "管理者情報が更新されました");
				return "admin_info_changed.html"; // 更新成功后跳转到登录页面
			} else {
				throw new RuntimeException("指定の管理者は存在しません");
			}
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "管理者情報の更新中にエラーが発生しました");
			return "admin_info_change.html";
		}

	}
}
