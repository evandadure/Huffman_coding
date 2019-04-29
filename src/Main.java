public class Main {

    public static void main(String[] args) throws Exception {
        // -------- CODE TO CREATE A FREQUENCIES FILE --------
//        Alphabet a = new Alphabet("src/data/test2.txt");
//        a.build_freq_file("src/data/freq.dat");


        // -------- CODE TO COMPRESS A TEXT --------
        Huffman h1 = new Huffman();
        h1.compress("src/data/alice29.txt");

        // displays all found characters with their frequency and binary code
        for (Letter l:h1.getListLetters()) {
            System.out.println("character : "+l.getCharac()+", freq : "+l.getFreq()+", binary : "+l.getBinaryCode());
        }
        // -------- CODE TO DECOMPRESS A COMPRESSED TEXT --------
        Huffman h2 = new Huffman();
        h2.decompress("src/data/huffman_encoded.txt","src/data/huffman_freq.txt");


    }
}
