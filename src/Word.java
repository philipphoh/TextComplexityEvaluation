import lingologs.Script;

public class Word {

    private Script content;
    private boolean isComposition;
    private boolean isAbbreviations;
    private boolean isForeign;
    private boolean isPassive;

    public Word(Script content) {
        this.content = content;
        this.isComposition = false;
        this.isAbbreviations = false;
        this.isForeign = false;
        this.isPassive = false;
    }

    public Script getContent() {
        return content;
    }

    public void setContent(Script content) {
        this.content = content;
    }

    public boolean isComposition() {
        return isComposition;
    }

    public void setComposition(boolean composition) {
        isComposition = composition;
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

    public boolean setForeign(boolean foreign) {
        return isForeign = foreign;
    }

    public boolean isPassive() {
        return isPassive;
    }

    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
