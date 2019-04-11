import java.io.*;
import java.util.Arrays;

public class Alphabet {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Alphabet(String path){
        this.path = path;
    }

    public static String get_file_content(String path) throws Exception
    {
        String line = null;
        String output = new String("");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        // We add each line of the file to our new String, adding \n (new line) after each line
        while ((line = bufferedReader.readLine()) != null)
//            System.out.println(line);
            output+=line+"\n";
        bufferedReader.close();
        // Finally we remove the last \n that we added (which counts as 1 character)
        return output.substring(0,output.length()-1);
    }

    private char[] sort_string(String inputStr){
        // We first convert our inputStr to a character array
        char tempArray[] = inputStr.toCharArray();
        // The Array.sort method is sorting by ASCII code, which is perfect for our program
        Arrays.sort(tempArray);
        return tempArray;
    }

    private String build_frequencies(char[] sortedInputStr) {
        Character currentChar = sortedInputStr[0];
        String freq = "";
        int nbCurrChar = 1;
        int nbDifferentChar = 1;
        // In this for loop, we look at every character in our char array, and check if either it's same char as the one
        // before it, or it's a different char. In this case, we add the previous char in our "list" of frequencies
        // (which is actually a String).
        for (int i = 1; i < sortedInputStr.length; i++) {
            if (sortedInputStr[i] == currentChar)
                nbCurrChar += 1;
            else {
                freq += "\n" + sortedInputStr[i - 1] + " " + nbCurrChar;
                nbCurrChar = 1;
                currentChar = sortedInputStr[i];
                nbDifferentChar += 1;
            }
        }
        freq += "\n" + sortedInputStr[sortedInputStr.length - 1] + " " + nbCurrChar;
        freq = nbDifferentChar + freq;
//        System.out.println(freq);

        return freq;
    }

    public static void writeInFile(String filepath, String stringToWrite) throws IOException {
        FileWriter fileWriter = new FileWriter(filepath);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(stringToWrite);
        printWriter.close();
    }

    public void build_freq_file(String filepath) throws Exception {
        // Sets one character per line in a String
        String content = this.get_file_content(this.path);
        // Sorts these character by ASCII number
        char[] sortedContent = this.sort_string(content);
        // Regroups each character and associates the frequency of each of them after a space
        String freq = this.build_frequencies(sortedContent);
        // Puts the freq String created in the freq file
        this.writeInFile(filepath,freq);
    }



}
