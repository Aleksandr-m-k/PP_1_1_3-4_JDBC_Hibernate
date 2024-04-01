package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    // создание таблицы
    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE `users` ( id int NOT NULL AUTO_INCREMENT, name varchar(45) NOT NULL," +
                " lastName varchar(45) DEFAULT NULL, age int NOT NULL, PRIMARY KEY (id))";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица уже была создана");
        }
    }

    // удаление таблицы
    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table Users");
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name, lastName, age) values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("введено неверное значение");
        }
    }

    // удаление User из таблицы по Id
    public void removeUserById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Users where ID=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // получение всех User из таблицы
    public List<User> getAllUsers() {
        List<User> userArrayList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User users = new User();
                users.setId(resultSet.getLong("id"));
                users.setName(resultSet.getString("name"));
                users.setLastName(resultSet.getString("lastName"));
                users.setAge(resultSet.getByte("age"));
                userArrayList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    // очистка содержания таблицы
    public void cleanUsersTable() {
        PreparedStatement preparedStatement = null;
        try (Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("DELETE from Users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }
}
