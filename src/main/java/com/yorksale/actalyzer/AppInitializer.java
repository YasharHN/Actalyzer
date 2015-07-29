package com.yorksale.actalyzer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
//        servletContext.addListener(new Log4jConfigListener());
//        servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.xml");

        web(servletContext, context);
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.yorksale.actalyzer");
        return context;
    }

    private void web(ServletContext servletContext, AnnotationConfigWebApplicationContext context) {
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
