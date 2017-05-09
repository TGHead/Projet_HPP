package team.tse.hpp;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by TGHead on 2017/4/28.
 */

public class Main {

    private String postfilepath_;
    private String commentfilepath_;

    private List<Item> items_;
    private List<Post> listResult_;
    
    private BlockingQueue<Item> commentList_;

    private CommentReader commentReader_;
    private FileRead file_;
    private ResultList result_;

    public Main(String postfilepath, String commentfilepath) {
        this.postfilepath_ = postfilepath;
        this.commentfilepath_ = commentfilepath;

        this.items_ = new ArrayList<Item>();
        this.listResult_ = new LinkedList<Post>();

        this.file_ = new FileRead(this.items_, this.postfilepath_, this.commentfilepath_);
        
        this.commentList_=new ArrayBlockingQueue<Item>(100);
        this.commentReader_=new CommentReader(commentList_,this.commentfilepath_);
        
        this.result_ = new ResultList(this.listResult_);
        

        
    }

    private String showResult() {
        String sout = result_.getCurrentTime().toString(this.items_.get(0).getFormat_()) + ",";
        for (int i = 0; i < Math.min(3, this.listResult_.size()); i++) {
            sout += this.listResult_.get(i).getId_() + ","
                    + this.listResult_.get(i).getUser_() + ","
                    + this.listResult_.get(i).getSumScore() + ","
                    + this.listResult_.get(i).getCommenters_();
            if (i < 2) {
                sout += ",";
            }
        }
        for (int i = 0; i < 3 - this.listResult_.size(); i++) {
            sout += "-,-,-,-";
            if (i < 2 - this.listResult_.size()) {
                sout += ",";
            }
        }
        return sout;
    }

    public void run() {
        file_.packList();
        for (Item item : this.items_) {
            this.result_.consumeItem(item);
            if (this.result_.getListeChanged()) {
                System.out.println(showResult());
            }
        }
    }


    public ArrayList<String> fortest() {
        file_.packList();
        ArrayList<String> resLine = new ArrayList<String>();
        for (Item item : this.items_) {
            this.result_.consumeItem(item);
            if (this.result_.getListeChanged()) {
                resLine.add(showResult());
            }
        }
        return resLine;
    }
}
