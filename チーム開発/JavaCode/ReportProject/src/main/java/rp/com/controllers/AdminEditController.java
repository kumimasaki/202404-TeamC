package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

import rp.com.models.entity.Admin;
import rp.com.services.AdminService;

// これは管理者の情報を変更するためのクラスです
@Controller
@RequestMapping("/admin")
public class AdminEditController {

	// AdminServiceを使うための準備をします
	@Autowired
	private AdminService adminService;

	// 管理者の情報変更画面を表示するためのメソッドです
	// URLは /admin/info/edit/{adminId} です
	@GetMapping("/info/edit/{adminId}")
	public String showEditForm(@PathVariable("adminId") Long adminId, Model model) {
		// 管理者IDを使って管理者の情報を探します
		Admin admin = adminService.getAdminById(adminId);
		// 管理者が見つかった場合
		if (admin != null) {
			// 管理者の情報をモデルに追加します
			model.addAttribute("admin", admin);
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
	public String updateAdminInfo(@ModelAttribute("admin") Admin admin,
			@RequestParam("adminIcon") MultipartFile adminIcon, Model model) {
		try {
// 管理者情報を更新します
			adminService.updateAdminInfoWithIcon(admin, adminIcon);

// 成功メッセージをモデルに追加します
			model.addAttribute("successMessage", "管理者情報が更新されました");
// admin_pw_changed.htmlという画面を表示します
			return "admin_pw_changed.html";
		} catch (Exception e) {
// 更新中にエラーが発生した場合
			model.addAttribute("errorMessage", "管理者情報の更新中にエラーが発生しました");
// admin_info_change.htmlという編集画面を再度表示します
			return "admin_info_change.html";
		}

	}
}
