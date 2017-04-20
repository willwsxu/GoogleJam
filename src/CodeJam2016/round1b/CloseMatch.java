/*
 * the absolute difference between the integers represented by c and j is minimized. 
 * If there are multiple solutions with the same absolute difference, use the one in 
 * which c is minimized; if there are multiple solutions with the same absolute 
 * difference and the same value of c, use the one in which j is minimized.
 */
package CodeJam2016.round1b;

import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

public class CloseMatch {
    
    // process extra from left
    // return true if extra contains one zero digit
    boolean headStart(String old, StringBuilder newVal, int len)
    {
        boolean bigger=false;
        for (int i=0; i<len; i++) {
            if (old.charAt(i)=='?')
                newVal.append('0');
            else {
                newVal.append(old.charAt(i));
                if (old.charAt(i)!='0')
                    bigger=true;
            }
        }
        return bigger;
    }
    String fill0(String str, int len)  // fill 0 at beginning
    {
        char[] c=new char[len];
        Arrays.fill(c, '0');
        String s = new String(c)+str;
        //out.println("fill "+s);
        return s;
    }
    
    void greedy(String C, String J)
    {
        int cmp=0; // -1 c<J, 0 same, 1 C>J
        StringBuilder newC=new StringBuilder(), newJ=new StringBuilder();
        int len = min(C.length(), J.length());
        if (C.length()>len) {
            J = fill0(J, C.length()-len);
        }
        else if (J.length()>len) {
            C = fill0(C, J.length()-len);
        }
        len = C.length();
        for (int i=0; i<len; i++) {
            int lookahead=0;
            if (i<len-1) {
                if (C.charAt(i+1)!='?' && J.charAt(i+1)!='?') {
                    lookahead = C.charAt(i+1)-J.charAt(i+1);
                }
            }
            if (C.charAt(i)=='?' && J.charAt(i)=='?') {
                if (cmp<0) {
                    newC.append('9');
                    newJ.append('0');
                } else if (cmp>0) {
                    newC.append('0');
                    newJ.append('9');                    
                } else {
                    if ( lookahead> 5 ) {
                        newC.append('0');
                        newJ.append('1');   
                        cmp=-1;
                    }
                    else if ( lookahead < -5 ) {
                        newC.append('1');
                        newJ.append('0');   
                        cmp=1;
                    } else {
                        newC.append('0');
                        newJ.append('0');   
                    }
                }
            }
            else if (C.charAt(i)=='?') {
                if (cmp<0) {
                    newC.append('9');
                } else if (cmp>0) {
                    newC.append('0');                  
                } else {
                    char ch=J.charAt(i);
                    if ( lookahead> 5 && ch>'0' ) { // ?7 vs 11
                        newC.append((char)( ch-1)); 
                        cmp=-1;
                    }
                    else if ( lookahead < -5 && ch<'9') { // ?1 vs 07
                        newC.append((char)(ch+1));  
                        cmp=1;                  
                    }
                    else
                        newC.append(ch);                   
                }   
                newJ.append(J.charAt(i));     
            }
            else if (J.charAt(i)=='?') {
                if (cmp<0) {
                    newJ.append('0');
                } else if (cmp>0) {
                    newJ.append('9');                    
                } else {
                    char ch=C.charAt(i);
                    if ( lookahead> 5 && ch<'9' ) {  // 07 vs ?1
                        //out.println(newJ.toString());
                        newJ.append((char)(ch+1));  // need cast to get char instead of int
                        //out.println(newJ.toString());
                        cmp=-1;                    
                    }
                    else if ( lookahead < -5 && ch>'0' ) {  // 11 vs ?7
                        newJ.append((char)( ch-1));  
                        cmp=1;                                  
                    } else
                        newJ.append(ch);                     
                }
                newC.append(C.charAt(i));
            }
            else {
                if (cmp==0 ) {
                    cmp = C.charAt(i)>J.charAt(i)? 1:-1;
                }
                newC.append(C.charAt(i));
                newJ.append(J.charAt(i));  
            }
        }
        out.println(newC.toString()+" "+newJ.toString());
    }
    void fill(String str, StringBuilder sb, int f, char c) {
        for (int j=f; j<str.length(); j++) {
            if (str.charAt(j)=='?')
                sb.append(c);
            else
                sb.append(str.charAt(j));
        }
    }
    
