package CodeJam2017.round1b;


import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * Brief Description: Arrange N unicorns in a circular stalls, neighbors have no color in common
 * primary color Red, Blue, Yellow
 * secondary color: Orange=R+Y, Green=B+Y, Violet=R+B
 * SubTask 1 only primay color. each color should be <= N/2
 *  start with color of the most, arrange as 1, 4, 7, ..K
 *  add second color to fill from 2, 5, 8, .. K+1
 *  if color 2 is < K, fill the remainging with color 3, then go around to fill 3, 6,9 etc.
 * A more complicated scheme: A B C in descending order, fill AB first until A' B' C (B'==C)
 *  then fill ABAC unti A' B' C' all same, then fill as ABC
 * SubTask 2: Primary and secondary color. observe secondary can only have one neighbor
 *  e.g BOB, so we can chain all seconday together,it should have at least extra primary color left
 * after pairing OB. If B==O, then there should be no other colors.
 * One scheme is to simplify task 2 to exclude primary-secondary pairs first and arrange like subtask 1.
 * then insert the pairs back
 * todo
 * solve with dp later
 */

public class StableNeighbors {
    // ROYGBV color code
    char[]color=new char[]{'R','O','Y','G','B','V'};
    int [][]adj;// color match
    int []unicorns;
    int N;
    StableNeighbors(int n[], int N)
    {
        adj = new int[6][];
        adj[0] = new int[]{2,3,4};// R->Y,G,B
        adj[1] = new int[]{4};    // O->B, O=R+Y
        adj[2] = new int[]{0,4,5};// Y->R,B,V
        adj[3] = new int[]{0};    // G->R, G=B+Y
        adj[4] = new int[]{0,1,2};// B->R,O,Y
        adj[5] = new int[]{2};    // V->Y, V=R+B
        unicorns=n;
        this.N = N;
    }
    int index(char c)
    {
        switch(c)
        {
            case 'R': return 0;
            case 'O': return 1;
            case 'Y': return 2;
            case 'G': return 3;
            case 'B': return 4;
            case 'V': return 5;
        }
        return 0;
    }
    boolean neighbor(char c, char c2)
    {
        int i1=index(c);
        int i2=index(c2);
        return neighbor(i1, i2);
    }
    boolean neighbor(int i1, int i2)
    {
        for (int j=0; j<adj[i1].length; j++) {
            if (adj[i1][j]==i2)
                return true;
        }
        return false;        
    }
    boolean validate (String stall)
    {
        for (int i=0; i<N; i++) {
            if ( !neighbor(stall.charAt(i), stall.charAt((i+1)%N)))
            {
                out.println("index "+i+" "+stall.charAt(i)+"->"+stall.charAt((i+1)%N));
                return false;
            }
        }
        return true;
    }
    
    String result=null;
    void recurse(String res)
    {
        if (result !=null)
            return;
        if (res.length()==N) {
            if (neighbor(res.charAt(N-1)-'0', res.charAt(0)-'0'))
                result=res;
            return;
        }
        int last = res.charAt(res.length()-1)-'0';
        for (int i=0; i<adj[last].length; i++) {
            if (unicorns[adj[last][i]]>0) {
                unicorns[adj[last][i]]--;
                recurse(res+(char)(adj[last][i]+'0'));
                unicorns[adj[last][i]]++;
            }
        }
    }
    void solve()
    {
        for (int i=0; i<6; i++)
            if (unicorns[i]>0) {
                unicorns[i]--;
                recurse(""+(char)(i+'0'));
            }
        if (result==null)
            out.println("IMPOSSIBLE");
        else {
            for (int i=0; i<N; i++) {
                char uni = color[result.charAt(i)-'0'];
                out.print(uni);
            }
            out.println();
        }
    }
    
    // O->B, G->R, V->Y
    boolean validateOGV(int mix, int single)
    { 
        if (unicorns[mix]>0) { // BOBO, BOBOBO,BOBOBR
            unicorns[single] -= unicorns[mix];
            if (unicorns[single]<0)
                return false;
            if (unicorns[single]==0 && N!=2*unicorns[mix])
                return false;
        }
        return true;        
    }
    boolean validate()
    {
        if (!validateOGV(1, 4))  // OB
            return false;
        if (!validateOGV(3, 0))  // GR
            return false;
        if (!validateOGV(5, 2))  // VY
            return false;
        if (unicorns[0]>unicorns[2]+unicorns[4])
            return false;
        if (unicorns[2]>unicorns[0]+unicorns[4])
            return false;
        if (unicorns[4]>unicorns[0]+unicorns[2])
            return false;
        return true;
    }
    class PQItem
    {
        char color;
        int  num;
        public PQItem(char c, int n)
        {
            color=c; num=n;
        }
        @Override
        public String toString()
        {
            return color+":"+num;
        }
    }
    String repeat(String x, int r) {
        StringBuilder sb=new StringBuilder();
        repeat(sb, x, r);
        return sb.toString();
    }
    void repeat(StringBuilder sb, String color, int r) {
        while (r-->0)
            sb.append(color);        
    }
    String getOGV()
    {
        for (int i=1; i<6; i+=2) {
            if (unicorns[i]>0) {
                int primary = adj[i][0];  // neighbor
                return repeat(""+color[i]+color[primary], unicorns[i]);
            }
        }
        return "IMPOSSIBLE";
    }
    
    StringBuilder getRYB(List<PQItem> ryb)
    {
        StringBuilder sb = new StringBuilder();
        if (ryb.size()==2) {
            if (ryb.get(0).num !=ryb.get(1).num)
                out.println("2 color expect equal "+ryb.get(0).color+"="+ryb.get(0).num+","+ryb.get(1).color+"="+ryb.get(1).num);
            repeat(sb, ""+ryb.get(0).color+ryb.get(1).color, ryb.get(0).num);
            return sb;
        }
        int r = ryb.get(1).num-ryb.get(2).num; // 132 106 85
        if (r>0) {
            repeat(sb, ""+ryb.get(0).color+ryb.get(1).color, r);
            ryb.get(0).num -= r;
            ryb.get(1).num -= r;
        }
        while (ryb.get(0).num>ryb.get(1).num) { // 111 85 85
            int diff =ryb.get(0).num - ryb.get(1).num;
            if ( diff == ryb.get(1).num) 
                break;
            repeat(sb, ""+ryb.get(0).color+ryb.get(1).color, 1);
            repeat(sb, ""+ryb.get(0).color+ryb.get(2).color, 1);   
            ryb.get(0).num -= 2;
            ryb.get(1).num--;
            ryb.get(2).num--;
        }
        //out.println(ryb);
        if ( ryb.get(0).num == ryb.get(1).num) {
            repeat(sb, ""+ryb.get(0).color+ryb.get(1).color+ryb.get(2).color, ryb.get(0).num);
        }
        else if (ryb.get(0).num == 2*ryb.get(1).num) {
            repeat(sb, ""+ryb.get(0).color+ryb.get(1).color, ryb.get(1).num);
            repeat(sb, ""+ryb.get(0).color+ryb.get(2).color, ryb.get(1).num);        
        } else
            out.println("error");
        return sb;
    }
    
    StringBuilder getRYB2(List<PQItem> ryb)  // much short than getRYB
    {
        StringBuilder sb = new StringBuilder();
        int second=ryb.get(1).num;
        int third = second+ryb.get(2).num-ryb.get(0).num;
        for (int i=0; i<ryb.get(0).num; i++) {
            sb.append(ryb.get(0).color);
            if (second-->0)
                sb.append(ryb.get(1).color);
            else
                sb.append(ryb.get(2).color);
            if (third-->0)
                sb.append(ryb.get(2).color);
        }
        return sb;
    }
    
    void insert(StringBuilder sb, char color, char color2, int r) 
    {
        int idx = sb.toString().indexOf(color); // find primary color B
        while (r-->0) {
            sb.insert(idx, color2);  // insert OBOB
            sb.insert(idx, color);
        }
    }
    String greedy()
    {
        if (!validate()) { // remove secondary-primary pair
            return "IMPOSSIBLE";
        }
        if (unicorns[0]+unicorns[2]+unicorns[4]==0) // no extra RBY
            return getOGV();
        Comparator<PQItem> cmp = (p1,p2)->p2.num-p1.num;
        List<PQItem> ryb = new ArrayList<>();
        for (int i=0; i<6; i+=2) {
            if (unicorns[i]>0)
                ryb.add(new PQItem(color[i], unicorns[i]));
        }
        Collections.sort(ryb, cmp);
        //out.println(ryb);
        StringBuilder sb = getRYB(ryb);
        for (int i=1; i<6; i+=2) {
            if (unicorns[i]>0) {
                int neb = adj[i][0];  // neighbor
                insert(sb, color[neb], color[i], unicorns[i]);
            }
        }
        if (sb.length()!=N)
            out.println("bad length "+sb.length()+" expect "+N);
        else
            validate(sb.toString());
        return sb.toString();
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1b\\B-large-practice.in.txt");
        //sc = googlejam.ContestHelper.getFileScanner("unicorns-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) { // 3 <= N≤ 1000
            // N, R, O, Y, G, B, V
            int N = sc.nextInt();
            int[] unicorns=new int[6];
            for (int j=0; j<6; j++)
                unicorns[j] = sc.nextInt();
            out.print("Case #"+(i+1)+": ");
            //new StableNeighbors(unicorns, N).solve();
            out.println(new StableNeighbors(unicorns, N).greedy());
        }
    }
}
