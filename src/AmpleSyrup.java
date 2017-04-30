
import static java.lang.Double.max;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Cylinder
{
    long r, h;
    double face;
    double side;
    Cylinder(int r, int h)
    {
        this.r=r; this.h=h;
        face=Math.PI*r*r;;
        side = 2*Math.PI*r*h;
    }
    double area()
    {
        return face+side;
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
        Comparator<Cylinder> cmp1=(c1,c2)->{ 
            double d=c2.area()-c1.area();
            if (d>0)
                return 1;
            else if (d<0)
                return -1;
            else
                return 0;
        };
        Comparator<Cylinder> cmp2=(c1,c2)->{ 
            double d=c2.side-c1.side;
            if (d>0)
                return 1;
            else if (d<0)
                return -1;
            else
                return 0;
        };
        
        Comparator<Cylinder> cmp3=(c1,c2)->(int)(c2.r-c1.r);
        Collections.sort(pancakes, cmp2);
        Collections.sort(pancakes, cmp3);
        for (int i=0; i<N; i++) {            
            //out.println((long)pancakes.get(i).area()+",side="+(long)+pancakes.get(i).side+",r="+pancakes.get(i).r+",h="+pancakes.get(i).h+";");
        }
        //out.println(K);
        {
            double a = dp(0, 0, true);
            out.println(String.format("%.09f", a));
        }
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("A-small-practice.in");
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
