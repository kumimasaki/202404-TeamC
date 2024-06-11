package rp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rp.com.models.dao.UsersDao;
import rp.com.models.entity.Users;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersDao usersDao;
    
    // すべてのユーザーを取得するメソッド
    public List<Users> getAllUserList() {
		if(usersDao == null) {
    		return null;
    	}else {
        return usersDao.findAll();
        }
    }

    // ユーザー名またはメールアドレスでユーザーを検索するメソッド
    public List<Users> searchUsersByNameOrEmail(String search) {
        return usersDao.findByUserNameContainingOrUserEmailContaining(search, search);
    }

    // ユーザーを削除するメソッド
    public void deleteUser(Long userId) {
        usersDao.deleteById(userId);
    }

    // ユーザーの作成処理
    // もし、findByUserEmail == nullだったら登録処理を行う
    // 保存ができたらtrue、そうでない場合、保存処理失敗falseを返す
    public boolean createUser(String userName,String userEmail,String userPassword) {
        if (usersDao.findByUserEmail(userEmail) == null) {
            usersDao.save(new Users(userName,userEmail,userPassword));
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
       if(users==null) {
    	   return null;
       }else {
        return users;
    }
    }
}

//	public List<Users> searchByUserName(String userName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<Users> searchByUserEmail(String userEmail) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Users getUserById(Long userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}