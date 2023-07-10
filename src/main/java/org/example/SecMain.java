package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SecMain {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            String url = "http://api.nbp.pl/api/exchangerates/tables/A/";
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .uri(new URI(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.body());

            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(httpResponse.body(), JsonArray.class);
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            JsonArray ratesArray = jsonObject.getAsJsonArray("rates");
            for (JsonElement rateElement : ratesArray) {
                JsonObject rateObject = rateElement.getAsJsonObject();
                String code = rateObject.get("code").getAsString();
                double value = rateObject.get("mid").getAsDouble();
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
