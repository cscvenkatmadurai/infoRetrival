package listOperation;

import java.util.LinkedList;


/**
 * Created by SAI on 6/27/2015. for InformationRetrival
 */
public class ListOperation {
    static String or = "|";
    static String and = "&";
    public static LinkedList andOrOpearation(LinkedList<Integer> list1, LinkedList<Integer> list2, String operation) {
        LinkedList<Integer> resList = new LinkedList();
        int i = 0, j = 0;
        for (i = 0, j = 0; i < list1.size() && j < list2.size(); ) {
            if (list1.get(i) == list2.get(j)) {
                resList.add(list1.get(i));
                i++;
                j++;
            } else if (list1.get(i) < list2.get(j)) {
                checkOrAndAddElement(list1, operation, resList, i);
                i++;
            } else if (list1.get(i) > list2.get(j)) {//list 2 element is lesser than list one element
                checkOrAndAddElement(list2, operation, resList, j);
                j++;
            }

        }
        if (operation.equals(or)) {
            addRemainingElements(list1, resList, i);
            addRemainingElements(list2, resList, j);
        }
        return resList;
    }

    private static void checkOrAndAddElement(LinkedList<Integer> list, String operation, LinkedList<Integer> resList, int loopIndex) {
        if (operation.equals(or)) {
            resList.add(list.get(loopIndex));
        }
    }


    private static void addRemainingElements(LinkedList<Integer> list, LinkedList<Integer> resList, int indexPosition) {

        if (indexPosition < list.size()) {
            while (indexPosition < list.size()) {
                resList.add(list.get(indexPosition));
                indexPosition++;
            }
        }
    }

    public static LinkedList<Integer> notList(LinkedList<Integer> list, int maxDocumentNo) {
        LinkedList<Integer> result = new LinkedList<>();
        int i, j;
        for (i = 1, j = 0; i <= maxDocumentNo && j < list.size(); ) {
            if (list.get(j) == i) {

                i++;
                j++;
            } else if (list.get(j) < i) {

                j++;
            } else if (i < list.get(j)) {//list 2 element is greater than list one element
                result.add(i);
                i++;
            }
        }
        addRemainingElementsNotOp(maxDocumentNo, result, i);


        return result;
    }

    private static void addRemainingElementsNotOp(int maxDocumentNo, LinkedList<Integer> result, int i) {
        if (i <= maxDocumentNo) {
            while (i <= maxDocumentNo) {
                result.add(i);
                i++;
            }
        }
    }



}
