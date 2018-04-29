import java.util.LinkedList;
import java.util.Queue;

//缓存队列处理不是性能瓶颈，舍弃

@Deprecated
public class ReadFileM implements Runnable {
    private Queue<int[]> queue = new LinkedList<>();
    private DataStore dataStore;

    public ReadFileM(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void add(int n1, int n2){
        int[] temp = {n1,n2};
        queue.offer(temp);
    }
    public int getQueueSize(){
        return queue.size();
    }

    private void solve(){
        while (true){
            while(!queue.isEmpty()){
                int[] temp = queue.poll();
                dataStore.addEdge(temp[0],temp[1]);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        solve();
    }
}
