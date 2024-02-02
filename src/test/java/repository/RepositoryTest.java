package repository;

import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.repository.Repository;
import com.google.gson.JsonArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest  {

    @Test
    void testAddKeywordWhenNotExistKeyWord() {
        Repository repository = new Repository();
        String keyword = "teste";
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int allowedCharactersLength = 8;

        String id = repository.addKeyword(keyword);
        assertEquals(allowedCharactersLength, id.length());
        for (char c : id.toCharArray()) {
            if (allowedCharacters.indexOf(c) == -1) {
                fail();
            }
        }
        assertTrue(repository.containsKeyword(keyword));
        assertTrue(repository.containsId(id));
        assertEquals(repository.getStatusSearch(id), SearchStatus.ACTIVE);
        assertEquals(repository.getListUrlsById(id).toString(), new JsonArray().toString());
    }

    @Test
    void testAddKeywordWhenExistKeyWord() {
        Repository repository = new Repository();
        String keyword = "teste";

        String id = repository.addKeyword(keyword);
        SearchStatus status = repository.getStatusSearch(id);
        JsonArray urls = repository.getListUrlsById(id);

        String anotherId = repository.addKeyword(keyword);
        SearchStatus anotherStatus = repository.getStatusSearch(anotherId);
        JsonArray anotherUrls = repository.getListUrlsById(anotherId);

        assertTrue(repository.containsId(anotherId));
        assertEquals(id, anotherId);
        assertEquals(status, anotherStatus);
        assertEquals(urls, anotherUrls);
    }

    @Test
    void testRestartData() {
        Repository repository = new Repository();
        String keyword = "teste";
        String id = repository.addKeyword(keyword);
        repository.setStatusDone(id);
        var arr = repository.getListUrlsById(id);
        arr.add("http://url.com/1");
        assertEquals(1, repository.getListUrlsById(id).size());
        assertEquals(SearchStatus.DONE, repository.getStatusSearch(id));

        repository.restartData(id);
        assertEquals(0, repository.getListUrlsById(id).size());
        assertEquals(SearchStatus.ACTIVE, repository.getStatusSearch(id));
    }

}
