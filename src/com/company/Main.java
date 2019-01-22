package com.company;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    static String getWeather(String location){
        //You can connect it to an actual weather API here
        return HttpConn.getRequest("http://weather.taheer.me/weather.php?location="+location.replace(" ", "+"));
    }

    public static void main(String[] args) throws IOException {
        Chatbot talker = new Chatbot();
        //You can manually add new intents here
        Intent weather  = talker.newIntent();
        weather.trainPhrase("What's the weather in @location");
        weather.trainPhrase("How's the weather at @location");

        //Set the action of the intent to a function call
        weather.setAction( () -> System.out.println(getWeather(weather.getCurrentParam())) );

        //This will ensure that the user is prompted if they forget to include a parameter
        weather.setParametersRequired(true);

        //Some standard procedures after creating our Chatbot instance
        talker.namePrompt();
        talker.introduce();
        talker.listenNext();


    }
}
