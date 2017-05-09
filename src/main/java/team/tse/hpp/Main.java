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

//    private String postfilepath_;
//    private String commentfilepath_;

    //    private List<Item> items_;
    private List<Post> listResult_;

    private BlockingQueue<Item> postQueue_;
    private BlockingQueue<Item> commentQueue_;
    private BlockingQueue<Item> itemsQueue_;

    private PostReader postReader_;
    private CommentReader commentReader_;
    private Integrator integrator_;
    //    private FileRead file_;
    private ResultList result_;

    public Main(String postfilepath, String commentfilepath) {
//        this.postfilepath_ = postfilepath;
//        this.commentfilepath_ = commentfilepath;

        this.postQueue_ = new ArrayBlockingQueue<Item>(100);
        this.commentQueue_ = new ArrayBlockingQueue<Item>(100);
        this.itemsQueue_ = new ArrayBlockingQueue<Item>(100);
        this.listResult_ = new LinkedList<Post>();

//        this.file_ = new FileRead(this.itemsQueue_, this.postfilepath_, this.commentfilepath_);

        this.postReader_ = new PostReader(postQueue_, postfilepath);
        this.commentReader_ = new CommentReader(commentQueue_, commentfilepath);
        this.integrator_ = new Integrator(itemsQueue_, postQueue_, commentQueue_);
        
        this.result_ = new ResultList(this.listResult_);

    }

    private String showResult() {
        String sout = result_.getCurrentTime().toString(this.itemsQueue_.poll().getFormat_()) + ",";
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

//    public void run() {
//        file_.packList();
//        for (Item item : this.itemsQueue_) {
//            this.result_.consumeItem(item);
//            if (this.result_.getListeChanged()) {
//                System.out.println(showResult());
//            }
//        }
//    }


    public ArrayList<String> fortest() {
//        file_.packList();
        Thread th_post = new Thread(postReader_);
        Thread th_comment = new Thread(commentReader_);
        Thread th_integrator = new Thread(integrator_);

        th_post.setName("PostReader");
        th_comment.setName("CommentReader");
        th_integrator.setName("Integrator");

        th_post.start();
        th_comment.start();
        th_integrator.start();

        try {
            th_post.join();
            th_comment.join();
            th_integrator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ArrayList<String> resLine = new ArrayList<String>();
        for (Item item : this.itemsQueue_) {
            this.result_.consumeItem(item);
            if (this.result_.getListeChanged()) {
                resLine.add(showResult());
            }
        }
        return resLine;
    }
}
