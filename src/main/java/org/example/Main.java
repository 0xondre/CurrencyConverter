package org.example;

public class Main {
    public static void main(String[] args) {
        HttpTools httpTools = new HttpTools();
        int i = 0;
        while(i<HttpTools.getUrlLength()){
            httpTools.processResponse(httpTools.apiNBP(i));
            i++;
        }

    }
}