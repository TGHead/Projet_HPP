package team.tse.hpp.producer;

import team.tse.hpp.data_structure.Item;
import team.tse.hpp.data_structure.Post;

import java.util.concurrent.BlockingQueue;

/**
 * Created by TGHead on 2017/5/9.
 */

public class Integrator implements Runnable {
    private boolean flagPostProcess_;
    private boolean flagCommentProcess_;

    private BlockingQueue<Item> q_items_;
    private BlockingQueue<Item> q_posts_;
    private BlockingQueue<Item> q_comments_;

    public Integrator(BlockingQueue<Item> q_items, BlockingQueue<Item> q_posts, BlockingQueue<Item> q_comments) {
        this.flagPostProcess_ = false;
        this.flagCommentProcess_ = false;

        this.q_items_ = q_items;
        this.q_posts_ = q_posts;
        this.q_comments_ = q_comments;
    }

    public void Integrate() {
        Item post = null;
        try {
            post = q_posts_.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Item comment = null;
        try {
            comment = q_comments_.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (post.getId_() != -1 || comment.getId_() != -1) {

            if (post.getId_() != -1 && comment.getId_() != -1) {
                if (post.getTs_().compareTo(comment.getTs_()) > 0) {
                    try {
                        q_items_.put(comment);
                        comment = q_comments_.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        q_items_.put(post);
                        post = q_posts_.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            } else if (post.getId_() != -1) {
                try {
                    q_items_.put(post);
                    post = q_posts_.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    q_items_.put(comment);
                    comment = q_comments_.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Item itemEnd=new Post("2010-02-01T05:12:32.921+0000","-1","-1","","");
        try {
			q_items_.put(itemEnd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void run() {
        Integrate();
    }
}
