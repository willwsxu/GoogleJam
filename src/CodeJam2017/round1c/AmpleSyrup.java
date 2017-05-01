package CodeJam2017.round1c;


import static java.lang.Double.max;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Cylinder
{
    long r, h;
    int  id;
    double face;
    double side;
    static int uniqueID=0;
    Cylinder(int r, int h)
    {
        id = ++uniqueID;
        this.r=r; this.h=h;
        face=Math.PI*r*r;;
        side = 2*Math.PI*r*h;
    }
    double area()
    {
        return face+side;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof Cylinder) {
            Cylinder other = (Cylinder)s;
            return r==other.r && h==other.h && id ==other.id;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(r*h);
    }
    @Override
    public String toString()
    {
        return r+":"+h;
    }
}

public class AmpleSyrup {
    
    List<Cylinder> pancakes;
    int K;
    double areaDp[];
    double areaDp2[];
    double INF=-999999999999999L;
    double dp(int start, int count, boolean bFace)
    {
        if (count==K)
            return 0; // done
        else if (start==pancakes.size())
        {
            return INF;
        }
        double area=0;
        if (bFace) {
            if (areaDp[start]>0)
                return areaDp[start];
            area =pancakes.get(start).area()+dp(start+1, count+1, false);
            area=max(area, dp(start+1, count, true));
            return areaDp[start]=area;
        } else {
            if (areaDp2[start]>0)
                return areaDp2[start];
            area =pancakes.get(start).side+dp(start+1, count+1, false);
            area=max(area, dp(start+1, count, false));
            return areaDp2[start]=area;            
        }
    }
    public AmpleSyrup(int[] R, int[] H, int K)
    {
        this.K=K;
        int N=R.length;
        areaDp=new double[N];
        areaDp2=new double[N];
        pancakes = new ArrayList<>();
        for (int i=0; i<N; i++)
            pancakes.add (new Cylinder(R[i], H[i]));
        
        double a = greedy();
        out.println(String.format("%.09f", a));
    }
    
    void print()
    {
        for (int i=0; i<pancakes.size(); i++) {            
            out.println((long)pancakes.get(i).area()+",side="+(long)+pancakes.get(i).side+",r="+pancakes.get(i).r+",h="+pancakes.get(i).h+";");
        }
        out.println(K);        
    }
    double solveDp()
    {        
        Collections.sort(pancakes, cmp_side);
        Collections.sort(pancakes, cmp_r);
        //print();
        return dp(0, 0, true);        
    }
    
    Comparator<Cylinder> cmp_r=(c1,c2)->(int)(c2.r-c1.r);
    Comparator<Cylinder> cmp_side=(c1,c2)->{ 
        double d=c2.side-c1.side;
        if (d>0)
            return 1;
        else if (d<0)
            return -1;
        else
            return 0;
    };
    PriorityQueue<Cylinder> pancakeQ; // sorted by radius
    double greedy()
    {
        int N=pancakes.size();
        if (K==1) {
            Comparator<Cylinder> cmp=(c1,c2)->{ 
                double d=c2.area()-c1.area();
                if (d>0)
                    return 1;
                else if (d<0)
                    return -1;
                else
                    return 0;
            };
            pancakeQ = new PriorityQueue<>(N, cmp);
            for (int i=0; i<N; i++) {      
                pancakeQ.add(pancakes.get(i));
            }   
            return pancakeQ.poll().area();
        }
        pancakeQ = new PriorityQueue<>(N, cmp_r);
        for (int i=0; i<N; i++) {      
            pancakeQ.add(pancakes.get(i));
        }   
        Collections.sort(pancakes, cmp_side);
        double ans=0;
        while(!pancakeQ.isEmpty() && pancakes.size()>=K) { //pick largest bottom
            Cylinder curr = pancakeQ.poll();
            int chosen=1;
            double area=curr.area();
            for (int i=0; i<pancakes.size(); i++) {   
                if (pancakes.get(i).r>curr.r)
                    pancakes.remove(i);
                else if (pancakes.get(i).equals(curr))
                    continue;
                else {
                    area += pancakes.get(i).side;
                    if (++chosen==K)
                        break;
                }
            }   
            if (ans<area)
                ans=area;
        }
        return ans;
    }
    
    static void test()
    {
        new AmpleSyrup(new int[]{3132, 3904, 15050, 3304}, new int[]{391970, 362400, 15, 363402}, 3);
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        test();
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1c\\A-small-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();  // 1 ≤ N ≤ 1000
            int K = sc.nextInt();  // 1 ≤ K ≤ N
            int R[] = new int[N];  // 1 ≤ Ri ≤ 10^6
            int H[] = new int[N];  // 1 ≤ Hi ≤ 10^6
            for (int j=0; j<N; j++) {
                R[j] = sc.nextInt();
                H[j] = sc.nextInt();
            }
            out.print("Case #"+(i+1)+": ");
            new AmpleSyrup(R, H, K);
        }
    }
}
