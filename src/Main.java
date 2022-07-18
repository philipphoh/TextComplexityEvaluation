import lingologs.Script;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static String textAsString;

    public static void main(String[] args) throws IOException {

        //STEP 1: read Text from file
        textAsString = readFile();
        System.out.printf("\nDie bereitgestellte Datei enthält den folgenden Text: \n" + textAsString);

        Script textAsScript = new Script(textAsString);
        Text text = new Text(textAsScript);

        //STEP 2: find, print and filter Abbreviations, normalize Text
        Processor processedText = new Processor(text);

        //filter Abbr & normalize Text
        Text normalizedText = processedText.normalize();

        //STEP 3, 4, 5: evaluate Text & print Results
        Evaluator evaluatedText = new Evaluator(normalizedText);

        //print improvable Words & Sentences
        System.out.println("\n\n\nDie folgenden Teile des vorliegenden Textes sollten in ihrer Verständlichkeit verbessert werden:");
        evaluatedText.printImprovableWords();
        processedText.printAbbreviations();
        evaluatedText.printImprovableSentences();

        //print accessibility score
        System.out.println("\n\nDer bereitgestellte Text wird wie folgt bewertet:\n");
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
        evaluatedText.printReadabilityScore();
        evaluatedText.printEntropy();
    }

    private static String readFile() throws IOException {
        BufferedReader brInput;
        brInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Geben Sie den Pfad zu dem auszuwertenden Text ein:");
        String filePath = brInput.readLine();
        //TODO: check if provided path is actually a path
        String textAsString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        return textAsString;
    }
}