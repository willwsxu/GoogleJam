/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("closematch-s.in.txt");
        sc = googlejam.ContestHelper.getFileScanner("closematch-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String C = sc.next(); // 1 ≤ len(C) ≤ 18
            String J = sc.next(); // 1 ≤ len(J) ≤ 18
            out.print("Case #"+(i+1)+": ");
            new CloseMatch().greedy(C, J);
        }
    }
}