    void fillSame(char cc, char jj, StringBuilder newC, StringBuilder newJ)
    {
        if (cc=='?' && jj=='?') {
            newC.append('0');
            newJ.append('0');
        }
        else if (cc=='?') {
            newC.append(jj);
            newJ.append(jj);                        
        } else if (jj=='?') {
            newC.append(cc);
            newJ.append(cc);                           
        } else {
            newC.append(cc);
            newJ.append(jj);                                
        }
    }
    void greedy2(String C, String J)
    {
        StringBuilder newC=new StringBuilder(), newJ=new StringBuilder();
        int len = C.length();
        outterfor:
        for (int i=0; i<len; i++) {
            char cc=C.charAt(i);
            char jj=J.charAt(i);
            if (cc !='?' && jj !='?') {
                if (cc!=jj) {  // 1?? 23?
                    boolean cmp = cc>jj;
                    fill(C, newC, i, cmp?'0':'9');
                    fill(J, newJ, i, cmp?'9':'0');
                    break;
                }
                else { // 91?? 923?
                    newC.append(cc);
                    newJ.append(jj);
                }
            }else {  // one current char is '?'
                int lookAhead=0;
                for (int k=i+1; k<len; k++) {
                    if (C.charAt(k)=='?' || J.charAt(k)=='?') { // ?2?9 ?242  ?2?9 ?249 ?2?9 ?2??
                        fillSame(cc,jj,newC,newJ);
                        continue outterfor;  // defer decision to last ? before differing digits
                    }
                    else if ( C.charAt(k)==J.charAt(k))
                        continue;
                    else {
                        lookAhead = C.charAt(k)-J.charAt(k);
                        break;
                    }
                }
                if (lookAhead==0) { // 9?23 9?23 or ? 9
                    for (int k=i; k<len; k++) {
                        fillSame(C.charAt(k), J.charAt(k), newC, newJ);
                    }
                } else {
                    // make a copy for two possible solutions
                    StringBuilder newC2 = new StringBuilder();
                    StringBuilder newJ2 = new StringBuilder();
                    newC2.append(newC.toString());
                    newJ2.append(newJ.toString());
                    // keep it consistent newC<newJ, newC2>newJ2
                    if (lookAhead<0) {
                        if (cc=='?' && jj=='?') { //?41 ?91 ?41 ?90 ?40 ?91
                            newC.append('0');
                            newJ.append('0');
                            newC2.append('1');
                            newJ2.append('0');
                        } else if (cc=='?') { // ?3 89  ?4 89 ?3 99
                            newC.append(jj);
                            newJ.append(jj);
                            if (jj<'9') {
                                newC2.append((char)(jj+1));
                                newJ2.append(jj);
                            } else {  // mark as duplicate case
                                newC2=null;
                                newJ2=null;
                            }
                        } else {             // 12 ?9 02 ?9 ** 14 ?9
                            newC.append(cc);
                            newJ.append(cc);
                            if (cc>'0') {
                                newJ2.append((char)(cc-1));
                                newC2.append(cc); 
                            } else {  // mark as duplicate case
                                newC2=null;
                                newJ2=null;  
                            }
                        }                  
                    } else { // C>J   
                        if (cc=='?' && jj=='?') { //?9 ?2 ?9 ?4 ?9 ?6
                            newC2.append('0');
                            newJ2.append('0');
                            newC.append('0');
                            newJ.append('1');
                        } else if (cc=='?') { // ?9 12 ?9 14 ?9 02
                            newC2.append(jj);
                            newJ2.append(jj);
                            if (jj>'0') {
                                newC.append((char)(jj-1));
                                newJ.append(jj);
                            } else {  // mark as duplicate case
                                newC=null;
                                newJ=null;
                            }
                        } else {  // 89 ?2 89 ?4 99 ?2
                            newC2.append(cc);
                            newJ2.append(cc);
                            if (cc<'9') {
                                newC.append(cc);   
                                newJ.append((char)(cc+1));
                            } else {  // mark as duplicate case
                                newC=null;
                                newJ=null;
                            }
                        }                       
                    }
                    if ( newC !=null ) {
                        fill(C, newC, i+1, '9');
                        fill(J, newJ, i+1, '0');
                    }
                    if (newC2 != null) {
                        fill(C, newC2, i+1, '0');
                        fill(J, newJ2, i+1, '9');   
                    }
                    if ( newC ==null ) {
                        newC=newC2;
                        newJ = newJ2;
                    } else if (newC2 !=null) {
                        long diff1 = Long.parseLong(newC.toString())-Long.parseLong(newJ.toString());
                        long diff2 = Long.parseLong(newC2.toString())-Long.parseLong(newJ2.toString());
                        if (diff1<0)
                            diff1 = -diff1;
                        if (diff2<0)
                            diff2 = -diff2;
                        if (diff2<diff1) {
                            newC=newC2;
                            newJ=newJ2;
                        } else if (diff2==diff1) {
                            if ( Long.parseLong(newC2.toString())<Long.parseLong(newC.toString()) )
                                newC = newC2;
                            if ( Long.parseLong(newJ2.toString()) < Long.parseLong(newJ.toString()) )
                                newJ = newJ2;
                        }                       
                    }
                }
                break;
            }
        }
        out.println(newC.toString()+" "+newJ.toString());        
    }
    
