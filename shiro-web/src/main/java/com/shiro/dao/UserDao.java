package com.shiro.dao;

import com.shiro.vo.User;

import java.util.List;
import java.util.Set;

public interface UserDao {


    User getPasswordByUsername(String username);

    List<String> getRolesByUsername(String username);

    List<String> getPermissionsByRolename(Set<String> roles);
}
