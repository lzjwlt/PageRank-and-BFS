import java.util.*;

public class DataStore {
    private int maxNodeLength = 5000000;
    private int maxEdgeLength = 70000000;
//    private ArrayList<Node> nodes = new ArrayList<>();
//    private ArrayList<Edge> edges = new ArrayList<>();
    private Node[] nodes;
//    private ArrayList<Edge> edges = new ArrayList<>();


    public DataStore() {
        nodes = new Node[maxNodeLength];
    }

    public DataStore(int maxNodeLength, int maxEdgeLength) {
        this.maxNodeLength = maxNodeLength;
        this.maxEdgeLength = maxEdgeLength;
        nodes = new Node[maxNodeLength];
    }

    public void addEdge(int num1, int num2){
        if(nodes[num1] == null){
            nodes[num1] = new Node(num1);
        }
        if(nodes[num2] == null){
            nodes[num2] = new Node(num2);
        }
        Edge edge = new Edge(num1,num2);
        nodes[num1].addOutEdges(edge);
        nodes[num2].addInEdges(edge);
//        edges.add(edge);
    }

    public Node[] getNodes() {
        return nodes;
    }

}
