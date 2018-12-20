package com.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PermOrFilter extends AuthorizationFilter {
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request,response);

        String[] perms = (String[]) mappedValue;

        if (perms.length == 0 || perms == null){
            return  true;
        }

        for (String perm : perms){
            if (subject.isPermitted(perm)){
                return  true;
            }
        }

        return false;
    }
}
