import lingologs.Script;

import java.util.ArrayList;

public class Text {

    private Script content;
    private ArrayList<Sentence> sentences;

    public Text(Script content) {
        this.content = content;
        //this.sentences = splitTextToSentences(content);
    }

    //private ArrayList<Sentence> splitTextToSentences(Script content) {
//
//    }

    public Script getContent() {
        return content;
    }
}
