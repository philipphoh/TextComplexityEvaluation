import lingolava.Mathx;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator {
    private Text text;
    private Sentence sentence ;
    private ArrayList<Word> wordsList;

    public Evaluator(Text text) {
        this.text = text;
        wordsList = getWordsListFromText(text);
    }


//    private boolean checkAbbreviations(ArrayList<Word> wordsList, Word word){
//
//    }

//    private boolean checkComposita(ArrayList<Word> wordsList, Word word){
//
//    }

    public boolean checkForeign(Word word) throws IOException {

        File foreignWordsFile = new File("./src/Data/foreignwords.txt");
        FileReader fr = new FileReader(foreignWordsFile);

        ArrayList<String> foreignWordsList = new ArrayList<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null){
            foreignWordsList.add(line);
            for (String foreignWord : foreignWordsList){
                if (word.toString().equals(foreignWord.toLowerCase())){
                    word.setForeign(true);
                    return word.isForeign();
                }
            }
        }
        return false;
    }

    public double getEntropy (){
        return Mathx.info(getWordFrequency());
    }

    private ArrayList<Integer> getWordFrequency (){
        HashMap<Word, Integer> wordCount = new HashMap<>();

        for (Word word : wordsList){
            if (wordCount.containsKey(word)){
                wordCount.put(word, wordCount.get(word) + 1);
            }
            else {
                wordCount.put(word, 1);
            }
        }

        ArrayList<Integer> wordFrequencyList = new ArrayList<>(wordCount.values());
        return wordFrequencyList;
    }

    private ArrayList<Word> getWordsListFromText(Text text) {
        ArrayList<Sentence> sentencesListFromText = text.splitTextToSentences();
        ArrayList<Word> wordsListFromText = new ArrayList<>();

        for (Sentence sentence:sentencesListFromText){
            ArrayList<Word> wordsListFromSentence = sentence.splitSentenceIntoWords();
            for (Word w : wordsListFromSentence){
                wordsListFromText.add(w);
            }
        }
        return wordsListFromText;
    }

}
