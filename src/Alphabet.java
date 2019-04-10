import java.io.*;
import java.util.Arrays;
import java.util.List;

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

    private String get_file_content(String path) throws Exception
    {
        String line = null;
        String output = new String("");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path));
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

    private String charToString(Character varChar){
        String varStr=varChar.toString();
        if(varStr.contains("\n"))
            varStr = "\\n";
        if(varStr.contains(" "))
            varStr = "SPACE";
        return varStr;
    }

    private String build_freq(char[] sortedInputStr){
        Character currentChar = sortedInputStr[0];
        String freq = "";
        int nbCurrChar = 1;
        int nbDifferentChar = 1;
        // In this for loop, we look at every character in our char array, and check if either it's same char as the one
        // before it, or it's a different char. In this case, we add the previous char in our "list" of frequencies
        // (which is actually a String).
        for (int i = 1; i < sortedInputStr.length; i++) {
            if(sortedInputStr[i] == currentChar)
                nbCurrChar+=1;
            else{
                freq+="\n"+this.charToString(sortedInputStr[i-1])+" "+nbCurrChar;
                nbCurrChar=1;
                currentChar = sortedInputStr[i];
                nbDifferentChar += 1;
            }
        }
        freq+="\n"+this.charToString(sortedInputStr[sortedInputStr.length-1])+" "+nbCurrChar+"\n"+nbDifferentChar;
        System.out.println(freq);

        return "yey";
    }



    public void build() throws Exception {
        String content = this.get_file_content(this.path);
        char[] sortedContent = this.sort_string(content);
        String freq = this.build_freq(sortedContent);
        System.out.println(freq);
    }





    //A test method to print the input and output after having sorted the input
//    public void printInputOutput () throws Exception {
//        String inputText = this.get_file_content(this.path);
//        System.out.println(inputText);
//        String outputText = new String("");
//        outputText = this.sort_string(inputText);
//        System.out.println("----------------------");
//        System.out.println(outputText);
//    }




    //get_fichier -> return string
    //mise dans un tableau trié par ordre ascii, avec fréquence associée au caractère
    //modification du fichier freq.dat
    //mise en private des méthodes sauf la principale en public
}
