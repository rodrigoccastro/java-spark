package util;

import com.axreng.backend.util.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import spark.Request;

public class UtilTest {

    @Test
    public void testGetValidateBaseUrlWhenExistsUrl() throws Exception {
        String validBaseUrl = "http://example.com";
        assertEquals(validBaseUrl, Util.getValidateBaseUrl(validBaseUrl));
    }

    @Test
    public void testGetValidateBaseUrlWhenNotExistsUrl() {
        try {
            Util.getValidateBaseUrl(null);
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("Not found BASE_URL!", e.getMessage());
        }
    }

    @Test
    public void testGetKeywordFromBodyWhenExistsKeyword() throws Exception {
        Request validRequest = createRequestWithBody("{\"testKey\": \"testValue\"}");
        assertEquals("testValue", Util.getKeywordFromBody(validRequest, "testKey"));
    }

    @Test
    public void testGetKeywordFromBodyWhenNotExistsKeyword() {
        try {
            Util.getKeywordFromBody(createRequestWithBody(null), "testKey");
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("The body cannot be null!", e.getMessage());
        }
    }

    @Test
    public void testGetKeywordFromParamsWhenExistsParams() throws Exception {
        Request validRequest = createRequestWithParams("testKey", "testValue");
        assertEquals("testValue", Util.getKeywordFromParams(validRequest, "testKey"));
    }

    @Test
    public void testGetKeywordFromParamsWhenNotExistsParams() {
        try {
            Util.getKeywordFromParams(createRequestWithParams("testKey", null), "testKey");
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("The param testKey cannot be null!", e.getMessage());
        }
    }

    private Request createRequestWithBody(String body) {
        return new Request() {
            @Override
            public String body() {
                return body;
            }
        };
    }

    private Request createRequestWithParams(String key, String value) {
        return new Request() {
            @Override
            public String params(String param) {
                if (param.equals(key)) {
                    return value;
                }
                return null;
            }
        };
    }

}
