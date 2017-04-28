package team.tse.hpp;

/**
 * Created by TGHead on 2017/4/28.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestFileRead {

    private final String postfilepath = "src/test/Test resources/Tests/Q1BigTest/posts.dat";
    private final String commentfilepath = "src/test/Test resources/Tests/Q1BigTest/comments.dat";
    FileRead fr;
    private ArrayList<Item> items;

    @Before
    public void setup() {
        items = new ArrayList<Item>();
        fr = new FileRead(items, postfilepath, commentfilepath);
        fr.packList();
    }

    @Test
    public void test() {
        //TODO: FileRead Class test
//        fail("write test!");
        for (Item item : items) {
            System.out.println(item);
        }
    }

}