package com.elintegro.dataframe

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class OneTimePasswordFilter implements Filter {
    @Override
    public void destroy(){
        //..
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //..
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        filterChain.doFilter(request, response);


    }
}
