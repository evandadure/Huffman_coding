public class Main {

    public static void main(String[] args) throws Exception {
//        Alphabet a = new Alphabet("src/data/test2.txt");
//        a.build_freq_file("src/data/freq.dat");


        Huffman h = new Huffman();
        h.compress("src/data/extrait_alice.txt");
        for (Letter l:h.getListLetters()) {
            System.out.println("ma lettre : "+l.getCharac()+", ma freq : "+l.getFreq()+", binary : "+l.getBinaryCode());
        }
        h = new Huffman();
        h.decompress("src/data/extrait_alice_freq.dat","src/data/extrait_alice_compressed.txt");


    }
}
