package org.jfl110.example;

import static org.jfl110.prender.api.HtmlPageRenderNode.htmlPage;
import static org.jfl110.prender.api.HtmlPageRenderNode.localHtmlPage;
import static org.jfl110.prender.api.StringRenderNodes.string;
import static org.jfl110.prender.api.render.RenderMaps.renderMap;
import static org.jfl110.prender.impl.parse.RenderTags.tag;

import java.net.MalformedURLException;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.resources.URLResourceSources;
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
public class AppServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppModule());
	}

	private static final RenderNode TEMPLATE_PAGE = localHtmlPage("/template.html");
	private static final RenderNode INDEX_PAGE_CONTENT = localHtmlPage("/index-page-content.html").bodyOnly();
	private static final String TITLE_PLACEHOLDER_KEY = "TITLE";
	private static final String CONTENT_PLACEHOLDER_KEY = "CONTENT";

	static class AppModule extends ServletModule {
		@Override
		protected void configureServlets() {
			
			install(new DefaultRenderingModule());
			install(new DefaultResourceSourceModule());
			install(new DefaultParsingModule());
			
			RenderNode remoteComingSoonPage = remotePage();
			
			RenderMap indexPage = renderMap(TEMPLATE_PAGE);
			indexPage.putPlaceholderValue(TITLE_PLACEHOLDER_KEY, tag("title").addChild(string("Simple GAE App")).build());
			indexPage.putPlaceholderValue(CONTENT_PLACEHOLDER_KEY, INDEX_PAGE_CONTENT);

			install(Serving.of("/").with(indexPage));
			
			install(Serving.of("/word")
					.with(string("bird")));
			
			install(Serving.of("/coming-soon").with(remoteComingSoonPage));
		}
		
		
		private RenderNode remotePage(){
			try {
				return htmlPage(URLResourceSources.urlResource("https://raw.githubusercontent.com/JFL110/app-base-examples/master/simple-gae-app/remote-resources/page-coming-soon.html"));
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}	
}