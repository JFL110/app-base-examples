package org.jfl110.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jfl110.testing.utils.EmbeddedJetty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Integration test for authentication.
 *
 * @author JFL110
 */
public class TestIntegration {

	@ClassRule
	public static final EmbeddedJetty server = EmbeddedJetty.embeddedJetty()
												.withContextListener(new AppServletContextListener())
												.build();

	
	/**
	 * Tests the '/word' path
	 */
	@Test
	public void testWordPath() {
		String word = ClientBuilder
						.newClient()
						.target(server.getBaseUri()).path("word")
						.request(MediaType.TEXT_PLAIN_TYPE).get(String.class);

		assertEquals("bird",word);
	}
	
	
	/**
	 * Tests the '/' path
	 */
	@Test
	public void testIndexPath() {
		String indexPathHtml = ClientBuilder
								.newClient()
								.target(server.getBaseUri()).path("/")
								.request(MediaType.TEXT_HTML).get(String.class);
		
		assertNotNull(indexPathHtml);
		
		Document doc = Jsoup.parse(indexPathHtml);
		assertThat(doc.select("title").text(), is("Simple GAE App"));
		assertThat(doc.select("h1").text(), is("App Base Examples : Simple GAE App!"));
	}
}