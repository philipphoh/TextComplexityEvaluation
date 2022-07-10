import lingologs.Script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Processor {
    private Script content;
    private ArrayList<Script> abbrListFromText;

    public Processor(Script content) {
        this.content = content;
        abbrListFromText = new ArrayList<>();
    }

//    public int countAcronyms() throws IOException {
//        for (Word word :  wordsListFromText){
//            if (checkAcronyms(word)){
//                acronymsListFromText.add(word);
//            }
//        }
//        return compoundWordsListFromText.size();
//    }
//
//    private boolean checkAcronyms(Word word) throws IOException {
//        File abbreviationsFile = new File("./src/Data/Acronyms.txt");
//        FileReader fr = new FileReader(abbreviationsFile);
//
//        HashMap<String, String> acronymsFromFileMap = new HashMap<>();
//
//        BufferedReader br = new BufferedReader(fr);
//        String line;
//        while ((line = br.readLine())!= null) {
//            String[] parts = line.split(";");
//            String key = parts[0];
//            String value = parts[1];
//            acronymsFromFileMap.put(key, value);
//
//            for (String acronym : acronymsFromFileMap.keySet()){
//                if (word.toString().equals(acronym.toLowerCase())){
//                    word.setAcronym(true);
//                    return word.isCompound();
//                }
//            }
//        }
//        return false;
//    }
//
//    public HashMap<Script, String> getAcronymMeaning() throws IOException {
//        File abbreviationsFile = new File("./src/Data/Abbreviations.txt");
//        FileReader fr = new FileReader(abbreviationsFile);
//
//        HashMap<String, String> abbrFromFileMap = new HashMap<>();
//        HashMap<Script, String> abbrInTextMap = new HashMap<>();
//
//        BufferedReader br = new BufferedReader(fr);
//        String line;
//        while ((line = br.readLine())!= null) {
//            String[] parts = line.split(";");
//            String key = parts[0];
//            String value = parts[1];
//            abbrFromFileMap.put(key, value);
//
//            for (Script abbr : abbrListFromText){
//                for (String abbrKey : abbrFromFileMap.keySet()){
//                    if (abbr.toString() == abbrKey.toLowerCase()){
//                        abbrInTextMap.put(abbr, abbrFromFileMap.get(abbrKey));
//                    }
//                }
//            }
//        }
//        return abbrInTextMap;
//    }

    public ArrayList<Script> getAbbrListFromText(){
        abbrListFromText = (ArrayList<Script>) content.find("([A-ZÜÖÄßa-zäöü]*\\.( |-)?){1,}))");
        return abbrListFromText;
    }

    public int countAbbrInText(){
        return abbrListFromText.size();
    }

    public HashMap<Script, String> getAbbrMeaning() throws IOException {
        File abbreviationsFile = new File("./src/Data/Abbreviations.txt");
        FileReader fr = new FileReader(abbreviationsFile);

        HashMap<String, String> abbrFromFileMap = new HashMap<>();
        HashMap<Script, String> abbrInTextMap = new HashMap<>();

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine())!= null) {
            String[] parts = line.split(";");
            String key = parts[0];
            String value = parts[1];
            abbrFromFileMap.put(key, value);

            for (Script abbr : abbrListFromText){
                for (String abbrKey : abbrFromFileMap.keySet()){
                    if (abbr.toString() == abbrKey.toLowerCase()){
                        abbrInTextMap.put(abbr, abbrFromFileMap.get(abbrKey));
                    }
                }
            }
        }
        return abbrInTextMap;
    }

    public Script normalize() {
        Script lowerText = content.toLower();
        return lowerText;
    }

    public Script removeAbbr() {
        Script textWithoutAbbr = content.replace("([A-ZÜÖÄßa-zäöü]*\\.( |-)?){1,}))", "");
        return textWithoutAbbr;
    }

}
