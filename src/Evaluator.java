import lingologs.Script;

import java.io.*;
import java.util.ArrayList;

public class Evaluator {
    private Word word;

    public Evaluator(Word word) {
        this.word = word;
    }

    private boolean checkForeign(ArrayList<Word> wordsList, Word word) throws IOException {

        File foreignWordsFile = new File("./src/Data/Fremdwörter.txt");
        FileReader fr = new FileReader(foreignWordsFile);

        ArrayList<String> foreignWordsList = new ArrayList<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null){
            foreignWordsList.add(line);
            for (Word w : wordsList){
                for (String foreignWord : foreignWordsList){
                    if (w.toString().equals(foreignWord.toLowerCase())){
                        return word.setForeign(true);
                    }
                }
            }
        }
        return word.setForeign(false);
    }

}
