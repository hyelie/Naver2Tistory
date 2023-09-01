package migrator.targetBlogConfigFactories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import auth.AuthClient;
import auth.TistoryClient;
import convert.converters.Converter;
import convert.converters.tistory.TistoryConverter;

public class TistoryConfigFactoryTest {
    @Test
    public void testCreateAuthClient() {
        // given
        TistoryConfigFactory factory = new TistoryConfigFactory();

        // when
        AuthClient authClient = factory.createAuthClient();

        // then
        // expected null because TistoryClient.authorize() does not work
        assertNull(authClient);
    }

    @Test
    public void testCreateConverter() {
        // given
        TistoryConfigFactory factory = new TistoryConfigFactory();

        // when
        AuthClient authClient = factory.createAuthClient();
        Converter converter = factory.createConverter(authClient);

        // then
        assertNotNull(converter);
        assertTrue(converter instanceof TistoryConverter);
    }
}