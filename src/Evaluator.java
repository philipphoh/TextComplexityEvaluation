import lingolava.Mathx;
import lingologs.Script;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Evaluator {
    private Text text;
    private ArrayList<Sentence> sentencesListFromText;
    private ArrayList<Word> wordsListFromText;
    private ArrayList<Word> foreignWordsListFromText;

    public Evaluator(Text text) {
        this.text = text;
        sentencesListFromText = text.splitTextToSentences();
        wordsListFromText = getWordsListFromText();
        foreignWordsListFromText = new ArrayList<>();
    }


//    private boolean checkAbbreviations(ArrayList<Word> wordsList, Word word){
//
//    }

//    private boolean checkComposita(ArrayList<Word> wordsList, Word word){
//
//    }

    private boolean checkForeign(Word word) throws IOException {

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
                    foreignWordsListFromText.add(word);
                    return word.isForeign();
                }
            }
        }
        return false;
    }

    public int countForeignWords(){
        return foreignWordsListFromText.size();
    }


    //funktioniert noch nicht
    public double getEntropy (){
        return Mathx.info(getWordFrequency());
    }

    private ArrayList<Integer> getWordFrequency (){
        HashMap<Word, Integer> wordCount = new HashMap<>();

        for (Word word : wordsListFromText){
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

    private ArrayList<Word> getWordsListFromText() {
        ArrayList<Word> wordsListFromText = new ArrayList<>();

        for (Sentence sentence: sentencesListFromText){
            ArrayList<Word> wordsListFromSentence = sentence.splitSentenceIntoWords();
            for (Word w : wordsListFromSentence){
                wordsListFromText.add(w);
            }
        }
        return wordsListFromText;
    }

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
    public double getReadabilityScore(){
        double ASL = calAverageSentenceLength();
        double ASW = calAverageNumberOfSyllablesPerWord();

        //Flesch-Formel
        double readabilityScore = 180 - ASL - 58.5 * ASW;
        return readabilityScore;
    }

    private double calAverageSentenceLength(){
        List<Integer> lengthSentenceList = new ArrayList<>();
        for (Sentence sentence : sentencesListFromText){
            lengthSentenceList.add(sentence.getNumWordsPerSentence());
        }

        double ASL = Mathx.mean(lengthSentenceList);
        return ASL;
    }

    private double calAverageNumberOfSyllablesPerWord(){
        List<Integer> lengthWordList = new ArrayList<>();

        for (Word word: wordsListFromText){
            lengthWordList.add(word.countSyllable());
        }

        double ASW = Mathx.mean(lengthWordList);
        return ASW;
    }


}
