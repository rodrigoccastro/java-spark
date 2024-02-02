import com.axreng.backend.WebRoutes;
import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.repository.Repository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {

    @Test
    void testCrawlEndpointFind() throws IOException, InterruptedException {
        Repository repository = new Repository();
        String baseUrl = "http://example.com";
        WebRoutes.configureRoutes(repository, baseUrl);

        String url = "http://localhost:4567/crawl";
        String jsonPayload = "{\"keyword\": \"value\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        JsonObject responseJson = new Gson().fromJson(response.body(), JsonObject.class);
        String id = responseJson.get("id").getAsString();
        assertNotNull(id);
    }


    @Test
    void testCrawlEndpointResult() throws IOException, InterruptedException {
        Repository repository = new Repository();
        String baseUrl = "http://example.com";
        WebRoutes.configureRoutes(repository, baseUrl);

        String url = "http://localhost:4567/crawl";
        String jsonPayload = "{\"keyword\": \"value\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject responseJson = new Gson().fromJson(response.body(), JsonObject.class);
        String id = responseJson.get("id").getAsString();

        String urlGet = "http://localhost:4567/crawl/"+id;
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(URI.create(urlGet))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> responseGet = httpClient.send(requestGet, HttpResponse.BodyHandlers.ofString());

        JsonObject responseJsonGet = new Gson().fromJson(responseGet.body(), JsonObject.class);
        String idGet= responseJsonGet.get("id").getAsString();
        String statusGet = responseJsonGet.get("status").getAsString();
        String urlsGet = responseJsonGet.get("urls").getAsJsonArray().toString();

        assertEquals(id, idGet);
        assertEquals(SearchStatus.ACTIVE.getLabel(), statusGet);
        assertEquals(new JsonArray().toString(), urlsGet);
    }

}
