import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class Huffman {
    private ArrayList<Letter> listLetters = new ArrayList<Letter>();
    private ArrayList<Node> listNodes = new ArrayList<Node>();

    public ArrayList<Letter> getListLetters() {
        return listLetters;
    }

    public ArrayList<Node> getListNodes() {
        return listNodes;
    }

    public Letter getLetterByChar(char c){
        for (Letter l:this.listLetters) {
            if(l.getCharac() == c)
                return l;
        }
        return null;
    }

    public Letter getLetterZeros(){
        // this method returns the only letter with a binary code composed only by zeros.
        for (Letter l:this.listLetters) {
            if(!l.getBinaryCode().contains("1"))
                return l;
        }
        return null;
    }

    private void addLetterToList(char c, int freq){
        this.listLetters.add(new Letter(c,freq));
    }

    private Letter get_rarest_letter(ArrayList<Letter> list){
        // gets the first letter with the lowest frequency in a list of letters
        int min = Integer.MAX_VALUE;
        Letter rarestLetter = new Letter();
        for(Letter l:list){
            if(l.getFreq()<min){
                rarestLetter = l;
                min = l.getFreq();
            }
        }
        return rarestLetter;
    }

    private void sort_letter_list(){
        //sorts the letter list by frequencies and by ASCII number (it's normally already sorted by ASCII number)
        ArrayList<Letter> sortedList = new ArrayList<Letter>();
        int nbLetters = this.listLetters.size();
        for (int i = 0; i < nbLetters; i++) {
            Letter rarestLetter = this.get_rarest_letter(this.listLetters);
            sortedList.add(rarestLetter);
            this.listLetters.remove(rarestLetter);
        }
        this.listLetters = sortedList;
    }

    public void get_letters_freqs(String filepath) throws Exception {
        String freq_string = Alphabet.get_file_content(filepath);
        char[] freq_arr = freq_string.toCharArray();
        for (int i = 0; i < freq_arr.length ; i++) {
            if(freq_arr[i] == ' ' && freq_arr[i+1] != ' '){
                char current_char = freq_arr[i-1];
                int current_freq = Integer.parseInt(freq_string.substring(i+1).split("(?=\\D)")[0]);
                this.addLetterToList(current_char,current_freq);
            }
        }
        this.sort_letter_list();
    }

    public ArrayList<Node> get_lightest_nodes(){
        //this method returns the 2 lightest nodes of our listNodes, from left to right
        ArrayList<Node> lightestNodes = new ArrayList<Node>();
        Node min1 = new Node(null,Integer.MAX_VALUE,null,null);
        Node min2 = new Node(null,Integer.MAX_VALUE,null,null);

        for(int i = 0; i < this.listNodes.size(); i++) {
            if(this.listNodes.get(i).getWeight() < min1.getWeight()) {
                min2 = min1;
                min1 = this.listNodes.get(i);
            } else if(this.listNodes.get(i).getWeight() < min2.getWeight()) {
                min2 = this.listNodes.get(i);
            }
        }
        lightestNodes.add(min1);
        lightestNodes.add(min2);
        return lightestNodes;
    }

    public void build_nodes_tree() {
        // adding all the letters from the listLetters as nodes to the listNodes
        for (Letter l : this.listLetters) {
            this.listNodes.add(new Node(l.getCharac(), l.getFreq(), null, null));
        }
        // some nodes are created with the algorithm of Huffman, while some others are deleted. When there is only one
        // node left, the loop stops.
        while (this.listNodes.size() > 1) {
            ArrayList<Node> lightestNodes = this.get_lightest_nodes();
            Node tinyNode1 = lightestNodes.get(0);
            Node tinyNode2 = lightestNodes.get(1);
            Node newNode = new Node(null, tinyNode1.getWeight() + tinyNode2.getWeight(), tinyNode1, tinyNode2);
            this.listNodes.add(0, newNode);
            this.listNodes.remove(tinyNode1);
            this.listNodes.remove(tinyNode2);
        }
    }

    public void set_binary_codes(Node currentNode, String code){
        // goes from fathers to childs to associate a binary string to each character.
        if(currentNode.getLetter() != null){
            Letter let = this.getLetterByChar(currentNode.getLetter());
            let.setBinaryCode(code);
        }else{
            set_binary_codes(currentNode.getlChild(),code+"0");
            set_binary_codes(currentNode.getrChild(),code+"1");
        }
    }

    public String get_binary_code(char c){
        Letter l=this.getLetterByChar(c);
        return l.getBinaryCode();
    }

    public Letter get_first_letter_from_binary_string(String binaryString){
        //this methods finds the only letter whose binary code is matching the first bits of the binary string
        Letter firstLetter = new Letter();
        for(Letter l:this.listLetters){
            String char_binary = l.getBinaryCode();
            try {
                if (binaryString.substring(0, char_binary.length()).equals(char_binary))
                    firstLetter = l;
            } catch(Exception e){
                System.out.print("");
            }
        }
        return firstLetter;
    }


    public String get_decoded_string_binary(String binaryString){
        // decode a coded (compressed) string and returns the original string
        int nbLetterZeros = 0;
        String decoded_string = "";
        Letter letterZeros = this.getLetterZeros();
        while(binaryString.length() > 0){
            // when there is only zeros left in the binary string, we check if all the occurrences of the letter with
            // its code only composed by zeros have been found. If it's the case, the loop is ended
            if(letterZeros.getFreq() == nbLetterZeros && !binaryString.contains("1"))
                binaryString = "";
            // if there is still some letters to decode, we find the first letter to decode, add it to the decoded_string,
            // and remove its binary code from the binaryString
            else {
                Letter firstLetter = this.get_first_letter_from_binary_string(binaryString);
                if(firstLetter == letterZeros)
                    nbLetterZeros+=1;
                decoded_string+=firstLetter.getCharac();
                // we remove decoded parts of the binaryString until it's fully decoded.
                binaryString = binaryString.substring(firstLetter.getBinaryCode().length());
            }
        }
        return decoded_string;
    }

    public String get_coded_binary_string(String stringToCode){
        // creates a string from the binary code of each character of the string
        String codedString = "";
        char stringArray[] = stringToCode.toCharArray();
        for(char c:stringArray){
            try {
                codedString += this.get_binary_code(c).toString();
            }
            catch(Exception e){
                System.out.print("");
            }

        }
        // if the length of the binary string is not a multiple of 8, we add some "0" at the end til the lenght is equal
        // to a multiple of 8. By doing this, we can then group these bits to make bytes, and replace these bytes by
        // "normal" characters.
        if(codedString.length()%8 != 0) {
            int numberOfZeros = (codedString.length() / 8 + 1) * 8 - codedString.length();
            for (int i = 0; i < numberOfZeros; i++)
                codedString+="0";
        }
        return codedString;
    }

    public String binary_to_string(String binaryStr){
        ArrayList<String> bytesArray = new ArrayList<String>();
        String coded_string = "";
        String currentByte = "";
        for (int i = 0; i < binaryStr.length(); i++) {
            currentByte+=binaryStr.charAt(i);
            if(currentByte.length() == 8){
                bytesArray.add(currentByte);
                currentByte = "";
            }
        }
        for(String s:bytesArray)
            // for each byte (8 bits) found in the binary file, the corresponding ASCII character is added to the coded
            // string
            coded_string+=(char) Integer.parseInt(s, 2);
        return coded_string;


//        StringBuilder sb = new StringBuilder(); // Some place to store the chars
//        Arrays.stream( // Create a Stream
//                binaryStr.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
//        ).forEach(s -> // Go through each 8-char-section...
//                sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
//        );
//        String output = sb.toString(); // Output text (t)
//        return output;
    }

    public String string_to_binary(String codedStr){
        char stringArray[] = codedStr.toCharArray();
        String binaryString = "";
        for(char c:stringArray){
            String binaryCharString = Integer.toBinaryString(c);
            int numberofZeros = 8-binaryCharString.length();
            for (int i = 0; i < numberofZeros; i++) {
                binaryCharString = "0"+binaryCharString;
            }
            binaryString+=binaryCharString;
        }
        return binaryString;
    }

    public String get_file_name(String filepath){
        String filename = new File(filepath).getName();
        filename = filename.substring(0,filename.indexOf('.'));
        return filename;
    }

    public void compress(String inputFilePath) throws Exception {
        String inputFileName = this.get_file_name(inputFilePath);
        String freqFilePath = "src/data/"+inputFileName+"_freq.dat";
        String outputFilePath = "src/data/"+inputFileName+"_compressed.txt";
        // creates the frequency file, associating to each character of the input file a frequency (and sorts these
        // characters by ASCII number)
        Alphabet a = new Alphabet(inputFilePath);
        a.build_freq_file(freqFilePath);
        // fills the listLetters list with all letters from the frequency file, with their associated frequency
        this.get_letters_freqs(freqFilePath);
        // uses the Huffman algorithm to build every node, letting only the root node in the listNodes list
        this.build_nodes_tree();
        // from this root node, go through all nodes to associate a binary code to each letter of the listLetters list
        this.set_binary_codes(this.getListNodes().get(0),"");
        // gets the input file content and creates a new string made of the binary code of each character
        String binaryString = this.get_coded_binary_string(Alphabet.get_file_content(inputFilePath));
        // translate the created binary string to a real string made of ASCII characters
        String codedString = this.binary_to_string(binaryString);
        // puts the coded string into the an output file (which has the same name as the input + "coded")
        Alphabet.writeInFile(outputFilePath,codedString);
    }

    public void decompress(String freqFilePath, String compressedFilePath) throws Exception {
        String compressedFileName = this.get_file_name(compressedFilePath);
        String decompressedFilePath = "src/data/"+compressedFileName+"_decompressed.txt";
        // fills the listLetters list with all letters from the frequency file, with their associated frequency
        this.get_letters_freqs(freqFilePath);
        // uses the Huffman algorithm to build every node, letting only the root node in the listNodes list
        this.build_nodes_tree();
        // from this root node, go through all nodes to associate a binary code to each letter of the listLetters list
        this.set_binary_codes(this.getListNodes().get(0),"");
        // creates a binary string made of the compressed file content
        String compressed_binary_string = this.string_to_binary(Alphabet.get_file_content(compressedFilePath));
        // decodes the binary string to recreate the original string
        String decoded_string = this.get_decoded_string_binary(compressed_binary_string);
        // puts the coded string into the an output file (which has the same name as the input + "coded")
        Alphabet.writeInFile(decompressedFilePath,decoded_string);

    }
}
