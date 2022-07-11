import lingologs.Script;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    private static String textAsString;

    public static void main(String[] args) throws IOException {
//        textAsString = readFile1();
//        System.out.printf(textAsString);

        Script scriptFromFile = new Script("hello hallo. Abtrakt hgfuz. HeeLLO z. B. hgd ggf. abbauprodukt DDR");

        //process Script
        Processor processedScript = new Processor(scriptFromFile);

        //print Abbreviations
//        System.out.println(processedScript.getAbbrMeaning());
//        System.out.println(processedScript.countAbbrInText()); //false

        //filter Abbr
        Script filteredAbbrScript = processedScript.removeAbbr();
        Script normalizedScript = filteredAbbrScript.toLower();

        //evaluate Text
        Text textToEvaluate = new Text(new Script(normalizedScript));
        Evaluator evaluator = new Evaluator(textToEvaluate);

        ArrayList<Sentence> sentencesListFromText = evaluator.getSentencesListFromText();
        ArrayList<Word> wordsListFromText = evaluator.getWordsListFromText(sentencesListFromText);

        System.out.println(evaluator.countCompoundWords(wordsListFromText));
        System.out.println( evaluator.countAcronyms(wordsListFromText));
        System.out.println(evaluator.countForeignWords(wordsListFromText));

        //drücken verbesserbare Wörter
        printImprovableWords(evaluator);

        //drücken verbesserbare Sätze
        printImprovableSentences(sentencesListFromText);

        //score and evaluation
//        double entropy = evaluator.getEntropy(wordsListFromText);
//        System.out.println(entropy);

        /**
         * Flesch-Index
         *
         * 0-30 - Schwer
         * 30-50 - Schwierig
         * 50-60 - Anspruchsvoll
         * 60-70 - Normal
         * 70-80 - Einfach
         * 80-90 - Leicht
         * 90-100 - Sehr leicht
         */
        double readabilityScore = evaluator.getReadabilityScore(sentencesListFromText, wordsListFromText);
        System.out.println(readabilityScore);
        if (readabilityScore > 0 && readabilityScore < 30){
            System.out.println("Schwer");
        } else if (readabilityScore >= 30 && readabilityScore < 50){
            System.out.println("Schwierig");
        } else if (readabilityScore >= 50 && readabilityScore < 60) {
            System.out.println("Anspruchsvoll");
        } else if (readabilityScore >= 60 && readabilityScore < 70) {
            System.out.println("Normal");
        } else if (readabilityScore >= 70 && readabilityScore < 80) {
            System.out.println("Einfach");
        } else if (readabilityScore >= 80 && readabilityScore < 90) {
            System.out.println("Leicht");
        } else {
            System.out.println("Sehr leicht");
        }
    }

    private static void printImprovableSentences(ArrayList<Sentence> sentencesListFromText) {
        ArrayList<Sentence> improvableSentences = new ArrayList<>();
        for (Sentence sentence: sentencesListFromText)
            if (sentence.getNumWordsPerSentence() > 15){
                improvableSentences.add(sentence);
        }
        System.out.println(improvableSentences);
    }

    private static void printImprovableWords(Evaluator evaluator) {
        System.out.println(evaluator.getCompoundWordsListFromText());
        System.out.println(evaluator.getAcronymsMeaning());
        System.out.println(evaluator.getForeignWordsListFromText());
    }

    private static String readFile1() throws IOException {
        BufferedReader brInput;
        brInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the absolute path to the text to be evaluated:");
        String filePath = brInput.readLine();
        // TODO: check if provided path is actually a path
        String textAsString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        return textAsString;
    }

    private static void readFile() throws IOException {
        BufferedReader brInput;

        brInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the absolute path to the text to be evaluated:");
        String filePath = brInput.readLine();
        // TODO: check if provided path is actually a path
        textAsString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
    }

}