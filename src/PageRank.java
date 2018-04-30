import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class PageRank {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();
        DataReader dataReader = new DataReader("data/LiveJournal1.txt");
//        DataReader dataReader = new DataReader("data/roadNet.txt");
//        DataReader dataReader = new DataReader("data/test.txt");
        dataReader.readAllLine(dataStore);
        System.out.println("Finished Load");
        PageRank pageRank = new PageRank(dataStore,5000000);
        rank(pageRank.PR());
    }
    private DataStore dataStore;
    private int nodeSize = 5000000;
    private static final double DAMP_FACTOR = 0.85;
    private static final double COST = 1;
    private static final double MAX_TIMES = 100;

    public PageRank(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public static void rank(double[]PR){
        rank(PR,10);
    }

    public static void rank(double[] PR, int size){
        int len = PR.length;
        double [][] arr = new double[size][2];
        System.out.printf("PR值排名前 %d 的节点为：\n",size);
        for(int i=0; i<size; i++){
            arr[i][0] = i;
            arr[i][1] = PR[i];
        }
        Arrays.sort(arr, (o1, o2) -> (int)(o2[1] - o1[1]));
        for(int i=size; i<len; i++){
            if(PR[i] > arr[size-1][1]){
                arr[size-1][0] = i;
                arr[size-1][1] = PR[i];
            }
            Arrays.sort(arr, (o1, o2) -> (int)(o2[1] - o1[1]));
        }
        for(int i=0; i<size; i++){
            System.out.println("Node: " + (int)arr[i][0] + " , PR: "+ arr[i][1] );
        }
    }

    public PageRank(DataStore dataStore, int nodeSize) {
        this.dataStore = dataStore;
        this.nodeSize = nodeSize;
    }

    public double[] PR(){
        double [] PrNew = new double[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            PrNew[i] = 1.0;
        }
        double [] Pr;
        System.out.println("PageRank开始");
        Date begin = new Date();
        for (int i = 0; i < MAX_TIMES; i++) {
            Pr = PrNew;
            PrNew = getNewPR(Pr);
            double cost = getCost(PrNew,Pr);
            System.out.printf("第 %d 次迭代， 用时 %.3f 秒，Cost = %f\n" ,i, (new Date().getTime() - begin.getTime())/1000.0,cost);
            if(cost < COST)
                break;
        }
        return PrNew;
    }

    private double[] getNewPR(double[] pr) {
        double [] PrNew = new double[nodeSize];
        Node[] nodes = dataStore.getNodes();
        for(int i=0;i<nodes.length;i++){
            double temp = 0;
            if(nodes[i] == null)continue;
            for(Edge inEdge : nodes[i].getInEdges()){
                temp += pr[inEdge.getOutNodeId()] / nodes[inEdge.getOutNodeId()].getOutEdges().size() ;
            }
            PrNew[i] = 1 - DAMP_FACTOR + DAMP_FACTOR * temp;
        }
        return PrNew;
    }

    private double getCost(double[] PrNew,double [] Pr){
        double temp = 0;
        for (int i = 0; i < nodeSize; i++) {
            temp += (PrNew[i] - Pr[i])*(PrNew[i] - Pr[i]);
        }
        return Math.sqrt(temp);
    }
}
