package listOperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by HARISH on 9/28/2015.
 */
public class FileManager {
    protected String fileName[];
    final String regexSpacePattern = " +";

    public FileManager(String[] fileName) {
        this.fileName = new String[fileName.length];
        for (int i = 0; i < this.fileName.length; i++) {
            this.fileName[i] = fileName[i];
        }
    }

    public TreeMap<String,TreeSet<Integer>> loadFiles(){
        TreeMap<String,TreeSet<Integer>> ts = new TreeMap<>();
        BufferedReader read = null;
        try {
            for (int i = 0; i < fileName.length; i++) {
                 read = new BufferedReader(new FileReader(fileName[i]));
                loadFile(ts,read,i);

            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                read.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        return ts;
    }
    private void loadFile(TreeMap<String,TreeSet<Integer>> ts,BufferedReader read,int docId) throws IOException{
        String ip,data[];
        TreeSet<Integer> temp;
        while( (ip = read.readLine()) != null){
      //      System.out.println(docId+" ");
            data = ip.split(regexSpacePattern);
            loadSentence(ts, docId, data);
        }
    }

    private void loadSentence(TreeMap<String, TreeSet<Integer>> ts, int docId, String[] data) {
        TreeSet<Integer> temp;
        for (int i = 0; i < data.length; i++) {
       //     System.out.print(data[i] + " ");
            if( ts.containsKey(data[i].trim()) ){
                temp = ts.get(data[i]);
                temp.add(docId);
                ts.put(data[i].trim(),temp);

            }else {
                temp = new TreeSet<>();
                temp.add(docId);
                ts.put(data[i].trim(),temp);

            }
        }
      //  System.out.println();
    }
}
