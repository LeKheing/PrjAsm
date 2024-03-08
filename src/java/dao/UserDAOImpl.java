package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

// DAO : data access object : doi tuong de truy cap du lieu 
public class UserDAOImpl extends DBContext implements UserDAO {

    @Override
    public boolean insertUser(String firstName, String lastName, String email, String password, String phone) {
        try {
            Connection conn = getConnection();
            // Hash the password using SHA-256
            String hashedPassword = hashPassword(password);
            String sql = "INSERT INTO users (first_name, last_name, email, password_hash, phone_number) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, hashedPassword);
                pstmt.setString(5, phone);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the insertion was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log or throw it
        }

        return false;
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the email parameter
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id"); // Assuming 'id' is the column name for the user ID
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String phoneNumber = rs.getString("phone_number");
                    String password = rs.getString("password_hash");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    // Create and return the User object
                    User user = new User();
                    user.setId(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phoneNumber);
                    user.setEmail(email);
                    user.setPasswordHash(password);
                    user.setIsAdmin(isAdmin);

                    // Set other user properties
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
        return null;
    }

    @Override
    public void updateUserInfoByEmail(String email, String firstName, String lastName, String phoneNumber) {
        String query = "UPDATE users SET first_name=?, last_name=?, phone_number=? WHERE email=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the parameters
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setString(4, email);

            // Execute the update query
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    @Override
    public void updateUserPass(String email, String password) {
        String query = "UPDATE users SET password_hash=? WHERE email=?";
        String hashedPassword = null;
        try {
            hashedPassword = hashPassword(password);
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            // Set the new password and email parameters
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            System.out.println(hashedPassword);
            // Execute the update query
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id"); // Assuming 'id' is the column name for the user ID
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("password_hash");
                boolean isAdmin = rs.getBoolean("is_admin");

                // Create and add the User object to the list
                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setPasswordHash(password);
                user.setIsAdmin(isAdmin);
                userList.add(user);

            }
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
        return userList;
    }

    @Override
    public void updateUserRole(String email) {
        String query = "UPDATE users SET is_admin = 1 WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            // Set the email parameter
            ps.setString(1, email);
            // Execute the update statement
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        String query = "DELETE FROM users WHERE email=? AND is_admin <> 1";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            // Set the parameter
            ps.setString(1, email);

            // Execute the delete query
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with email " + email + " has been deleted.");
            } else {
                System.out.println("User with email " + email + " not found or is an admin.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }
}
