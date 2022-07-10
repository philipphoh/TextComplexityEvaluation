import lingologs.Script;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    private static String textAsString;

    public static void main(String[] args) throws IOException {
//        readFile();
//        System.out.printf(textAsString);

        Text text = new Text(new Script("hello hello abstrakt how are you abbauprodukt."));
        Evaluator evaluator = new Evaluator(text);
        System.out.println(evaluator.getEntropy());
        System.out.println(evaluator.countForeignWords());
        System.out.println(evaluator.getReadabilityScore());
        System.out.println(evaluator.countCompoundWords());

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