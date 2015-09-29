package listOperation;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.TreeSet;
import java.util. TreeMap;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 * Created by HARISH on 9/28/2015.
 */
public class EvalvationWithFile {
    protected TreeMap<String,TreeSet<Integer>> ts;
    protected int noOfDocuments;

    public EvalvationWithFile(String fileNmaes[]) {
        FileManager fm = new FileManager(fileNmaes);
        this.ts = fm.loadFiles();
        noOfDocuments = fileNmaes.length-1; //as array indexing starts from 0
    }

    public void printTreeSet() {
        Set<String> arr = ts.keySet();
        Iterator<String> it = arr.iterator();
        while (it.hasNext()){
            String text = it.next();
            System.out.println(text+" "+ts.get(text ) );
        }
    }
    private  void displayInfo(){
        System.out.println("\t\t\tList Validator");
        System.out.println("\tuse charactors for lists[a-z]");
        //    System.out.println("\tuse and- & or - | not -!");
        System.out.println("ex -!((a&b)||c)");
    }
    private boolean validateParanthesis(String ip){
        if(Validation.checkParanthasis(ip) == -1){
            System.out.println("missing ( paranthesis");
            return false;
        }else if(Validation.checkParanthasis(ip) == 1){
            System.out.println("missing ) paranthesis");
            return false;
        }


        return true;

    }
    private boolean isOperator(String token){
        String operator[] = {"&","|","!","(",")"};
        for (int i = 0; i < operator.length; i++) {
            if(token.equals(operator[i])) return  true;
        }
        return false;

    }
    private boolean isValidKey(String token){
        Set<String> keys = ts.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()){

            while (iter.next().equals(token)) return true;
        }
        return false;
    }
    private String[] convertExpressionToStringArray(String expression){
        StringTokenizer tokenizer ;
        String delimiter = "|&!()";
        LinkedList<String> arr = new LinkedList<>();
        tokenizer = new StringTokenizer(expression,delimiter,true);
        while (tokenizer.hasMoreTokens()){
            arr.add(tokenizer.nextToken());
        }
       return arr.toArray(new String[arr.size()]);
    }
    private boolean validateTokens(String tokens[]){
        String invalid = "Invlid token";
        for (int i = 0; i < tokens.length ; i++) {
            if(!isValidKey(tokens[i]) && !isOperator(tokens[i])) {
                System.out.println(tokens[i]+" "+invalid);
                return false;
            }
        }
        return  true;
    }
    private LinkedList<Integer> covertTreeSetToLinkedList(TreeSet<Integer> tset){
        return new LinkedList<>(tset);
    }
    private LinkedList<Integer> performContinousNot(LinkedList<Integer> operand,Stack<String> operator){
        boolean evenNoNot = true;
        String not = "!";
        while (operator.size() > 0 &&  operator.peek().equals(not)      ) {
            operator.pop();
            evenNoNot  = !evenNoNot;
        }
        if(!evenNoNot){
            return ListOperation.notList(operand,noOfDocuments);
        }
        return operand;

    }


    private LinkedList<Integer> evalvateExpression(String tokens[]){
        String openBracket = "(",closeBracket = ")";
        Stack<LinkedList<Integer>> operand = new Stack<>();
        Stack<String> operator = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if(isOperator(tokens[i])){
                if( !tokens[i].equals(closeBracket)){
                    operator.push(tokens[i]);
                }else{
                    String op;
                    while (operator.size() > 0 && !((op = operator.peek()).equals(openBracket)) ){
                        operator.pop();
                        computeAndOrNot(operand, op);

                    }
                    operator.pop();//to remove (
                }

            }else {
                LinkedList<Integer> operand1 = covertTreeSetToLinkedList(ts.get(tokens[i]));

                operand.push(performContinousNot(operand1, operator));
             //   System.out.println(operand.peek());
            }
        }

        performUnbracketedExpression(operand,operator);
        if(operand.size() == 1) {
            return operand.pop();
        }else {
            System.out.println("Oops Invalid expression");
            return null;
        }
    }
    /*
     for ip a&b


     */
    private void performUnbracketedExpression(Stack<LinkedList<Integer>>operand,Stack<String> operator){
        String operator1;
        while (operator.size() > 0 ){
            operator1 = operator.pop();
            computeAndOrNot(operand,operator1);
        }

    }

    private void computeAndOrNot( Stack<LinkedList<Integer>> operand, String op) {
        String and = "&",or = "|",not = "!";

        try {
            if (op.equals(and)) {

                operand.push(ListOperation.andOrOpearation(operand.pop(), operand.pop(), and));

            } else if (op.equals(or)) {
                System.out.println("or");
                operand.push(ListOperation.andOrOpearation(operand.pop(), operand.pop(), or));

            } else if (op.equals(not)) {

                operand.push(ListOperation.notList(operand.pop(), noOfDocuments));
            }
        }catch (Exception e){
            System.out.println("Invalid Expression missing operands");
        }
    }


    public void getIpValidateEvalvate(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String ip,tokens[];
        displayInfo();
        System.out.println();
        printTreeSet();
        try {
            ip = reader.readLine();
            if (validateParanthesis(ip)) {
                tokens = convertExpressionToStringArray(ip);
                if (validateTokens(tokens)) {
                    LinkedList<Integer> ans = evalvateExpression(tokens);
                    if(ans != null) {
                        System.out.println("The Reultant list is" + evalvateExpression(tokens));
                    }
                }

            }
        }catch (IOException e){

        }finally {
            try {
                reader.close();
            }catch (IOException e){

            }
        }
    }




    public static void main(String[] args) {
        String file[] = {"C:\\Users\\HARISH\\IdeaProjects\\infoRet\\src\\listOperation\\file1.txt","C:\\Users\\HARISH\\IdeaProjects\\infoRet\\src\\listOperation\\file2.txt","C:\\Users\\HARISH\\IdeaProjects\\infoRet\\src\\listOperation\\file3.txt"};
       EvalvationWithFile ef = new EvalvationWithFile(file);
        ef.getIpValidateEvalvate();


    }
}
