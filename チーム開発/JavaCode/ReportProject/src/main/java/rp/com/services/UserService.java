package rp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rp.com.models.dao.UsersDao;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Users;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UsersDao usersDao;
	
	public List<Users> getAllUsers() {
        return usersDao.findAll();
    }

    public List<Users> searchUsersByNameOrEmail(String search) {
        return usersDao.findByUserNameContainingOrUserEmailContaining(search, search);
    }

    public void deleteUser(Long userId) {
        usersDao.deleteById(userId);
    }

	// 保存処理（登録処理）
	// もし、findByUserEmail == nullだったら登録処理をします
	// 保存ができたらtrue
	// そうでない場合、保存処理失敗false
	public boolean createUser(String userEmail, String userName, String userPassword) {
		if (usersDao.findByUserEmail(userEmail) == null) {
			Admin admin = new Admin();
			usersDao.save(new Users(userEmail, userName, userPassword,admin));
			return true;

		} else {
			return false;
		}
	}

	// ログイン処理
	// もし、emailとpasswordがfindByUserAndPasswordを使用して存在しなかった場合==nullの場合、
	// 存在しないnullであることをコントローラークラスに知らせる
	// そうでない場合、ログインしている人の情報をコントローラークラスに渡す
	public Users loginCheck(String userEmail, String userPassword) {
		Users users = usersDao.findByUserEmailAndUserPassword(userEmail, userPassword);
		if (users == null) {
			return null;
	
		} else {
			return users;
		}
	}

}
