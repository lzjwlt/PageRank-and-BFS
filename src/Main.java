import java.io.File;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        File file1 = new File("data/0811.txt");
        File file2 = new File("data/roadNet.txt");
        File file3 = new File("data/LiveJournal1.txt");

        solve(file1);
        solve(file2);
        solve(file3);
    }

    private static void solve(File file){
        System.out.println("文件名：" + file.getName());
        //读取数据
        DataStore dataStore = new DataStore();
        DataReader dataReader = new DataReader(file);
        dataReader.readAllLine(dataStore);
        System.out.println();

        //BFS
        BFS bfs = new BFS(dataStore);
        BFS.printLevel(bfs.doBFS());
        System.out.println();

        //PageRank
        PageRank pageRank = new PageRank(dataStore);
        PageRank.rank(pageRank.PR(),10);
        System.out.println();
        System.out.println();
        System.out.println("----------------------------------");


    }
}
