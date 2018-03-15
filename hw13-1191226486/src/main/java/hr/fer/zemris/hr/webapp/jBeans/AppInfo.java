package hr.fer.zemris.hr.webapp.jBeans;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class that implements servlet listener that adds information when it was
 * called into servlet context's attributes.
 * 
 * @author tina
 *
 */
@WebListener
public class AppInfo implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("appStarted", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
