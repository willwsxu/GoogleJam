/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.qualification;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Quaternions
{
    static class Value{
        int sign;
        Character val;
        
        Value(int s, char v)
        {
            sign=s;
            val=v;
        }
    }
    static Map<String, Value> mapping=new HashMap();
    static {
        mapping.put("ii", new Value(-1, '1'));
        mapping.put("jj", new Value(-1, '1'));
        mapping.put("kk", new Value(-1, '1'));
        mapping.put("ij", new Value(1, 'k'));
        mapping.put("jk", new Value(1, 'i'));
        mapping.put("ki", new Value(1, 'j'));
        mapping.put("ji", new Value(-1, 'k'));
        mapping.put("kj", new Value(-1, 'i'));
        mapping.put("ik", new Value(-1, 'j'));
    }
    Quaternions()
    {
    }
    boolean moreInput()
    {
        return sb.length()<2;
    }
    void feed(char c)
    {
        sb.append(c);
    }
    boolean compute(char target)
    {
        if (sb.length()!=2)
            return false;
        Value v=mapping.get(sb.toString());
        sb.setLength(0);
        if (v.val!='1')
            sb.append(v.val);
        sign *= v.sign;
        if (sign>0 && v.val==target)
            return true;        // got target value
        return false;
    }
    // compute multiplication successively for all values in str
    void compute(String str)
    {
        int i=0;
        while (i<str.length()) {
            feed(str.charAt(i++));
            if (!moreInput())
                compute('0');// just compute, don't care about target
        }
    }
    void repeat(long r)
    {
        if (sb.length()>1) {
            out.println("repeat err");
            return;
        }
        if ( sb.length()==1 ) {  // repeat any one letter (i, j, or k)
            switch((int)(r%4)) {
                case 0:
                default:
                    sb.setLength(0);
                    sign=1;
                    break;  // i*i*i*i=1, sign is 1
                case 2:
                    sb.setLength(0);
                    sign=-1;// due to i*i=-1
                    break;
                case 1:  // no change to sign or letter
                    break;
                case 3:
                    sign *= -1;  // slip sign due to i*i=-1
                    break;
            }
        } else {
            switch((int)(r%4)) {
                case 0:     sign=1; break;
                case 1:
                default:
                    break;                    
            }
        }
    }
    boolean valid(long r)  // i*j*k=-1
    {
        repeat(r);
        if ( sign==-1 && sb.length()==0)
            return true;
        return false;
    }
    boolean empty()
    {
        if (sign==1 && sb.length()==0)
            return true;
        return false;
    }
    int sign=1;
    StringBuilder sb=new StringBuilder();
}


public class Dijkstra 
{    
    boolean solve(String str, long repeat) {
        // compute all string operation plus repeat to see if result is -1
        Quaternions q=new Quaternions();
        q.compute(str);
        if (!q.valid(repeat))
            return false;  // fail if result is not -1
        q=new Quaternions();
        // first compute to get i
        char target='i';
        outterfor:
        for (int r=0; r<repeat; r++) {            
            for (int index=0; index<str.length(); index++) {
                if (q.empty() && str.charAt(index)==target) { // find target before compute
                    if (target=='j') // got both
                        return true;
                    target='j';
                    continue;
                }
                q.feed(str.charAt(index));
                if (!q.moreInput()) {                    
                    if (q.compute(target)) {
                        if (target=='j') // got both
                            return true;
                        target='j';
                        continue;                        
                    }
                }
            }
        }
        return false;
    }
    public static void test()
    {
        out.println(new Dijkstra().solve("ik", 1));
        out.println(new Dijkstra().solve("ijk", 1));
        out.println(new Dijkstra().solve("kji", 1));
        out.println(new Dijkstra().solve("ji", 6));
        out.println(new Dijkstra().solve("i", 10000));
        out.println(new Dijkstra().solve("ii", 80));
        out.println(new Dijkstra().solve("ii", 81));
        out.println(new Dijkstra().solve("ii", 82));
        out.println(new Dijkstra().solve("ii", 83));
        out.println(new Dijkstra().solve("ik", 80));
        out.println(new Dijkstra().solve("ik", 81));
        out.println(new Dijkstra().solve("ik", 82));
        out.println(new Dijkstra().solve("ik", 83));
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        judge();
    }
    public static void judge()
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\qualification\\C-small-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int L = sc.nextInt();  // character len, 1 ≤ L ≤ 10000, 1 ≤ L * X ≤ 10^16
            long X=sc.nextLong();
            String p = sc.next();
            String ans = new Dijkstra().solve(p, X)?"YES":"NO";
            out.println("Case #"+(i+1)+": "+ans);
        }
    }
}
