import lingologs.Charact;
import lingologs.Script;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {

    private Script content;
    private boolean isCompound;
    private boolean isAbbreviations;
    private boolean isForeign;

    public Word(Script content) {
        this.content = content;
        this.isCompound = false;
        this.isAbbreviations = false;
        this.isForeign = false;
    }

    public int countSyllable(){
        int counter = 0;

        ArrayList<String> arr = new ArrayList<>();
        Pattern tokenSplitter = Pattern.compile("[aeiouyüöä]+");
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

    public boolean isAbbreviations() {
        return isAbbreviations;
    }

    public void setAbbreviations(boolean abbreviations) {
        isAbbreviations = abbreviations;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    @Override
    public String toString() {
        return content.toString();
    }

}
