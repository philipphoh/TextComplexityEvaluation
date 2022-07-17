import lingolava.Mathx;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Evaluator {
    private Text text;
    private ArrayList<Word> foreignWordsListFromText;
    private ArrayList<Word> compoundWordsListFromText;
    private HashMap<String, String> acronymsInTextMap;


    public Evaluator(Text text) {
        this.text = text;
        foreignWordsListFromText = new ArrayList<>();
        compoundWordsListFromText = new ArrayList<>();
        acronymsInTextMap = new HashMap<>();
    }

    public int countAcronyms(ArrayList<Word> wordsList) throws IOException {
        for (Word word: wordsList){
            if (checkAcronyms(word))
                return acronymsInTextMap.size();
        }
        return 0;
    }

    public int countCompoundWords(ArrayList<Word> wordsList) throws IOException {
        for (Word word :  wordsList){
            if (checkCompound(word)){
                compoundWordsListFromText.add(word);
            }
        }
        return compoundWordsListFromText.size();
    }

    public int countForeignWords(ArrayList<Word> wordsList) throws IOException {
        for (Word word :  wordsList){
            if (checkForeign(word)){
                foreignWordsListFromText.add(word);
            }
        }
        return foreignWordsListFromText.size();
    }

    public boolean checkAcronyms(Word word) throws IOException {
        File abbreviationsFile = new File("./src/Data/Acronyms.txt");
        FileReader fr = new FileReader(abbreviationsFile);

        HashMap<String, String> acronymsFromFileMap = new HashMap<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine())!= null) {
            String[] parts = line.split(";");
            String key = parts[0];
            String value = parts[1];
            acronymsFromFileMap.put(key, value);

            for (String acronym : acronymsFromFileMap.keySet()){
                if (word.toString().equals(acronym.toLowerCase())){
                    acronymsInTextMap.put(acronym, acronymsFromFileMap.get(acronym));
                    word.setAcronym(true);
                    return word.isAcronym();
                }
            }
        }
        return false;
    }

    private boolean checkCompound (Word word) throws IOException {
        File compoundWordsFile = new File("./src/Data/composita.txt");
        FileReader fr = new FileReader(compoundWordsFile);

        ArrayList<String> compoundWordsList = new ArrayList<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine())!= null) {
            compoundWordsList.add(line);
            for (String compoundWord : compoundWordsList){
                if (word.toString().equals(compoundWord.toLowerCase())){
                    word.setCompound(true);
                    return word.isCompound();
                }
            }
        }
        return false;
    }

    private boolean checkForeign(Word word) throws IOException {

        File foreignWordsFile = new File("./src/Data/ForeignWords.txt");
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

    public ArrayList<Word> getCompoundWordsListFromText() {
        return compoundWordsListFromText;
    }

    public ArrayList<Word> getForeignWordsListFromText() {
        return foreignWordsListFromText;
    }

    public HashMap<String, String> getAcronymsMeaning() {
        return acronymsInTextMap;
    }

    public double getEntropy (ArrayList<Word> wordsList) {
        return Mathx.info(getWordProbability(wordsList));
    }

//  Voraussetzung: kein Zeichen am Ende :<
    private List<Double> getWordProbability (ArrayList<Word> wordsList){
        HashMap<String, Double> wordCountMap = new HashMap<>();
        List<Double> wordProbabilityList = new ArrayList<>();

        for (Word word : wordsList){
            double wordCount = text.getContent().count(word.getContent());
            if (!wordCountMap.containsKey(word.toString())){
                wordCountMap.put(word.toString(), wordCount);
            }
        }

        for (double val : wordCountMap.values()){
            double freqOfVal = val/wordCountMap.size();
            wordProbabilityList.add(freqOfVal);
        }
        return wordProbabilityList;
    }

    public ArrayList<Word> getWordsListFromText(ArrayList<Sentence> sentencesList) {
        ArrayList<Word> wordsListFromText = new ArrayList<>();

        for (Sentence sentence: sentencesList){
            ArrayList<Word> wordsListFromSentence = sentence.splitSentenceIntoWords();
            for (Word w : wordsListFromSentence){
                 wordsListFromText.add(w);
            }
        }
        return wordsListFromText;
    }

    public ArrayList<Sentence> getSentencesListFromText(){
        return text.splitTextToSentences();
    }

    public double getReadabilityScore(ArrayList<Sentence> sentencesList, ArrayList<Word> wordsList){
        double ASL = calAverageSentenceLength(sentencesList);
        double ASW = calAverageNumberOfSyllablesPerWord(wordsList);

        //Flesch-Formel
        double readabilityScore = 180 - ASL - 58.5 * ASW;
        return readabilityScore;
    }

    private double calAverageSentenceLength(ArrayList<Sentence> sentencesList){
        List<Integer> lengthSentenceList = new ArrayList<>();
        for (Sentence sentence : sentencesList){
            lengthSentenceList.add(sentence.getNumWordsPerSentence());
        }

        double ASL = Mathx.mean(lengthSentenceList);
        return ASL;
    }

    private double calAverageNumberOfSyllablesPerWord(ArrayList<Word> wordsList){
        List<Integer> numSyllablesWordList = new ArrayList<>();

        for (Word word: wordsList){
            numSyllablesWordList.add(word.countSyllable());
        }

        double ASW = Mathx.mean(numSyllablesWordList);
        return ASW;
    }

    private double calAverageWordLength(ArrayList<Word> wordsList){
        List<Integer> lengthWordList = new ArrayList<>();

        for (Word word: wordsList){
            lengthWordList.add(word.getLength());
        }

        double avgLen = Mathx.mean(lengthWordList);
        return avgLen;
    }

}
