public class Letter {
    private Character charac;
    private int freq;
    private String binaryCode;

    public Character getCharac() {
        return charac;
    }

    public void setCharac(Character charac) {
        this.charac = charac;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getBinaryCode() {
        return binaryCode;
    }

    public void setBinaryCode(String binaryCode) {
        this.binaryCode = binaryCode;
    }

    public Letter(){}

    public Letter(Character charac, int freq) {
        this.charac = charac;
        this.freq = freq;
    }
}
