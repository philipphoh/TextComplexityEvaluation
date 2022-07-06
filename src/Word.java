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

    public int getLength(){
        return content.length();
    }

    public boolean isComposition() {
        return isComposition;
    }

    public boolean isAbbreviations() {
        return isAbbreviations;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public boolean isPassive() {
        return isPassive;
    }
}
