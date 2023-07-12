package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Converter {
    public void askForCurrency(HashMap<String, Double> currencyMap) {
        System.out.println("Enter currency code and amount you want to convert to PLN: ");
        Scanner scanner = new Scanner(System.in);
        String currencyCode = scanner.next();
        try{
            Double currencyValue = scanner.nextDouble();
            System.out.printf("Your currency is worth %.4f PLN based on data published by NBP today",currencyMap.get(currencyCode.toUpperCase())*currencyValue);
        }
        catch (Exception e){
            System.out.println("Error: wrong input");
        }
    }
}
