import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Alphabet a = new Alphabet("src/data/test.txt");
        a.build();
    }
}
