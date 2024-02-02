package service;

import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.repository.Repository;
import com.axreng.backend.service.ServiceResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceResultTest {

    @Test
    public void testExecuteWhenExistsId() throws Exception {
        Repository mockRepository = mock(Repository.class);
        String id = "123";
        SearchStatus searchStatus = SearchStatus.DONE;
        when(mockRepository.containsId(id)).thenReturn(true);
        when(mockRepository.getStatusSearch(id)).thenReturn(searchStatus);
        when(mockRepository.getListUrlsById(id)).thenReturn(createTestUrls());

        ServiceResult serviceResult = new ServiceResult();
        JsonObject result = serviceResult.execute(mockRepository, id);

        // Verifying result JsonObject
        assertNotNull(result);
        assertEquals(id, result.get("id").getAsString());
        assertEquals(searchStatus.getLabel(), result.get("status").getAsString());
        assertTrue(result.get("urls").isJsonArray());
        JsonArray urlsArray = result.getAsJsonArray("urls");
        assertEquals(3, urlsArray.size());
    }

    @Test
    public void testExecuteWhenNotExistsId() throws Exception {
        Repository mockRepository = mock(Repository.class);
        String id = "invalidId";
        when(mockRepository.containsId(id)).thenReturn(false);

        ServiceResult serviceResult = new ServiceResult();
        try {
            serviceResult.execute(mockRepository, id);
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("Id " + id + " not exists in server!", e.getMessage());
        }
    }

    private JsonArray createTestUrls() {
        JsonArray urlsArray = new JsonArray();
        urlsArray.add("http://example.com/page1");
        urlsArray.add("http://example.com/page2");
        urlsArray.add("http://example.com/page3");
        return urlsArray;
    }

}
