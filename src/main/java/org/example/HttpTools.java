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

public class HttpTools {
    private static final String[] url = {"http://api.nbp.pl/api/exchangerates/tables/A/", "http://api.nbp.pl/api/exchangerates/tables/B/"};

    public static int getUrlLength() {
        return url.length;
    }

    public String apiNBP(int i) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .uri(new URI(url[i]))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processResponse(String httpResponse){
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(httpResponse, JsonArray.class);
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            JsonArray ratesArray = jsonObject.getAsJsonArray("rates");
            for (JsonElement rateElement : ratesArray) {
                JsonObject rateObject = rateElement.getAsJsonObject();
                String code = rateObject.get("code").getAsString();
                double value = rateObject.get("mid").getAsDouble();
                System.out.printf("Kod waluty: %s, kurs: %.4f\n", code, value);
            }
        }

    public static void main(String[] args) {

    }
}
