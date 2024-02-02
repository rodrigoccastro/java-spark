package service;

import com.axreng.backend.service.ServiceUrl;
import com.axreng.backend.util.UrlWrapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceUrlTest {

    @Test
    public void testExtractLinksByBaseUrl() throws IOException {
        String htmlContent = "<html><body><a href=\"/page1\">Page 1</a><a href=\"http://example.com/page2\">Page 2</a>" +
                                "<a href=\"http://anotherdomain.com/page2\">Page 3</a></body></html>";
        UrlWrapper mockUrl = mock(UrlWrapper.class);
        when(mockUrl.getUrl()).thenReturn("http://example.com");

        BufferedReader mockReader = new BufferedReader(new StringReader(htmlContent));
        when(mockUrl.getInputStream()).thenReturn(new MockInputStream(mockReader));

        ServiceUrl serviceUrl = new ServiceUrl(mockUrl);
        List<String> result = serviceUrl.extractLinksByBaseUrl();
        assertEquals(3, result.size());
        assertEquals("http://example.com", result.get(0));
        assertEquals("http://example.com/page1", result.get(1));
        assertEquals("http://example.com/page2", result.get(2));
    }

    @Test
    public void testGetPageContent() throws IOException {
        UrlWrapper mockUrl = mock(UrlWrapper.class);
        String pageContent = "This is the content of the page";
        BufferedReader mockReader = new BufferedReader(new StringReader(pageContent));
        when(mockUrl.getInputStream()).thenReturn(new MockInputStream(mockReader));

        ServiceUrl serviceUrl = new ServiceUrl(mockUrl);
        String result = serviceUrl.getPageContent();
        assertEquals(pageContent + "\n", result);
    }

    private static class MockInputStream extends java.io.InputStream {
        private final BufferedReader reader;

        MockInputStream(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public int read() throws IOException {
            return reader.read();
        }
    }
}
