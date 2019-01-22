# java-chatbot
A library for making chatbots in the terminal. 
I was intrigued by apps like dialogflow that use machine learning to identify an intent to speech and tried to make an algorithmic version of the intent matching without using machine learning.

#Uses
Since it's a library, it's main purpose is to be used in other cases where someone would create a chatbot using the library and thus, there are a lack of commands that currently exist

#Example of using the library
```
        Chatbot talker = new Chatbot();
        //You can manually add new intents here
        Intent weather  = talker.newIntent();
        weather.trainPhrase("What's the weather in @location");
        weather.trainPhrase("How's the weather at @location");
        //Set the action of the intent to a function call
        weather.setAction( () -> System.out.println(getWeather(weather.getCurrentParam())) );

```

#Things to try
*Asking the chatbot:
>What's the weather
>What's the weather in New York
>Banana

As well as making your own chatbots and intents using the library