    class Answer{
        String C;
        String J;
        long vC, vJ;
        long diff;
        Answer(String c, String j) {
            C=c; J=j;
            calc();
        }
        void calc(){
            vC = Long.parseLong(C.toString());
            vJ = Long.parseLong(J.toString());
            diff = vC-vJ;  
            if (diff<0)
                diff=-diff;
        }
        private void copy(Answer ans)
        {
            C=ans.C;    
            vC=ans.vC; 
            J=ans.J; 
            vJ = ans.vJ;
            diff = ans.diff;
        }
        void compareExchange(String c, String j) {
            Answer ans = new Answer(c, j);
            if ( ans.diff < diff) {
                copy(ans);
            } else if (ans.diff==diff) {
                if (ans.vC < vC) {
                    C=ans.C;    
                    vC=ans.vC; 
                    diff = ans.diff;                    
                }
                if (ans.vJ < vJ) {
                    J=ans.J; 
                    vJ = ans.vJ;
                    diff = ans.diff;                    
                }
            }
        }
    }
    Answer ans=null;
    void addAnswer(String c, String j)
    {
        //out.println("ans:"+c+" "+j);
        if (ans==null)
            ans=new Answer(c, j);
        else
            ans.compareExchange(c, j);
    }
    void addAnswer(String C, String J, StringBuilder newC, StringBuilder newJ, char c, char j, int i)
    {
        // make a copy try out potential solution
        StringBuilder newC2 = new StringBuilder();
        StringBuilder newJ2 = new StringBuilder();
        newC2.append(newC.toString());
        newJ2.append(newJ.toString());
        newC2.append(c);
        newJ2.append(j);
        fill(C, newC2, i, c<j?'9':'0');
        fill(J, newJ2, i, c<j?'0':'9');
        addAnswer(newC2.toString(), newJ2.toString());
    }
    
    void greedy3(String C, String J)
    {
        if (C.length() != J.length())
            return;
        StringBuilder newC=new StringBuilder(), newJ=new StringBuilder();
        for (int i=0; i<C.length(); i++) {
            char cc=C.charAt(i);
            char jj=J.charAt(i);
            if (cc != '?' && jj != '?') {
                if (cc!=jj) {  // 1?? 23?
                    boolean cmp = cc>jj;
                    fill(C, newC, i, cmp?'0':'9');
                    fill(J, newJ, i, cmp?'9':'0');
                    //addAnswer(newC.toString(), newJ.toString());
                    break;
                }
                else { // 91?? 923?
                    newC.append(cc);
                    newJ.append(jj);
                }
            } else if ( cc=='?' && jj=='?') {
                addAnswer(C, J, newC, newJ, '0', '1', i+1);
                addAnswer(C, J, newC, newJ, '1', '0', i+1);
                newC.append('0');
                newJ.append('0');
            } else if ( cc=='?') {
                if (jj<'9')
                    addAnswer(C, J, newC, newJ, (char)(jj+1), jj, i+1); 
                if (jj>'0')
                    addAnswer(C, J, newC, newJ, (char)(jj-1), jj, i+1); 
                newC.append(jj);
                newJ.append(jj);
            } else {
                if (cc<'9')
                    addAnswer(C, J, newC, newJ, cc, (char)(cc+1), i+1); 
                if (cc>'0')
                    addAnswer(C, J, newC, newJ, cc, (char)(cc-1), i+1); 
                newC.append(cc);
                newJ.append(cc);
            }
        }
        addAnswer(newC.toString(), newJ.toString());
        out.println(ans.C+" "+ans.J);      
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("closematch-l.in.txt");
        //sc = googlejam.ContestHelper.getFileScanner("closematch-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String C = sc.next(); // 1 ≤ len(C) ≤ 18
            String J = sc.next(); // 1 ≤ len(J) ≤ 18
            out.print("Case #"+(i+1)+": ");
            new CloseMatch().greedy3(C, J);
        }
    }
}
