import java.util.ArrayList;

public class Node {
    private int id;
    private ArrayList<Edge> outEdges = new ArrayList<>();
    private ArrayList<Edge> inEdges = new ArrayList<>();

    public Node(int id) {
        this.id = id;
    }

    public void addOutEdges(Edge edge){
        this.outEdges.add(edge);
    }
    public void addInEdges(Edge edge){
        this.inEdges.add(edge);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Edge> getOutEdges() {
        return outEdges;
    }

    public ArrayList<Edge> getInEdges() {
        return inEdges;
    }
}
