# java-chatbot
A library for making chatbots in the terminal. 
I was intrigued by apps like dialogflow that use machine learning to identify an intent to speech and tried to make an algorithmic version of the intent matching without using machine learning.

# Uses
Since it's a library, it's main purpose is to be used in other cases where someone would create a chatbot using the library and thus, there are a lack of commands that currently exist

# Example of using the library
```
        Chatbot talker = new Chatbot(); //Create an instance of the chatbot
     
        Intent weather  = talker.newIntent(); //Add a new intent
        weather.trainPhrase("What's the weather in @location"); //Add some training phrases for the intent
        weather.trainPhrase("How's the weather at @location"); 
        
        weather.setParametersRequired(true); //Make it so that a parameter is required. This is also handled by the library
        weather.setAction( () -> System.out.println(getWeather(weather.getCurrentParam()))); //Set a custom action to be called with the intent
        

```

# Things to try
* Asking the chatbot:
>What's the weather 

>What's the weather in New York 

>Banana 

* As well as making your own chatbots and intents using the library


# Drawbacks
* The library currently only supports one parameter being passed for an intent.
