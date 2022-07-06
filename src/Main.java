import lingolava.Mathx;
import lingologs.Script;
import lingologs.Texture;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader brInput, br;
        String textContent;

        brInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the path to the text to be evauated:");
        String filePath = brInput.readLine();
        // TODO: check if provided path is actually a path
        textContent = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);

        System.out.printf(textContent);




//        Text textToEvaluate = new Text(new Script("hello how are you do you want to how a hello?"));
//        System.out.println(textToEvaluate.getContent());
/*
        Script text = new Script("hello how are you do you want to how a hello?");
        System.out.println(getNumWords(text));
        System.out.println(getNumCommas(text));
        System.out.println(checkForeignWords(text));
        System.out.println(checkAbbreviations(text));
        //getAverageValueSentence(text);
        System.out.println(getWordFrequency(text));
        */

    }

    /*
    Überprüfen eines übergebenen Textes
     */

    // Pro Satz

        /*
    private static int getNumWords(Script text){
        ArrayList<Script> wordList = getWordList(text);
        return wordList.size();
    }

    private static int getNumCommas(Script text){
        return text.count(",");
    }

    // Pro Wort

    private static int getLengthWord(Script word){
        return word.length();
    }

    //funktioniert noch nix
    private static ArrayList<Script> checkForeignWords(Script text) throws IOException{
        File foreignWordsFile = new File("./src/Data/Fremdwörter.txt");
        FileReader fr = new FileReader(foreignWordsFile);

        ArrayList<Script> wordsList = getWordList(text);
        ArrayList<String> foreignWordsList = new ArrayList<>();
        ArrayList<Script> foreignWordsInText = new ArrayList<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null){
            foreignWordsList.add(line);
            for (Script word : wordsList){
                for (String foreignWord : foreignWordsList){
                    if (word.toString().toLowerCase().equals(foreignWord.toLowerCase())){
                        foreignWordsInText.add(word);
                    }
                }
            }
        }
        return foreignWordsInText;
    }

    private static boolean checkAbbreviations(Script text) throws IOException {
//        File abbreviationFile = new File("./src/Data/Abkürzungen.txt");
//        FileReader fr = new FileReader(abbreviationFile);
//
//        ArrayList<String> abbreviationsList = new ArrayList<>();
//        ArrayList<Script> abbreviationsInText = new ArrayList<>();
//
//        BufferedReader br = new BufferedReader(fr);
//        String line;
//        while ((line = br.readLine()) != null){
//
//        }
        //funktioniert nur bei den Abkürzungen
        boolean isAbbreviations = text.match("([A-ZÜÖÄßa-zäöü]*.( |-)?){1,}");

        return isAbbreviations;
    }

    //Satzebene

    private static void getMeanNumWordsPerSentence(Script text) {

    }

    private static double getAverageValueSentence(Script text, ArrayList<Integer> arr) {
        List<Script> sentencesList = text.split("[!?.:]+");

        for (Script sentence : sentencesList){
            arr.add(getNumWords(sentence));
        }

        double meanValue = Mathx.mean(arr);
        return meanValue;
    }
    //Wortebene

    private static double getAverageLengthOfWord (Script text){
        ArrayList<Script> wordsList = getWordList(text);
        List<Integer> lengthWordList = new ArrayList<>();

        for (Script word: wordsList){
            lengthWordList.add(getLengthWord(word));
        }

        double meanLengthWord = Mathx.mean(lengthWordList);
        return meanLengthWord;
    }

    /* Verarbeitung der Daten der Überprüfung */
    //funktioniert noch nix:< Warummmmmmm

    /*
    private static double getEntropy (Script text){
        return Mathx.info(getWordFrequency(text));
    }

    private static List<Integer> getWordFrequency (Script text){
        HashMap<Script, Integer> wordCount = new HashMap<>();
        ArrayList<Script> wordsList = getWordList(text);

        for (Script word : wordsList){
            Script processed = word.toLower();
            if (wordCount.containsKey(processed)){
                wordCount.put(processed, wordCount.get(processed) + 1);
            }
            else {
                wordCount.put(processed, 1);
            }
        }
        List<Integer> frequenciesList = new ArrayList<>(wordCount.values());
        return frequenciesList;
    }





    private static ArrayList<Script> getWordList(Script text){
        ArrayList<Script> words = new ArrayList<>();
        List<Script> wordListFromText = text.split("\\w+");

        for (Script word:wordListFromText){
            words.add(word);
        }
        return words;
    }
    */
}