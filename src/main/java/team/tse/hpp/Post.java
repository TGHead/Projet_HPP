package team.tse.hpp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Post {

    private Date ts_;
    private int post_id_, user_id_;
    private String post_, user_;

    private int score_;// record the score of this post
    private int commenters_;// record the number of commenters (excluding the post author) for the post

    private ArrayList<Comment> liste_c;

    public Post(String ts, String post_id, String user_id, String post, String user) {
        this.ts_ = new Date(Long.parseLong(ts));
        this.post_id_ = Integer.parseInt(post_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.post_ = post;
        this.user_ = user;

        this.score_ = 10;
        this.commenters_ = 0;

        this.liste_c = new ArrayList<Comment>();
    }

    public Date getTs_() {
        return ts_;
    }

    public int getPost_id_() {
        return post_id_;
    }

    public String getUser_() {
        return user_;
    }

    public int getScore_() {
        return score_;
    }

    public int getCommenters_() {
        return commenters_;
    }

    public void scoreDecrement() {
        if (this.score_ > 0)
            this.score_--;
        for (Comment c : liste_c) {
            c.scoreDecrement();
        }
    }

    public int getSumScore() {
        int sum = this.score_;
        for (Comment c : liste_c) {
            sum += c.getSumScore();
        }
        return sum;
    }

    public void commentersUpdate(Comment comment) {
        for (Comment c : liste_c) {
            if (!c.IsMyUser(comment))
                this.commenters_++;
        }
    }
}
