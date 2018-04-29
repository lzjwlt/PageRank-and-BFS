import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class DataReader {
    private File file = new File("data/test.txt");

    public DataReader() {
    }

    public DataReader(String file) {
        this.file = new File(file);
    }

    public DataReader(File file) {
        this.file = file;
    }

    // @return 返回执行时间毫秒数
    public long readAllLine(DataStore dataStore){
//        Scanner scanner;
        BufferedReader br;
        Date begin = new Date();
        Date now;

//        ReadFileM readFileM = new ReadFileM(dataStore);
//        new Thread(readFileM).start();

        try {
//            scanner = new Scanner(new FileInputStream(file));
            br = new BufferedReader(new FileReader(file),16384);
            int m = 0;
            String string;
            while((string = br.readLine()) != null){
//                String string = scanner.nextLine();
                if(string.charAt(0) == '#'){
                    continue;
                }
                m++;
                if(m%1000000 == 0) {
                    now = new Date();
                    System.out.println("已读完" + m + "行,  用时：" + (now.getTime() - begin.getTime()) / 1000.0 + "秒");
//                    System.out.println("缓存队列长度："+ readFileM.getQueueSize());
                }
                String[] strings = string.split("\t");
                int num1 = Integer.parseInt(strings[0]);
                int num2 = Integer.parseInt(strings[1]);
                dataStore.addEdge(num1,num2);
//                readFileM.add(num1,num2);
            }
            System.out.println("读取完成，共读取"+ m + "行， 用时："+ (new Date().getTime() - begin.getTime()) / 1000.0 + "秒");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        now = new Date();
        return now.getTime()-begin.getTime();
    }



}
