import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Alphabet {

    private String path;
    private List<String> linesList;
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Alphabet(String path) throws Exception {
        this.path = path;
        this.linesList = this.get_file_content();
    }

    private List<String> get_file_content() throws Exception
    {
        String line = null;
        List<String> records = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path));
        while ((line = bufferedReader.readLine()) != null)
            records.add(line);
        bufferedReader.close();
        return records;
    }

    public void printAllAscii () throws UnsupportedEncodingException {
        System.out.println(this.linesList);

        for (String var:this.linesList){
            for (Character letter:var.toCharArray()) {
                System.out.println(letter+"("+(int)letter+")("+(letter.toString().getBytes("ISO-8859-1"))+")");
            }
        }


    }
    //get_fichier -> return string
    //mise dans un tableau trié par ordre ascii, avec fréquence associée au caractère
    //modification du fichier freq.dat
    //mise en private des méthodes sauf la principale en public
}
