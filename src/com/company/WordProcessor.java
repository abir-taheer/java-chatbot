package com.company;
import java.util.ArrayList;
import java.util.Arrays;

/*TODO ADD A WORD COMPARISON FUNCTION THAT ATTEMPTS TO FIND AND COMPARE SYNONYMS OF WORDS IF POSSIBLE */

class WordProcessor {
    public static String[] separatePhrase( String phrase){
        String[] s = phrase.toLowerCase().replace("'s", " is").split("\\s+");
        return s;
    }

    public static String[][] deciperWords(String[] phrase) {
        //initialize an array for the interrogative words
        ArrayList<String> inters = new ArrayList<>();
        ArrayList<String> keywords = new ArrayList<>();
        for( int x = 0; x < phrase.length ; x++ ){
            if( hasIWord( phrase[x] ) ){
                //handle the special case that the word is an interrogative word and add it to a special array containing those
                inters.add(phrase[x]);
            } else {
                //add the rest of the "key words" to a separate array for comparison later
                keywords.add(phrase[x]);
            }
        }
        String[][] returnables = new String[2][];
        String[] inter = inters.toArray(new String[inters.size()]);
        String[] keyword = keywords.toArray(new String[keywords.size()]);
        returnables[0] = inter;
        returnables[1] = keyword;
        return returnables;

    }

    //Custom function to check for interrogative words
    public static boolean hasIWord(String word){
        String[] interWords = {"which", "what", "who", "whom", "whose", "where", "wither", "whence", "when", "how", "why", "are", "do", "is", "at", "in"};
        if(Arrays.asList(interWords).contains(word)){
            //This current word is an interrogative word, return a true
            return true;
        }
        return false;
    }

}
