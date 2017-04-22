package org.jfl110.example;

import java.net.URI;
import java.util.EnumSet;

import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;

/**
 * An embedded jetty server to use for testing with Guice.
 *
 * @author JFL110
 */
public class EmbeddedJetty {

	private final int port = 8080;
	private final String resourceBasePath = "src/main/webapp";
	private final ServletContextListener contextListener = new AppServletContextListener();
	
	private Server server;

	public static EmbeddedJetty ebeddedJetty() {
		return new EmbeddedJetty();
	}

	private EmbeddedJetty() {}

	public void start() throws Exception {
		server = new Server(port);

		ServletContextHandler context = new ServletContextHandler();
		context.setResourceBase(resourceBasePath);       
		context.addEventListener(contextListener);
		context.addFilter(GuiceFilter.class, "/*",EnumSet.of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
		
		server.setHandler(context);
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	public URI getBaseUri() {
		return server.getURI();
	}
}
