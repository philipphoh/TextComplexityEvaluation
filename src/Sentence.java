import lingologs.Script;
import lingologs.Texture;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private Texture<Script> content;
    private Script normalizedContent;
    private List<Word> words;
    private int numCommas;

    public Sentence(Texture<Script> content) {
        Processor sentenceProcessor = new Processor();
        this.content = content;
        //this.normalizedContent = sentenceProcessor.normalize()
        //this.words = splitSentenceIntoWords(content);
        int numCommas = 0;
    }


    //private ArrayList<Word> splitSentenceIntoWords(Script content) {
    //    //split sentence into words by remo
    //}


}
