package com.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 只要包含其中一种角色，就返回
 */
public class RolesOrFilter extends AuthorizationFilter {
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object) throws Exception {

        Subject subject = getSubject(request,response);

        String[] roles = (String[]) object;
        if (roles == null || roles.length == 0){
            return  true;
        }

        for (String role : roles){
            if (subject.hasRole(role)){
                return  true;
            }
        }

        return false;
    }
}
