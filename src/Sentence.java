import lingologs.Script;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private Script content;
    private Script normalizedContent;
    private ArrayList<Word> words;
    private int numCommas;

    public Sentence(Script content) {
        Processor sentenceProcessor = new Processor();
        this.content = content;
        //this.normalizedContent = sentenceProcessor.normalize()
        //this.words = splitSentenceIntoWords(content);
        numCommas = 0;
        words = new ArrayList<>();
    }

    public ArrayList<Word> splitSentenceIntoWords() {
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

    public Script getContent() {
        return content;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
