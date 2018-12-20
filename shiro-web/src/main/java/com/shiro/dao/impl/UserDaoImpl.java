package com.shiro.dao.impl;

import com.shiro.dao.UserDao;
import com.shiro.vo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getPasswordByUsername(String username) {

        String sql = "SELECT username,password FROM users WHERE username = ?";

        List<User> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });

        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        return list.get(0);
    }

    public List<String> getRolesByUsername(String username) {

        String sql = "SELECT role_name FROM user_roles WHERE username = ?";

        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });

    }

    public List<String> getPermissionsByRolename(Set<String> roles) {
        String sql = "SELECT permission FROM roles_permissions WHERE role_name = ?";

        List<String> list = new ArrayList<String>();

        for (String role : roles){
            list.addAll(jdbcTemplate.query(sql, new String[]{role}, new RowMapper<String>() {
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("permission");
                }
            }));
        }

        return list;
    }
}
