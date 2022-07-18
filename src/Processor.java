import lingologs.Script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
    private Text text;
    private ArrayList<String> abbrListFromText;

    public Processor(Text text) {
        this.text = text;
        abbrListFromText = getAbbrListFromText();
    }

    public void printAbbreviations() throws IOException {
        System.out.println("\nDer Text enthält " + countAbbrInText() + " Abkürzungen.");
        System.out.println("Sie sollten versuchen, folgende Abkürzungen auszuschreiben: " + getAbbrMeaning());

//        System.out.println(getAbbrListFromText()); //99% correct =))
    }

    public Text normalize(){
        Script textWithoutAbbr = text.getContent().replace("(([A-ZÜÖÄßa-zäöü]*\\.(\\s|-)?){1,}|([A-ZÜÖÄßa-zäöü]*))([A-ZÜÖÄßa-zäöü]*\\.)", "");
        Script textWithoutAbbrAndRedundantSpaces = textWithoutAbbr.replace(" {2,}", " ");

        return new Text(textWithoutAbbrAndRedundantSpaces.toLower());
    }

    private int countAbbrInText() throws IOException {
        return getAbbrMeaning().size();
    }

    private HashMap<String, String> getAbbrMeaning() throws IOException {
        File abbreviationsFile = new File("./src/Data/Abbreviations.txt");
        FileReader fr = new FileReader(abbreviationsFile);

        HashMap<String, String> abbrFromFileMap = new HashMap<>();
        HashMap<String, String> abbrInTextMap = new HashMap<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        String[] parts;

        while ((line = br.readLine())!= null) {
            parts = line.split(";");

            String key = parts[0];
            String value = parts[1];
            abbrFromFileMap.put(key, value);

            for (String abbr : abbrListFromText){
                for (String abbrKey : abbrFromFileMap.keySet()){
                    if (abbr.toLowerCase().equals(abbrKey.toLowerCase())){
                        abbrInTextMap.put(abbr, abbrFromFileMap.get(abbrKey));
                    }
                }
            }
        }
        return abbrInTextMap;
    }

    private ArrayList<String> getAbbrListFromText(){
        ArrayList<String> arr = new ArrayList<>();
        Pattern tokenSplitter = Pattern.compile("(([A-ZÜÖÄßa-zäöü]*\\.(\\s|-)?){1,}|([A-ZÜÖÄßa-zäöü]*))([A-ZÜÖÄßa-zäöü]*\\.)");
        Matcher matcher = tokenSplitter.matcher(text.getContent().toString());

        while (matcher.find()){
            arr.add(matcher.group());
        }
        return arr;
    }

}
