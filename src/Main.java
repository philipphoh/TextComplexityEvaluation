import lingologs.Script;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static String textAsString;

    public static void main(String[] args) throws IOException {

        //STEP 1: read Text from file
//        textAsString = readFile1();
//        System.out.printf(textAsString);

        Script textAsScript = new Script("hello hallo hallo hallo hallo hallo jshjd. Abstrakt hgfuz. HeeLLO B. Sc. hgd ggf. abbauprodukt DDR");
        Text text = new Text(textAsScript);

        //STEP 2: find, print and filter Abbreviations, normalize Text
        Processor processedText = new Processor(text);

        //print Abbreviations
        processedText.printAbbreviations();

        //filter Abbr & normalize Text
        Text normalizedText = processedText.normalize();

        //STEP 3, 4, 5: evaluate Text & print Results
        Evaluator evaluatedText = new Evaluator(normalizedText);

        //print improvable Words & Sentences
        evaluatedText.printImprovableWords();
        evaluatedText.printImprovableSentences();

        //print accessibility score
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
        evaluatedText.printAvgLenWord();
        evaluatedText.printEntropy();
        evaluatedText.printReadabilityScore();
    }

    private static String readFile() throws IOException {
        BufferedReader brInput;
        brInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the absolute path to the text to be evaluated:");
        String filePath = brInput.readLine();
        // TODO: check if provided path is actually a path
        String textAsString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        return textAsString;
    }
}