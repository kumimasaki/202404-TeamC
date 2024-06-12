package rp.com.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

    // 保存処理(登録処理)
    // もし、findByAdminEmail == null だったら、登録処理をします
    // saveメソッドを使用して登録処理をする
    // 保存できたら true
    // そうでない場合は、保存処理失敗 false
    @Transactional
    public boolean createAdmin(String adminName, String adminEmail, String adminPassword, MultipartFile adminIcon) {
        if (adminDao.findByAdminEmail(adminEmail) == null) {
            adminDao.save(new Admin());
            return true;
        } else {
            return false;
        }
    }
    
    //ログイン処理
    //もし、findByAdminEmailAndPasswordを使用して存在しなかった場合==nullの場合、
    //その場合は、存在しないnullであることをコンストローラークラスに知らせる
    //そうでない場合は、ログインしている人の情報をコンストローラークラスに渡す
    public Admin loginCheck(String adminEmail,String password) {
    	Admin admin = adminDao.findByAdminEmailAndAdminPassword(adminEmail,password);
    	if(admin == null) {
    		return null;
    	}else {
    		return admin;
    	}
    	
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

    // iconを保存するメソッド
    public void saveAdminIcon(Admin admin, MultipartFile adminIconFile) {
        try {
            // 创建临时文件并复制头像文件
            Path tempFile = Files.createTempFile("temp", adminIconFile.getOriginalFilename());
            Files.copy(adminIconFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

//            // 设置管理员的头像
//            admin.setAdminIcon(adminIconFile);

            // 保存管理员信息
            adminDao.save(admin);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("アイコン保存が失敗しました。", e);
        }		
	}

	public boolean createAdmin(String adminName, String adminEmail, String adminPassword) {
		// TODO Auto-generated method stub
		return true;
	}
}