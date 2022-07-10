import lingologs.Script;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private Script content;
    private ArrayList<Word> wordsListFromSentence;
    private int numCommas;

    public Sentence(Script content) {
        this.content = content;
        numCommas = 0;
        wordsListFromSentence = splitSentenceIntoWords();
    }

    public ArrayList<Word> splitSentenceIntoWords() {
        ArrayList<Word> words = new ArrayList<>();
        List<Script> wordsList = content.split("[^A-ZÜÖÄßa-züöä]+"); //Annahme: ohne Abkürzungen
        for (Script word : wordsList){
            words.add(new Word(word));
        }
        return words;
    }

    public int getNumCommas(){
        numCommas = content.count(",");
        return numCommas;
    }

    public int getNumWordsPerSentence(){
        return wordsListFromSentence.size();
    }

    public ArrayList<Word> getWordsListFromSentence() {
        return wordsListFromSentence;
    }

    public Script getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
