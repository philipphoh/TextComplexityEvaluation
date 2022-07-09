import lingologs.Script;

import java.util.ArrayList;
import java.util.List;

public class Text {

    private Script content;
    private ArrayList<Sentence> sentences;

    public Text(Script content) {
        this.content = content;
        //this.sentences = splitTextToSentences(content);
    }

    private ArrayList<Sentence> splitTextToSentences(Script content) {
        List<Script> sentencesList = content.split("[!?.:]+"); //Annahme: ohne Abkürzungen
        for (Script sentence : sentencesList){
            sentences.add(new Sentence(sentence));
        }
        return sentences;
    }

    public Script getContent() {
        return content;
    }
}
