
package dao;

// register 

import java.util.List;
import model.User;

public interface UserDAO  {
    boolean insertUser(String firstName,String lastName,String email,String password, String phone);
    User findByEmail(String email);
    List<User> getAllUsers();
    void updateUserInfoByEmail(String email, String firstName, String lastName, String phoneNumber);
    void updateUserPass(String email, String password);
    void updateUserRole(String email);
    void deleteUserByEmail(String email);
}
