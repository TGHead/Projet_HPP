package team.tse.hpp;

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
        Item post = q_posts_.poll();
        Item comment = q_comments_.poll();
        while (post != null || comment != null) {
            if (post != null && comment != null) {
                if (post.getTs_().compareTo(comment.getTs_()) > 0) {
                    try {
                        q_items_.put(comment);
                        comment = q_comments_.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        q_items_.put(post);
                        post = q_posts_.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            } else if (post != null) {
                try {
                    q_items_.put(post);
                    post = q_posts_.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    q_items_.put(comment);
                    comment = q_comments_.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        Integrate();
    }
}
