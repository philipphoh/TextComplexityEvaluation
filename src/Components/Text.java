import lingologs.Script;

import java.util.ArrayList;
import java.util.List;

public class Text {

    private Script content;
    private ArrayList<Sentence> sentences;

    public Text(Script content) {
        this.content = content;
        sentences = new ArrayList<>();
    }

    public ArrayList<Sentence> splitTextToSentences() {
        List<Script> sentencesList = content.split("[!?\\.:]+");
        for (Script sentence : sentencesList){
            sentences.add(new Sentence(sentence));
        }
        return sentences;
    }

    public Script getContent() {
        return content;
    }
}
