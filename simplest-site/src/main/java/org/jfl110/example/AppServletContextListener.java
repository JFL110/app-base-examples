package org.jfl110.example;

import static org.jfl110.prender.api.StringRenderNodes.string;

import java.util.logging.Logger;

import org.jfl110.prender.impl.parse.DefaultParsingModule;
import org.jfl110.prender.impl.render.DefaultRenderingModule;
import org.jfl110.prender.impl.resource.DefaultResourceSourceModule;
import org.jfl110.prender.impl.serve.Serving;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * Main entry point for the app.
 * 
 * @author JFL110
 */
public class AppServletContextListener extends GuiceServletContextListener{
	
	private static Logger logger = Logger.getLogger(AppServletContextListener.class.getSimpleName());

	@Override
	protected Injector getInjector() {
		logger.info("Creating injector");
		return Guice.createInjector(new AppModule());
	}
	
	
	class AppModule extends ServletModule{
		@Override
		protected void configureServlets() {
				
			install(new DefaultRenderingModule());
			install(new DefaultResourceSourceModule());
			install(new DefaultParsingModule());
			
			install(Serving.of("/").with(string("HELLO!")));
		}
	}
}
