public class Edge {
    private int outNodeId;
    private int inNodeId;

    public Edge(int outNodeId, int inNodeId) {
        this.outNodeId = outNodeId;
        this.inNodeId = inNodeId;
    }

    public int getOutNodeId() {
        return outNodeId;
    }

    public int getInNodeId() {
        return inNodeId;
    }
}
