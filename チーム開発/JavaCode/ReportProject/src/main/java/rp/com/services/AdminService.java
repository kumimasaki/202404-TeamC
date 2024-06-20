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

        if (adminIcon != null && !adminIcon.isEmpty()) {
            saveAdminIcon(admin, adminIcon);
        }

        adminDao.save(admin);
    }

    public Admin loginAdmin(String adminEmail, String adminPassword) {
        return adminDao.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
    }

    public boolean emailExists(String adminEmail) {
        return adminDao.findByAdminEmail(adminEmail) != null;
    }

    public Admin getAdminById(Long adminId) {
        return adminDao.findById(adminId).orElse(null);
    }

    public Admin saveAdmin(Admin admin) {
        return adminDao.save(admin);
    }

    public List<String> getAllAdminNames() {
        List<Admin> admins = adminDao.findAll();
        List<String> adminNames = new ArrayList<>();
        for (Admin admin : admins) {
            adminNames.add(admin.getAdminName());
        }
        return adminNames;
    }

    public Admin getAdminByName(String adminName) {
        return adminDao.findByAdminName(adminName);
    }

    @Transactional
    public void updatePassword(Admin admin, String newPassword) {
        admin.setAdminPassword(newPassword);
        adminDao.save(admin);
    }

    public Admin findByAdminEmail(String adminEmail) {
        return adminDao.findByAdminEmail(adminEmail);
    }

    @Transactional
    public void updateAdminInfoWithIcon(Admin admin, MultipartFile adminIcon) throws IOException {
        Admin existingAdmin = adminDao.findById(admin.getAdminId()).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setAdminName(admin.getAdminName());
            existingAdmin.setAdminEmail(admin.getAdminEmail());
            existingAdmin.setAdminPassword(admin.getAdminPassword());

            if (adminIcon != null && !adminIcon.isEmpty()) {
                saveAdminIcon(existingAdmin, adminIcon);
            }

            adminDao.save(existingAdmin);
        } else {
            throw new RuntimeException("指定の管理者は存在しません");
        }
    }

    private void saveAdminIcon(Admin admin, MultipartFile adminIcon) throws IOException {
        if (adminIcon != null && !adminIcon.isEmpty()) {
            String fileName = null;
            try {
                String originalFilename = adminIcon.getOriginalFilename();
                fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + originalFilename;

                Path uploadPath = Paths.get("src/main/resources/static/uploads/" + fileName);
                Files.createDirectories(uploadPath.getParent());
                Files.copy(adminIcon.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                admin.setAdminIcon(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("アイコン保存中にエラーが発生しました。");
            }
        }
    }
}
