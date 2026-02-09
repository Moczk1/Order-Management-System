package com.Moczk1.filter;

import com.Moczk1.common.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter  implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        log.info("本次请求的是：{}", requestURI);
        // 不拦截的所有uri
        String[] uris = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        // 使用 AntPathMatch 进行检查是否是白名单的 uri
        boolean check = check(uris, requestURI);

        if (check) {
            log.info("本次请求不需要处理");
            filterChain.doFilter(request, response);
            return;
        }

        // 不是访问白名单uri， 检查是否登录成功；成功，则放行。
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登陆，userid为：{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

//        log.info("拦截到请求：{}", request.getRequestURI());
//        filterChain.doFilter(request, response);
    }

    public boolean check(String[] uris, String requestURI) {
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
