public class Node {
    private Character letter;
    private int weight;
    private Node lChild;
    private Node rChild;

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getlChild() {
        return lChild;
    }

    public void setlChild(Node lChild) {
        this.lChild = lChild;
    }

    public Node getrChild() {
        return rChild;
    }

    public void setrChild(Node rChild) {
        this.rChild = rChild;
    }

    public Node(){}

    public Node(Character letter, int weight, Node lChild, Node rChild) {
        this.letter = letter;
        this.weight = weight;
        this.lChild = lChild;
        this.rChild = rChild;
    }
}
