package service;

import com.axreng.backend.repository.Repository;
import com.axreng.backend.service.ServiceFind;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceFindTest {

    @Test
    public void testeExecuteWithWrongKeyword()  {
        // validate key word between 4 and 32 and convert to lowercase because the search needs case-insensitive

        ServiceFind service = new ServiceFind();
        try {
            service.execute(null,null,"123");
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("The search term must have a minimum of 4 and a maximum of 32!", e.getMessage());
        }

        try {
            service.execute(null,null,"123123123123123123123123123123123");
            fail("Expected an exception");
        } catch (Exception e) {
            assertEquals("The search term must have a minimum of 4 and a maximum of 32!", e.getMessage());
        }
    }

    @Test
    public void testeExecuteWithNewKeyword() throws Exception {
        ServiceFind service = new ServiceFind();
        Repository repository = new Repository();
        String baseUrl = "http://example.com";
        JsonObject response = service.execute(repository,baseUrl,"teste");

        assertNotNull(response.get("id"));
        var id = response.get("id").getAsString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        assertEquals(jsonObject.toString(), response.toString());
    }

    @Test
    public void testeExecuteWithOldKeywordAndStatusActive() throws Exception {
        ServiceFind service = new ServiceFind();
        Repository repository = new Repository();
        String baseUrl = "http://example.com";

        JsonObject response = service.execute(repository,baseUrl,"teste");
        var id = response.get("id").getAsString();

        ServiceFind serviceTwo = new ServiceFind();
        JsonObject responseTwo = serviceTwo.execute(repository,baseUrl,"teste");
        var idTwo = responseTwo.get("id").getAsString();

        assertEquals(id, idTwo);
    }

}
