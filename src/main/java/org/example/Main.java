package org.example;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Double> currencyMap = new HashMap<>();
        HttpTools httpTools = new HttpTools();
        int i = 0;
        while(i<HttpTools.getUrlLength()){
            httpTools.processResponse(httpTools.apiNBP(i), currencyMap);
            i++;
        }

        Converter converter = new Converter();
        converter.askForCurrency(currencyMap);
    }
}