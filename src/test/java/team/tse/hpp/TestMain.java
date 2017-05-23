package team.tse.hpp;

/**
 * Created by TGHead on 2017/4/30.
 */

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestMain {
    private final String[] testname = {"Q1Basic", "Q1Basic2", "Q1BigTest", "Q1Case1", "Q1Case2", "Q1Case3", "Q1Case4", "Q1Case5", "Q1CommentCount", "Q1PostExpiredComment", "Q1PostExpiredComment2"};
    private int indice =10;

    private final String postfilepath = "src/test/Test resources/Tests/" + testname[indice] + "/posts.dat";
    private final String commentfilepath = "src/test/Test resources/Tests/" + testname[indice] + "/comments.dat";
    private final String expectedfilepath = "src/test/Test resources/Tests/" + testname[indice] + "/_expectedQ1.txt";

    private Main main;
    private BufferedReader filereader;

    @Before
    public void setup() {
        File file_expected = new File(expectedfilepath);
        try {
            filereader = new BufferedReader(new FileReader(file_expected));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        main = new Main(postfilepath, commentfilepath);
    }

    @Test
    public void test() {
        ArrayList<String> resLine = main.fortest();
        for (int i = 0; i < resLine.size(); i++) {
            String expLine = null;
            try {
                expLine = filereader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Expected: " + expLine);
            System.out.println("Actual:   " + resLine.get(i));
            assertEquals(expLine, resLine.get(i));
        }
        try {
            if (filereader.readLine() != null) {
                fail("Amount error!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}