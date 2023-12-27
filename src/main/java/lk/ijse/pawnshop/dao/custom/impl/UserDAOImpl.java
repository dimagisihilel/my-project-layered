package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.UserDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean addUser(UserDto user) throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (u_id, username, password) VALUES (?, ?, ?)");

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("INSERT INTO user (u_id, username, password) VALUES (?,?,?)",user.getId(),user.getUsername(),user.getPassword());
    }
    @Override
    public List<UserDto> getAllUsers() throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        List<UserDto> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserDto user = new UserDto(
                        resultSet.getString("u_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
        }

        return users;*/
        ResultSet rst = SQLUtil.execute("SELECT * FROM user");
        ArrayList<UserDto> getAllUsers = new ArrayList<>();
        while (rst.next()){
            UserDto user = new UserDto(
                rst.getString("u_id"),rst.getString("username"),rst.getString("password"));
            getAllUsers.add(user);
        }
        return getAllUsers;
    }
    @Override
    public boolean updateUser(UserDto user) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE user SET username=?, password=? WHERE u_id=?");

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("UPDATE user SET username=?, password=? WHERE u_id=?",user.getUsername(),user.getPassword(),user.getId());
    }
    @Override
    public boolean deleteUser(String id) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM user WHERE u_id=?");

            preparedStatement.setString(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("DELETE FROM user WHERE u_id=?",id);
    }

   /* private static void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
