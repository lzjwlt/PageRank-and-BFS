import java.util.Arrays;
import java.util.Date;

public class MPageRank {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();
//        DataReader dataReader = new DataReader("data/soc-Slashdot0811.txt");
        DataReader dataReader = new DataReader("data/soc-LiveJournal1.txt");
//        DataReader dataReader = new DataReader("data/roadNet.txt");
//        DataReader dataReader = new DataReader("data/test.txt");
        dataReader.readAllLine(dataStore);
        System.out.println("Finished Load");
        MPageRank mPageRank = new MPageRank(dataStore,5000000);
        int times = 6;
        for(int i=1; i<=times; i++){
            rank(mPageRank.PR(i));
        }

    }
    private DataStore dataStore;
    private int nodeSize = 5000000;
    private static final double DAMP_FACTOR = 0.85;
    private static final double COST = 1;
    private static final double MAX_TIMES = 100;

    public MPageRank(DataStore dataStore) {
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

    public MPageRank(DataStore dataStore, int nodeSize) {
        this.dataStore = dataStore;
        this.nodeSize = nodeSize;
    }

    public double[] PR(){
        return PR(1);
    }

    public double[] PR(int threadNum){
        double [] PrNew = new double[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            PrNew[i] = 1.0;
        }
        double [] Pr;
        System.out.println("PageRank开始, 线程数目为：" + threadNum);
        Date begin = new Date();
        for (int i = 0; i < MAX_TIMES; i++) {
            Pr = PrNew;
            PrNew = getNewPR(Pr,threadNum);
            double cost = getCost(PrNew,Pr);
            System.out.printf("第 %d 次迭代， 用时 %.3f 秒，Cost = %f\n" ,i, (new Date().getTime() - begin.getTime())/1000.0,cost);
            if(cost < COST)
                break;
        }
        return PrNew;
    }

    private double[] getNewPR(double[] pr){
        return getNewPR(pr,1);
    }

    private double[] getNewPR(double[] pr, int threadNum) {
        double [] PrNew = new double[nodeSize];
        Node[] nodes = dataStore.getNodes();
        class MThread implements Runnable{
            int start;
            int size;

            public MThread(int start, int size) {
                this.start = start;
                this.size = size;
            }

            @Override
            public void run() {
                for(int i=start; i<size; i++){
                    Node node = nodes[i];
                    double temp = 0;
                    if(node == null)continue;
                    for(Edge inEdge : node.getInEdges()){
                        temp += pr[inEdge.getOutNodeId()] / nodes[inEdge.getOutNodeId()].getOutEdges().size() ;
                    }
                    PrNew[i] = 1 - DAMP_FACTOR + DAMP_FACTOR * temp;
                }
            }
        }

        int size = nodes.length / 4;
        Thread [] subThread = new Thread[threadNum];
        for(int i=0;i<threadNum;i++){
            if(i == threadNum-1){
                subThread[i] = new Thread(new MThread(size*i, nodes.length-size*i));
            }
            else{
                subThread[i] = new Thread(new MThread(size*i,size));
            }
            subThread[i].start();
        }
        for(Thread thread : subThread){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
