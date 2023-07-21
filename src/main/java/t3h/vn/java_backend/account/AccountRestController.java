package t3h.vn.java_backend.account;

import org.springframework.web.bind.annotation.*;
import t3h.vn.java_backend.Model.Account;
import t3h.vn.java_backend.Model.author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RestController
public class AccountRestController {
    static String DB_INFOR = "jdbc:mysql://localhost:3306/test";
    static String USER_NAME = "root";
    static String PASS = "tuananhvu211";
    static Connection conn;

    private static void getConnection() throws SQLException {
        conn = DriverManager.getConnection(DB_INFOR, USER_NAME, PASS);
    }
@GetMapping("getaccount")
    public Account getDetialAccount(@PathVariable int id){
        Account account = null;
        try {
            if(conn == null || conn.isClosed()){
                getConnection();
            }
            Statement sqlFile = conn.createStatement();
            String query = "select * from account where  ID=" + id;
            ResultSet resultSet = sqlFile.executeQuery(query);
            while (resultSet.next()) {
                String email = resultSet.getString(2);
                String avatar= resultSet.getString(3);
                String password = resultSet.getString(4);
                String address = resultSet.getString(5);
                int status  = resultSet.getInt(6);
                account = new Account(id,email,avatar,password,address,status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account;
    }
@PostMapping("postaccount")
    public String postAccount(@RequestBody Account o) { //Che giấu thông tin
        try {
            if(conn == null || conn.isClosed()){
                getConnection();
            }
            String sql = " insert into account (EMAIL,AVARTAR,PASS,ADDRESS) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, o.getEmail());
            preparedStatement.setString(2, o.getAvatar());
            preparedStatement.setString(3, o.getPass());
            preparedStatement.setString(4, o.getAddress());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Thêm thành công";
    }
@PatchMapping("patchaccount/{id}")
    public String putAccount(@PathVariable int id, @RequestBody Account a) {// path variable xuất hiện sau dấu /
        PreparedStatement preparedStatement = null;
        try {
            if(conn == null || conn.isClosed()){
                getConnection();
            }
            String sql = " update account set EMAIL=? ,AVARTAR=?,PASS=?,ADDRESS=?,STATUS=? where ID = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, a.getEmail());
            preparedStatement.setString(2, a.getAvatar());
            preparedStatement.setString(3, a.getPass());
            preparedStatement.setString(4, a.getAddress());
            preparedStatement.setInt(5, a.getStatus());
            preparedStatement.setInt(6, id);
            int rowsAffected = preparedStatement.executeUpdate();

            while (rowsAffected > 0) {
                a.setID(id);
                return "Sửa thành công";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Sủa thành công";
    }

    public String deleteAccount(@PathVariable int id) {
        try {
            if(conn == null || conn.isClosed()){
                getConnection();
            }
            String sql = " delete from account where ID=" + id;
            Statement statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Xóa thành công";
    }
@GetMapping("listaccount")
    public List<Account> listAccount() {
        List<Account> listaccount = new ArrayList<>();
        try {
            if(conn == null || conn.isClosed()){
                getConnection();
            }
            Statement sqlFile = conn.createStatement();
            String query = "select * from account";
            ResultSet resultSet = sqlFile.executeQuery(query);
            while (resultSet.next()) {
                int id=resultSet.getInt(1);
                String email = resultSet.getString(2);
                String avatar = resultSet.getString(3);
                String pass = resultSet.getString(4);
                String address = resultSet.getString(5);
                int status = resultSet.getInt(6);
              Account account = new Account(id,email,avatar,pass,address,status);
                listaccount.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  listaccount;
    }
}
