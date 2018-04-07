
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 */


public class SaveUniverse {
    
    static int solve(int damage, String str)
    {
        List<int[]> strength=new ArrayList<>();
        int pos=0;
        int s=1; // initial strength
        int minimum=0;
        for (int i=0; i<str.length(); i++) {
            switch (str.charAt(i)) {
                case 'C':
                    s <<=1; // double
                    if (strength.size()>pos)
                        pos++;  // ready to add next shoot
                    break;
                case 'S':
                    if (strength.size()==pos)
                        strength.add(new int[]{s,1});
                    else {
                        strength.get(pos)[1]++; // count S at this strength
                    }
                    minimum++;
                    break;
            }
        }
        int total=0;
        for (int[] item: strength) {
            total += item[0]*item[1];
        }
        //System.out.println(total);
        if (damage<minimum)
            return -1;
        int ans=0;
        total -= damage;
        pos = strength.size()-1;
        while (total>0) {
            int[]item=strength.get(pos);
            s=item[0]/2; // reduction of strength, backwards
            if (s==0)  // no more move
                break;
            int count=(total+s-1)/s;
            if (count<=item[1]) {
                ans += count;
                break;
            }
            ans += item[1];
            total -= item[1]*s;
            if (pos>0) {
                int[] prev=strength.get(pos-1);
                if (prev[0]==s) { // find a previous charge
                    prev[1] += item[1];
                    pos--;
                    continue;
                }
            }
            item[0]=s; // just set current item to half strength
        }
        return ans;
    }
    static void test(){
        System.out.println(solve(1, "CS")==1);
        System.out.println(solve(2, "CS")==0);
        System.out.println(solve(1, "SS")==-1);
        System.out.println(solve(6, "SCCSSC")==2);
        System.out.println(solve(2, "CC")==0);
        System.out.println(solve(3, "CSCSS"));        
    }
    static Scanner sc = new Scanner(System.in);  
    private static void judge()
    {
        int T=sc.nextInt();
        for (int t=1; t<=T; t++) {
            int d=sc.nextInt();
            String s=sc.next();
            int ans=solve(d,s);
            System.out.println("Case #"+t+": "+(ans<0?"IMPOSSIBLE":ans));
        }
    }
    public static void main(String[] args)  
    {
        judge();
    }
}
