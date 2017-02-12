package org.jfl110.example;

import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Test;

/**
 * Tests the validity of the bindings set-up by the AppServletContextListener
 *
 * @author JFL110
 */
public class TestAppBindings {

	@Test
	public void testAppBindings(){
		 new AppServletContextListener()
		 .contextInitialized(new ServletContextEvent(mock(ServletContext.class)));
	}
}