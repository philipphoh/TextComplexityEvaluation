import lingolava.Mathx;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Evaluator {

    private static final String FOREIGN_WORDS_FILE_PATH = "./src/Data/ForeignWords.txt";
    private static final String COMPOUND_WORDS_FILE_PATH = "./src/Data/composita.txt";

    private Text text;
    private ArrayList<Sentence> sentencesList;
    private ArrayList<Word> wordsList;

    private ArrayList<Word> foreignWordsListFromText;
    private ArrayList<Word> compoundWordsListFromText;
    private HashMap<String, String> acronymsInTextMap;


    public Evaluator(Text text) {

        this.text = text;
        this.sentencesList = getSentencesListFromText();
        this.wordsList = getWordsListFromText();

        foreignWordsListFromText = new ArrayList<>();
        compoundWordsListFromText = new ArrayList<>();
        acronymsInTextMap = new HashMap<>();
    }

    //print results
    public void printImprovableWords() throws IOException {
        System.out.println(countCompoundWords());
        System.out.println(getCompoundWordsListFromText());

        System.out.println(countAcronyms());
        System.out.println(getAcronymsMeaning());

        System.out.println(countForeignWords());
        System.out.println(getForeignWordsListFromText());
    }

    public void printImprovableSentences() {
        ArrayList<Sentence> improvableSentences = new ArrayList<>();
        for (Sentence sentence: sentencesList)
            if (sentence.getNumWordsPerSentence() > 15){
                improvableSentences.add(sentence);
            } else if (sentence.getNumCommas() > 1){
                improvableSentences.add(sentence);
            }
        System.out.println(improvableSentences);
    }

    public void printEntropy(){
        System.out.println(getEntropy());
    }

    public void printReadabilityScore(){
        double readabilityScore = getReadabilityScore();
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

    public void printAvgLenWord(){
        System.out.println(calAverageWordLength());
    }

    //count number of acronyms, composita & foreign words in text
    private int countAcronyms() throws IOException {
        for (Word word: wordsList){
            if (checkAcronyms(word))
                return acronymsInTextMap.size();
        }
        return 0;
    }

    private int countCompoundWords() throws IOException {
        for (Word word :  wordsList){
            if (checkCompound(word)){
                compoundWordsListFromText.add(word);
            }
        }
        return compoundWordsListFromText.size();
    }

    private int countForeignWords() throws IOException {
        for (Word word :  wordsList){
            if (checkForeign(word)){
                foreignWordsListFromText.add(word);
            }
        }
        return foreignWordsListFromText.size();
    }

    //check if a word is acronym | composita | foreign or not
    private boolean checkAcronyms(Word word) throws IOException {
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
        ArrayList<String> compoundWordsList = readWordsFromFile(COMPOUND_WORDS_FILE_PATH);
        for (String compoundWord : compoundWordsList){
            if (word.toString().equals(compoundWord.toLowerCase())){
                word.setCompound(true);
                return word.isCompound();
            }
        }
        return false;
    }

    private boolean checkForeign(Word word) throws IOException {
        ArrayList<String> foreignWordsList = readWordsFromFile(FOREIGN_WORDS_FILE_PATH);
        for (String foreignWord : foreignWordsList){
            if (word.toString().equals(foreignWord.toLowerCase())){
                word.setForeign(true);
                return word.isForeign();
            }
        }
        return false;
    }

    private ArrayList<String> readWordsFromFile(String path) throws IOException {

        File wordsFromFile = new File(path);
        FileReader fr = new FileReader(wordsFromFile);

        ArrayList<String> wordsListFromFile = new ArrayList<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            wordsListFromFile.add(line);
        }
            return wordsListFromFile;
    }

    //get compoundWordsList, foreignWordsList and acronymsHashMap
    private HashMap<String, String> getAcronymsMeaning() {
        return acronymsInTextMap;
    }

    private ArrayList<Word> getCompoundWordsListFromText() {
        return compoundWordsListFromText;
    }

    private ArrayList<Word> getForeignWordsListFromText() {
        return foreignWordsListFromText;
    }

    //calculate entropy
    public double getEntropy () {
        return Mathx.info(getWordProbability());
    }

    // Voraussetzung: kein Zeichen am Ende :<
    private List<Double> getWordProbability (){
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

    //calculate readability score (Flesch - Formel)
    public double getReadabilityScore(){
        double ASL = calAverageSentenceLength();
        double ASW = calAverageNumberOfSyllablesPerWord();

        //Flesch-Formel
        double readabilityScore = 180 - ASL - 58.5 * ASW;
        return readabilityScore;
    }

    private double calAverageSentenceLength(){
        List<Integer> lengthSentenceList = new ArrayList<>();
        for (Sentence sentence : sentencesList){
            lengthSentenceList.add(sentence.getNumWordsPerSentence());
        }

        double ASL = Mathx.mean(lengthSentenceList);
        return ASL;
    }

    private double calAverageNumberOfSyllablesPerWord(){
        List<Integer> numSyllablesWordList = new ArrayList<>();

        for (Word word: wordsList){
            numSyllablesWordList.add(word.countSyllable());
        }

        double ASW = Mathx.mean(numSyllablesWordList);
        return ASW;
    }

    private double calAverageWordLength(){
        List<Integer> lengthWordList = new ArrayList<>();

        for (Word word: wordsList){
            lengthWordList.add(word.getLength());
        }

        double avgLen = Mathx.mean(lengthWordList);
        return avgLen;
    }

    //split text into sentences and words
    public ArrayList<Word> getWordsListFromText() {
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

}
