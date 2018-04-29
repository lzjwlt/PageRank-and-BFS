import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        int[][] a = {{1,2},{2,6},{3,5},{4,9}};
        Arrays.sort(a,((o1, o2) -> (int)(o2[0]-o1[0])));
       for(int[] a1 :a){
           for(int a2 : a1){
               System.out.print(a2 + " ");
           }
           System.out.println();
       }
    }



}
