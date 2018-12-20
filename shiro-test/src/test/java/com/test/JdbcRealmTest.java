package com.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm(){

        JdbcRealm jdbcRealm = new JdbcRealm();

        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "SELECT password FROM test_user WHERE username = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "SELECT role_name FROM test_user_role WHERE user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String permissonSql = "SELECT permission FROM test_roles_permissions WHERE role_name = ?";
        jdbcRealm.setPermissionsQuery(permissonSql);

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming","123456");

        subject.login(token);

        System.out.println("isAuthenticated："+subject.isAuthenticated());

        subject.checkRoles("admin","user");

        subject.checkPermission("user:select");
    }
}
