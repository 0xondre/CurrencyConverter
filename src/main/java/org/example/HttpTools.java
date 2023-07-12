package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HttpTools {
    private static final String[] URL = {"http://api.nbp.pl/api/exchangerates/tables/A/", "http://api.nbp.pl/api/exchangerates/tables/B/"};

    public static int getUrlLength() {
        return URL.length;
    }

    public String apiNBP(int i) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .uri(new URI(URL[i]))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processResponse(String httpResponse, HashMap<String, Double> currencyMap) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(httpResponse, JsonArray.class);
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        JsonArray ratesArray = jsonObject.getAsJsonArray("rates");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("logs.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
            for (JsonElement rateElement : ratesArray) {
                JsonObject rateObject = rateElement.getAsJsonObject();
                String code = rateObject.get("code").getAsString();
                double value = rateObject.get("mid").getAsDouble();
                currencyMap.put(code, value);

                printWriter.printf("Currency code: %s, value: %.4f\n", code, value);
            }
        }

}
