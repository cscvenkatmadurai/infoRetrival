package listOperation;

import java.util.Stack;

/**
 * Created by HARISH on 6/27/2015.
 */
public class Validation {
    public  static int checkParanthasis(String ip){
        int noOfParanthesis = 0;
        Stack<Character>  paranthesis= new Stack<Character>();
        for (int j = 0; j < ip.length() ; j++) {
            if( ip.charAt(j) == '(' ){
                 paranthesis.push(ip.charAt(j));
            }else if(ip.charAt(j) == ')') {
                if (paranthesis.size() > 0) {
                    paranthesis.pop();
                } else if (paranthesis.size() <= 0) {
                    return -1;
                }
            }
        }
        if(paranthesis.size() == 0){
            return  0;
        }else if(paranthesis.size() > 0){
            return 1;
        }
        return 10;//dummy return;
    }
}
