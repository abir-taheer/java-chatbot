package com.company;

import java.io.IOException;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Function;

class Intent {

    //Give the intent an id so that it can be referenced using that later
    private int id = (int)(Math.random() * 90000 + 1);
    private ArrayList<String[][]> train_phrases = new ArrayList<>();
    private boolean parametersRequired = false;
    private boolean hasParameters = false;

    private Runnable action = () -> System.out.println("There is currently no action associated to this intent");

    public void setAction(Runnable action) {
        this.action = action;
    }

    void doAction() {
        action.run();
    }

    public int getIntentId() {
        return id;
    }

    private String currentParam = "";

    public void setCurrentParam(String currentParam) {
        this.currentParam = currentParam;
    }

    public String getCurrentParam() {
        return currentParam;
    }

    public boolean getParametersRequired(){
        return parametersRequired;
    }
    public boolean getHasParameters(){
        return hasParameters;
    }
    public String getParamaterName(){
        return paramaterName;
    }

    //This will allow us to remind the user to insert a parameter if they every forget
    private String paramaterName = "";
    public WordProcessor wp = new WordProcessor();


    public void setParametersRequired(boolean parametersRequired) {
        this.parametersRequired = parametersRequired;
    }

    public void trainPhrase(String phrase){
        String[][] deciphered =  wp.deciperWords(wp.separatePhrase(phrase));
        for( int y = 0; y < deciphered[1].length ; y++){
            if( deciphered[1][y].startsWith("@") ){
                //There is a parameter in this current intent, update the hasParameter boolean accordingly
                this.hasParameters = true;
                this.paramaterName = deciphered[1][y].replace("@", "");
            }
        }
        train_phrases.add(deciphered);
    }

    public String[][] getTrainingI(){
        String[][] complete = new String[train_phrases.size()][];
        //Iterate through all of the training phrases
        for( int x = 0; x < train_phrases.size(); x++ ){
            complete[x] = train_phrases.get(x)[0];
        }
        return complete;
    }
    public String[][] getTrainingK(){
        String[][] complete = new String[train_phrases.size()][];
        //Iterate through all of the training phrases
        for( int x = 0; x < train_phrases.size(); x++ ){
            complete[x] = train_phrases.get(x)[1];
        }
        return complete;
    }

}