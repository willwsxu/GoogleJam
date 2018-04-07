
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * bubble sort every other number
 */

public class TroubleSort {
    static int solve(int[] nums)
    {
        int n=nums.length/2;
        int[] odd=new int[nums.length-n];
        int[] even=new int[n];
        for (int i=0; i<nums.length; i +=2) {
            odd[i/2]=nums[i];            
        }
        for (int i=1; i<nums.length; i +=2) {
            even[i/2]=nums[i];            
        }
        Arrays.sort(odd);
        Arrays.sort(even);
        for (int i=0; i<nums.length; i +=2) {
            nums[i]=odd[i/2];            
        }
        for (int i=1; i<nums.length; i +=2) {
            nums[i]=even[i/2];            
        }
        for (int i=0; i<nums.length-1; i++)
            if (nums[i]>nums[i+1])
                return i;
        return -1; //OK
    }
    static void test(){
        System.out.println(solve(new int[]{5, 6, 8, 4, 3})==-1);
        System.out.println(solve(new int[]{8,9,7})==1);
        System.out.println(solve(new int[]{8,5,7,6})==0);
    }
    static MyScanner sc=new MyScanner();
    private static void judge()
    {
        int T=sc.nextInt();
        for (int t=1; t<=T; t++) {
            int n=sc.nextInt();
            int nums[]=sc.ria(n);
            int ans=solve(nums);
            System.out.println("Case #"+t+": "+(ans<0?"OK":ans));
        }
    }
    
    public static void main(String[] args)  
    {
        judge();        
    }
}

// credit to http://codeforces.com/blog/entry/7018
class MyScanner {
    BufferedReader br;
    StringTokenizer st;

    MyScanner(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyScanner bad file "+f);
        }
    }
    public MyScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine(){
        String str = "";
        try {
           str = br.readLine();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return str;
    }
    
    public int ni()
    {
        return nextInt();
    }     
    public long nl()
    {
        return nextLong();
    }   
    public int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=nextInt();
        return L;
    }
    public long[] rla(int N) { // read long array
        long L[]=new long[N];
        for (int i=0; i<N; i++)
            L[i]=nextLong();
        return L;
    }
}
