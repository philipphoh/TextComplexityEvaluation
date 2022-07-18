import lingologs.Script;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {

    private Script content;
    private boolean isCompound;
    private boolean isAcronym;
    private boolean isForeign;

    public Word(Script content) {
        this.content = content;
        this.isCompound = false;
        this.isAcronym = false;
        this.isForeign = false;
    }

    public int countSyllable(){
        int counter = 0;

        ArrayList<String> arr = new ArrayList<>();
        Pattern tokenSplitter = Pattern.compile("[aeiouyäöü]+[^$e]");
        Matcher matcher = tokenSplitter.matcher(content);

        while (matcher.find()){
            arr.add(matcher.group());
        }

        counter += arr.size();
        return counter;
    }

    public int getLength(){
        return content.length();
    }

    public Script getContent() {
        return content;
    }

    public void setContent(Script content) {
        this.content = content;
    }

    public boolean isCompound() {
        return isCompound;
    }

    public void setCompound(boolean compound) {
        isCompound = compound;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    public boolean isAcronym() {
        return isAcronym;
    }

    public void setAcronym(boolean acronym) {
        isAcronym = acronym;
    }

    @Override
    public String toString() {
        return content.toString();
    }

}
