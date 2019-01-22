package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Chatbot {

    //Give each of our chatbot instances an id for whatever use they may have
    private int id = (int)(Math.random() * 90000 + 1);

    static Scanner inp = new Scanner(System.in);
    static WordProcessor wp = new WordProcessor();
    private String name;
    private String client_name;

    String getClient_name(){
        return client_name;
    }

    public void introduce(){
        System.out.println("Hey "+client_name+"! My name is " + name + " and I'll be your personal assistant!");
    }

    public void namePrompt(){
        System.out.print("What do you want to call your assistant? \n");
        if(inp.hasNextLine()){
            setName(inp.nextLine());
        } else {
            System.out.println("Sorry, I didn't get that. What do you want to call your assistant?");
        }

        System.out.println("Now what should I call you?");
        if(inp.hasNextLine()){
            setClient_name(inp.nextLine());
        } else {
            System.out.println("Sorry, I didn't get that. What's your name?");
        }
    }

    public Intent newIntent(){
        Intent i = new Intent();
        myIntents.add(i);
        return i;
    }

    public void setName(String n) {
        this.name = n;
    }
    public void setClient_name(String n){
        this.client_name = n;
    }

    //id getter
    public long getChatbotId() {
        return id;
    }

    private ArrayList<Intent> myIntents = new ArrayList<Intent>();

    public void listenNext() throws IOException {
        //Our listener for user desires
        do {
            ///Ask them what they want to do
            System.out.println("What would you like?");

            //Send their response to a processing function
            String[] respArr = wp.separatePhrase(inp.nextLine());
            String[][] deciphered = wp.deciperWords(respArr);
            int matchedIntId = matchIntent(deciphered);
            if( matchedIntId == -1 ){
                System.out.println("I don't know what to do. There might not be an intent associated to that phrase.");
            } else {
                Intent matchedIntent = getIntent(matchedIntId);
                //check if that intent requires a parameter, if not, perform the action associated with the intent
                if( matchedIntent.getHasParameters() ){
                    //get the parameters from another helper function
                    String param = findParameters(deciphered[1], matchedIntent.getTrainingK());
                    if( param == "" ){
                        if( matchedIntent.getParametersRequired() ){
                            System.out.println("Sorry, but that action requires a " + matchedIntent.getParamaterName() + " parameter.");
                        } else {
                            //call the response
                            matchedIntent.doAction();
                        }
                    } else {
                        //call the response
                        matchedIntent.setCurrentParam(param);
                        matchedIntent.doAction();
                    }
                } else {
                    //call the response
                    matchedIntent.doAction();
                }
            }

            listenNext();
        } while(!inp.hasNextLine());
    }

    public String findParameters(String[] queryK, String[][] intentK){

        for( int x = 0; x < intentK.length ; x++ ){
            for( int y = 0; y < intentK[x].length ; y++ ){
                queryK = removeItem(queryK, intentK[x][y] );
            }
        }
        if( queryK.length > 0 ){
            return String.join(" " , queryK);
        }
        return "";
    }

    public String[] removeItem(String[] arr, String toRemove){
        ArrayList<String> newArr = new ArrayList<>();
        for ( int x = 0; x < arr.length ; x++ ){
            if( ! arr[x].trim().equals(toRemove.trim()) ){
                newArr.add(arr[x]);
            }
        }
        return newArr.toArray(new String[newArr.size()]);
    }

    Intent getIntent(int ID){
        for( int x = 0; x < myIntents.size() ; x++ ){
            if( myIntents.get(x).getIntentId() == ID ){
                return myIntents.get(x);
            }
        }
        return new Intent();
    }

    /*TODO finish the intent matching */
    //Look through all of the intents that were created and compare their contents to that of the current
    //This will return the ID of the intent that it is matched with
    public int matchIntent(String[][] deciphered){
        double match = 0;
        int intentMatch = -1;
        for( int x = 0 ; x < myIntents.size() ; x++ ){
            //Get an intent for comparison now
            Intent currentIntent = myIntents.get(x);
            String[] qI = deciphered[0];
            String[] qK = deciphered[1];

            String[][] iI = currentIntent.getTrainingI();
            String[][] iK = currentIntent.getTrainingK();

            double iCont = 0;
            double kCont = 0;

            iCont = compareIt(qI, iI, iCont);
            kCont = compareIt(qK, iK, kCont);
            double tMatch = (0.2 * iCont) + (0.8 * kCont);
            if( tMatch > match && tMatch > 20 ){
                match = tMatch;
                intentMatch = myIntents.get(x).getIntentId();
            }
        }
        return intentMatch;
    }

    private double compareIt(String[] aq, String[][] ap, double comp) {
        for( int y = 0; y < ap.length ; y++ ){
            //We are now addressing a single dimensional array
            double tCont = 0;
            for( int z = 0; z < ap[y].length ; z++ ){
                //Now we're talking to the individual interrogative words themselves
                if( Arrays.asList(aq).contains(ap[y][z]) ){
                    //That's one word match, add a point for the comparison
                    tCont++;
                }
            }
            try {
                tCont = ((tCont / ap[y].length) * 100);
            } catch(Throwable er){}
            if( tCont > comp ){ comp = tCont; }
        }
        return comp;
    }


}
