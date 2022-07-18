import lingolava.Mathx;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Evaluator {

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

    private ArrayList<Word> getCompoundWordsListFromText() {
        return compoundWordsListFromText;
    }

    private ArrayList<Word> getForeignWordsListFromText() {
        return foreignWordsListFromText;
    }

    private HashMap<String, String> getAcronymsMeaning() {
        return acronymsInTextMap;
    }

    public double getEntropy () {
        return Mathx.info(getWordProbability());
    }

//  Voraussetzung: kein Zeichen am Ende :<
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
