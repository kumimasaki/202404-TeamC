package rp.com.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import rp.com.models.dao.AdminDao;
import rp.com.models.entity.Admin;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

    @Transactional
    public void createAdmin(String adminName, String adminEmail, String adminPassword, MultipartFile adminIcon, String confirmPassword) throws IOException {
        if (!adminPassword.equals(confirmPassword)) {
            throw new RuntimeException("パスワードが一致しません。");
        }

		if (adminDao.findByAdminEmail(adminEmail) != null) {
			throw new RuntimeException("このメールアドレスは既に登録されています。");
		}

		Admin admin = new Admin();
		admin.setAdminName(adminName);
		admin.setAdminEmail(adminEmail);
		admin.setAdminPassword(adminPassword);

		// 保存管理员头像
		if (adminIcon != null && !adminIcon.isEmpty()) {
			saveAdminIcon(admin, adminIcon);
		}

		adminDao.save(admin);
	}

	// ログイン処理
	// 如果adminEmail和adminPassword存在，返回Admin对象；否则返回null
	public Admin loginAdmin(String adminEmail, String adminPassword) {
		return adminDao.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
	}

	// メールアドレスの存在確認
	public boolean emailExists(String adminEmail) {
		// 指定されたメールアドレスの管理者が存在するかどうかを返します
		return adminDao.findByAdminEmail(adminEmail) != null;
	}

	// 管理者をIDで取得するメソッド
	public Admin getAdminById(Long adminId) {
		// 指定されたIDの管理者を取得し、存在しない場合は null を返します
		return adminDao.findById(adminId).orElse(null);
	}
	

	// 管理者情報を保存するメソッド
	public Admin saveAdmin(Admin admin) {
		return adminDao.save(admin);
	}

	// 管理者の名前リストを取得するメソッド
	public List<String> getAllAdminNames() {
		List<Admin> admins = adminDao.findAll();
		List<String> adminNames = new ArrayList<>();
		for (Admin admin : admins) {
			adminNames.add(admin.getAdminName());
		}
		return adminNames;
	}

	public Admin getAdminName(String adminName) {
		// 根据管理员名字查询管理员对象
		return adminDao.findByAdminName(adminName);
	}

	// パスワード更新メソッド
	@Transactional
	public void updatePassword(Admin admin, String newPassword) {
		admin.setAdminPassword(newPassword);
		adminDao.save(admin);
	}

	// 管理者をメールで取得するメソッド
	public Admin findByAdminEmail(String adminEmail) {
		return adminDao.findByAdminEmail(adminEmail);
	}

	// 管理者情報更新
	@Transactional
	public void updateAdminInfoWithIcon(Admin admin, MultipartFile adminIcon) throws IOException {
		// 判断管理员是否存在
		Admin existingAdmin = adminDao.findById(admin.getAdminId()).orElse(null);
		if (existingAdmin != null) {
			// 更新管理员信息
			existingAdmin.setAdminName(admin.getAdminName());
			existingAdmin.setAdminEmail(admin.getAdminEmail());
			existingAdmin.setAdminPassword(admin.getAdminPassword());

			// 更新管理员头像
			saveAdminIcon(existingAdmin, adminIcon);

			// 保存更新后的管理员信息
			adminDao.save(existingAdmin);
		} else {
			throw new RuntimeException("指定の管理者は存在しません");
		}
	}

	// アイコンを保存する
	private void saveAdminIcon(Admin admin, MultipartFile adminIcon) {
		if (adminIcon != null && !adminIcon.isEmpty()) {
			String fileName = null;
			try {

				String originalFilename = adminIcon.getOriginalFilename();
				fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + originalFilename;

				Path uploadPath = Paths.get("src/main/resources/static/uploads/" + fileName);
				Files.copy(adminIcon.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

				admin.setAdminIcon(fileName);

			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

	}
}
