package org.jfl110.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test for authentication.
 *
 * @author JFL110
 */
public class TestAuthenticationIntegration {

	private static final EmbeddedJetty server = EmbeddedJetty.ebeddedJetty();

	@BeforeClass
	public static void beforeClass() throws Exception {
		server.start();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		server.stop();
	}

	
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