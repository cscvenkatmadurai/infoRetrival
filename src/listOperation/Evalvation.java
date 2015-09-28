package listOperation;

/**
 * Created by HARISH on 6/27/2015.
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;

public class Evalvation {
    static int noOfList;
    final static int noOfAlphabets = 26;
    static int mapArray[] = new int[noOfAlphabets];
    static LinkedList<Integer> listArr[];
    static BufferedReader kb = new BufferedReader( new InputStreamReader( System. in ) );
    static String and = "and";
    static String or = "or";
    static int documentNo;


    public static void findCountOfLists(String ip) {
        noOfList = 0;
        for (int i = 0; i < ip.length() ; i++) {
            if (Character.isAlphabetic(ip.charAt(i)) && mapArray[ip.charAt(i) - 97] == 0) {
                mapArray[ip.charAt(i) - 97] = ++noOfList;

            }
        }
    }
    public static void getListData() throws IOException, ipNumberGreaterThanDocNoException {
        listArr = new LinkedList[noOfList];
        System.out.println("The No of lists are "+noOfList);
        System.out.println("Enter the maximum document no");
        documentNo = Integer.parseInt( kb.readLine() );

        for (int i = 0; i < listArr.length ; i++) {
            TreeSet<Integer> ts = new TreeSet<>();
            boolean isGetElement = true;
            do{
                isGetElement = getNo(i, ts, isGetElement);

            }while (isGetElement);
            listArr[i] = new LinkedList<>(ts);
            System.out.println("The list "+(i+1)+" is"+listArr[i]);
        }

    }

    private static boolean getNo(int loopIndex, TreeSet<Integer> ts, boolean isGetElement) throws IOException, ipNumberGreaterThanDocNoException {
        System.out.println("Enter the element for " + (loopIndex + 1) + "list");
        int no = Integer.parseInt(kb.readLine());
        if(no > documentNo){
            throw new ipNumberGreaterThanDocNoException();
        }
        ts.add(no);
        System.out.println(" \t\tto add next element press 1 else 0");
        if(Integer.parseInt(kb.readLine()) == 0){
            isGetElement = false;
        }
        return isGetElement;
    }

    public static LinkedList<Integer> evalvateExpr(String ip){
        Stack<Character> operator = new Stack<>();
        Stack<LinkedList<Integer>> operand = new Stack<>();
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < ip.length() ; i++) {
            if(Character.isAlphabetic(ip.charAt(i))){
                operand.push(listArr[
                        mapArray[   ip.charAt(i) -97 ]-1
                        ]);
                performContinousNot(operator, operand);

            }else if( ip.charAt(i) == ')'  ){
                char temp;
                while( operator.size() > 0 &&( temp = operator.peek())!= '(' ){
                    operator.pop();
                    if(temp == '&'){
                        performAndOperation(operand);
                    }else if( temp == '|'){
                        performOrOperation(operand);

                    }else{
                        System.out.println("not");
                        operand.push(ListOperation.notList(operand.pop(),documentNo) );
                    }
                }
                operator.pop();//to remove (

            }else{//other operators like & | (
                operator.push(ip.charAt(i));
            }
        }
        clearOpearatorStack(operator, operand);
        return operand.pop();
    }

    private static void clearOpearatorStack(Stack<Character> operator, Stack<LinkedList<Integer>> operand) {
        while (operator.size() > 0){
             char temp = operator.pop();
            System.out.println(temp);
             if(temp == '&'){
                 operand.push(ListOperation.andOrOpearation(operand.pop(), operand.pop(), and));

             }else if( temp == '|'){
                 operand.push(ListOperation.andOrOpearation(operand.pop(),operand.pop(),or));
             }else if(temp == '!'){
                 operand.push(ListOperation.notList(operand.pop(),documentNo) );
             }
         }
    }

    private static void performOrOperation(Stack<LinkedList<Integer>> operand) {
        //  System.out.println("or");
        LinkedList<Integer> l1 = operand.pop();
        LinkedList<Integer> l2 = operand.pop();
        //  System.out.println(l1+" "+l2 +" "+ListOperation.andOrOpearation(l1, l2, or));
        operand.push(ListOperation.andOrOpearation(l1, l2, or) );
    }

    private static void performAndOperation(Stack<LinkedList<Integer>> operand) {
        // System.out.println("and");
        LinkedList<Integer> l1 = operand.pop();
        LinkedList<Integer> l2 = operand.pop();
        //System.out.println(l1+" "+l2 +" "+ListOperation.andOrOpearation(l1,l2,and));
        operand.push(ListOperation.andOrOpearation(l1, l2, and));
    }

    private static void performContinousNot(Stack<Character> operator, Stack<LinkedList<Integer>> operand) {
        while(operator.size() > 0 && operator.peek() == '!'){
            operator.pop();
            operand.push(ListOperation.notList(operand.pop(), documentNo));
        }
    }
    private static void displayInfo(){
        System.out.println("\t\t\tList Validator");
        System.out.println("\tuse charactors for lists[a-z]");
        System.out.println("\tuse and- & or - | not -!");
        System.out.println("ex -!((a&b)||c)");
    }

    public static void main(String[] args) throws IOException{

        String ip = kb.readLine();
        ip = ip.toLowerCase();
        if( Validation.checkParanthasis(ip) == 0){
            displayInfo();
            findCountOfLists(ip);
            try{
                getListData();
            }catch (ipNumberGreaterThanDocNoException e){
                System.out.println(e.getMessage());
            }

            System.out.println(evalvateExpr(ip));
        }else if( Validation.checkParanthasis(ip) == -1){
            System.out.println("missing ( paranthesis");
        }else if(Validation.checkParanthasis(ip) == 1){
            System.out.println("missing ) paranthesis");
        }

    }
}
