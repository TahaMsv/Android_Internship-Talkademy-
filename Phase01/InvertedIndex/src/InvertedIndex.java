import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex {
    static Map<String, Set<String>> tokenWords = tokenWords = new HashMap<>();
    final static Set<String> stopWords = new HashSet<>(Arrays.asList("their", "too", "only", "myself", "which", "those", "i", "after",
            "few", "whom", "t", "being", "if", "theirs", "my", "against", "a", "by", "doing", "it", "how"
            , "further", "while", "above", "both", "up", "to", "ours", "had", "she", "all", "no", "when", "at",
            "any", "before", "them", "same", "and", "been", "have", "in", "will", "on", "does", "yourselves",
            "then", "ourselves", "hers", "between", "yourself", "but", "again", "of", "most", "itself", "other",
            "off", "is", "s", "am", "or", "who", "as", "from", "him", "each", "the", "themselves", "until", "below",
            "your", "his", "through", "don", "nor", "me", "were", "her", "more", "himself", "this", "down", "should",
            "our", "there", "about", "once", "during", "out", "very", "having", "with", "they", "own", "an", "be",
            "some", "for", "do", "its", "yours", "such", "into", "are", "we", "these", "has", "just", "where", "was", "here",
            "that", "because", "what", "over", "why", "so", "can", "did", "not", "now", "under", "he", "you", "herself", "than"));

    public static void main(String[] args) {
        String DIRECTORY_NAME = "SampleEnglishData\\EnglishData";
        DirectoryReader directoryReader = new DirectoryReader(DIRECTORY_NAME);
        extractWordsFromFiles(directoryReader);

        String str = "";
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Type a word to search. If you want to exit, please type \"EXIT()\":");
            str = input.nextLine();
            if (!str.equals("EXIT()")) {
                if (tokenWords.containsKey(str)) {
                    Set<String> docList = tokenWords.get(str);
                    System.out.println("This word exist in these documents:");
                    int i = 1;
                    for (String docName : docList) {
                        System.out.println(i + "- " + docName);
                        i++;
                    }
                } else {
                    System.out.println("No document found.");

                }
            }
        } while (!str.equals("EXIT()"));
    }


    private static void extractWordsFromFiles(DirectoryReader directoryReader) {
        File[] files = directoryReader.getFilesList();
        for (File file : files) {
            String fileName = file.getName();
            String fileContent = directoryReader.readFileContent(file);
            updateTokenWords(fileName, fileContent);
        }
    }

    private static void updateTokenWords(String fileName, String fileContent) {
        String[] words = fileContent.split("\\s+");
        for (String word : words) {
            if (!InvertedIndex.stopWords.contains(word)) {
                if (tokenWords.containsKey(word)) {
                    tokenWords.get(word).add(fileName);
                } else {
                    tokenWords.put(word, new HashSet<String>() {{
                        add(fileName);
                    }});
                }
            }
        }
    }

}


class DirectoryReader {
    private File directory;

    public DirectoryReader(String directoryName) {
        this.directory = new File(directoryName);
    }

    String readFileContent(File file) {
        StringBuilder fileContent = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader((reader));
            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                fileContent.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString().toLowerCase();

    }

    File[] getFilesList() {
        return directory.listFiles();
    }


}
