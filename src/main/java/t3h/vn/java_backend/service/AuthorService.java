package t3h.vn.java_backend.service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import t3h.vn.java_backend.Model.author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    private Connection connection;
    String url = "jdbc:mysql://localhost:3306/2211";
    String username = "root";
    String password = "tuananhvu211";
    private void connectDB() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public List<author> getAuthorList() {
        List<author> authors = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (connection == null || connection.isClosed()) {
                connectDB();
            }
            String query = "SELECT * FROM author";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Integer gender = resultSet.getInt("gender");
                author author = new author(id, name, address, gender);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return authors;
    }

    public author getAuthorById(@PathVariable Long id) {
        List<author> authors = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Kiểm tra kết nối đã được thiết lập hay chưa
            if (connection == null || connection.isClosed()) {
                connectDB();
            }
            // Chuẩn bị câu truy vấn SQL với tham số id
            String query = "SELECT * FROM author WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            // Thực thi truy vấn SQL
            resultSet = preparedStatement.executeQuery();
            // Xử lý kết quả truy vấn
            while (resultSet.next()) {
                Long authorId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Integer gender = resultSet.getInt("gender");
                author author = new author(id, name, address, gender);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả các tài nguyên (resultSet, preparedStatement)
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return authors.get(0);
    }

    public author createAuthor(@RequestBody author author) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (connection == null || connection.isClosed()) {
                connectDB();
            }
            String query = "INSERT INTO author (name, address, gender) VALUES (?, ? ,?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getAddress());
            preparedStatement.setInt(3, (author.getGender() == null
                    || author.getGender() < 0
                    || author.getGender() > 1) ? 0 : author.getGender());

            // Thực thi truy vấn SQL để thêm tác giả mới
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Lấy ID được tự động tạo cho tác giả mới
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long generatedId = resultSet.getLong(1);
                    author.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả các tài nguyên (resultSet, preparedStatement)
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return author;
    }

    public author updateAuthorById(@PathVariable Long id, @RequestBody author author) {
        PreparedStatement preparedStatement = null;

        try {
            if (connection == null || connection.isClosed()) {
                connectDB();
            }
            String query = "UPDATE author SET name = ?, address = ? WHERE id =  ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getAddress());
            preparedStatement.setLong(3, id);
            int rowsAffected = preparedStatement.executeUpdate();

            while (rowsAffected > 0) {
                author.setId(id);
                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả các tài nguyên (preparedStatement)
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return author;
    }

    public Boolean deleteAuthorById(@PathVariable Long id) {
        PreparedStatement preparedStatement = null;

        try {
            // Kiểm tra kết nối đã được thiết lập hay chưa
            if (connection == null || connection.isClosed()) {
                connectDB();
            }

            String query = "DELETE FROM author WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả các tài nguyên (preparedStatement)
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
