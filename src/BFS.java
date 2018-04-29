import java.util.BitSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private Node[] nodes;

    public BFS(Node[] nodes) {
        this.nodes = nodes;
    }

    public BFS(DataStore dataStore) {
        this.nodes = dataStore.getNodes();
    }

    public static void printLevel(int [] levels){
        int maxLevel = 0;
        int numOfNodes = 0;
        for(int i : levels){
            maxLevel = maxLevel > i ? maxLevel : i;
        }
        int[] arr = new int[maxLevel + 1];
        for(int i : levels){
            if(i < 0)continue;
            arr[i] ++ ;
        }
        for(int i=0; i<arr.length; i++){
            if(arr[i] == 0){
                continue;
            }
            numOfNodes += arr[i];
            System.out.printf("第 %d 层的节点个数是：%d\n", i, arr[i]);
        }
    }

    public int[] doBFS(){
        return doBFS(0);
    }

    // @return 返回层次数组
    public int[] doBFS(int id){
        Queue<Node> queue = new LinkedList<>();
        BitSet visited = new BitSet();
        System.out.println("BFS开始，访问根节点："+id);
        int visitedNodes = 0;
        Date begin = new Date();
        Date now;
        int[] level = new int[nodes.length];
        for(int i=0;i<level.length;i++){
            level[i] = -1;
        }
        level[id] = 0;
        queue.offer(nodes[id]);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            int nid = node.getId();
            if(visited.get(nid) == false) {
                visited.set(nid);
                visitedNodes ++;
                visit(nid);
                if(visitedNodes % 1000000 == 0){
                    now = new Date();
                    System.out.println((now.getTime()-begin.getTime())/1000.0 +"秒，访问了"+visitedNodes+"个节点。" );
                }
                visit(nid);
                for (Edge edge : node.getOutEdges()) {
                    int inNodeID = edge.getInNodeId();
                    if(level[inNodeID] == -1) {
                        level[inNodeID] = level[nid] + 1;
                        queue.offer(nodes[inNodeID]);
                    }
                }
            }
        }
        Date end = new Date();
        System.out.println("BFS结束，共访问了"+ visitedNodes +"个联通节点，用时："+(end.getTime()-begin.getTime())/1000.0+"秒");
        return level;
    }

    private void visit(int id) {
//        System.out.println("visit "+id);
    }

    public static void main(String[] args) {
        DataStore dataStore = new DataStore();
        DataReader dataReader = new DataReader("data/roadNet.txt");
        dataReader.readAllLine(dataStore);
        BFS bfs = new BFS(dataStore);
        BFS.printLevel(bfs.doBFS());
    }
}
