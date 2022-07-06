import lingologs.Script;

import java.util.ArrayList;

public class Sentence {
    private Script content;
    private Script normalizedContent;
    private ArrayList<Word> words;

    public Sentence(Script content) {
        Processor sentenceProcessor = new Processor();
        this.content = content;
        //this.normalizedContent = sentenceProcessor.normalize()
        //this.words = splitSentenceIntoWords(content);
    }

    //private ArrayList<Word> splitSentenceIntoWords(Script content) {
    //    //split sentence into words by remo
    //}
}
