package demoapi;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Login {

    @Test
    public void verifyLoginWithValidCredentials() throws IOException {
        String response = sendLoginRequest("validUser", "validPass");
        Assert.assertTrue(response.contains("token")); // Assuming successful login returns a token
    }

    @Test
    public void verifyLoginWithInvalidPassword() throws IOException {
        String response = sendLoginRequest("validUser", "invalidPass");
        Assert.assertFalse(response.contains("token"));
    }

    @Test
    public void verifyLoginWithInvalidUsername() throws IOException {
        String response = sendLoginRequest("invalidUser", "pass");
        Assert.assertFalse(response.contains("token"));
    }

    @Test
    public void verifyLoginWithMissingCredentials() throws IOException {
        String response = sendLoginRequest("", "");
        Assert.assertFalse(response.contains("token"));
    }

    @Test
    public void verifyLoginWithUnusualCharactersInCredentials() throws IOException {
        String response = sendLoginRequest("🤖👽🎃", "🔑🔒");
        Assert.assertFalse(response.contains("token"));
    }

    private String sendLoginRequest(String username, String password) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://dummyjson.com/auth/login");

        // Constructing JSON string payload
        String json = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }
}
