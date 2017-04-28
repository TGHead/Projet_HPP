package team.tse.hpp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Post implements Item {

    protected int score_;// record the score of this post
    protected ArrayList<Comment> liste_c;
    private Date ts_;
    private int id_, user_id_;
    private String contenu_, user_;
    private int commenters_;// record the number of commenters (excluding the post author) for the post

    public Post(String ts, String post_id, String user_id, String post, String user) {
        this.ts_ = new Date(Long.parseLong(ts));
        this.id_ = Integer.parseInt(post_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.contenu_ = post;
        this.user_ = user;

        this.score_ = 10;
        this.commenters_ = 0;
        this.liste_c = new ArrayList<Comment>();
    }

    @Override
    public Date getTs_() {
        return ts_;
    }

    @Override
    public int getId_() {
        return id_;
    }

    @Override
    public int getUser_id_() {
        return user_id_;
    }

    @Override
    public String getContenu_() {
        return contenu_;
    }

    @Override
    public String getUser_() {
        return user_;
    }

    @Override
    public int getScore_() {
        return score_;
    }

    @Override
    public int getCommenters_() {
        return commenters_;
    }

    @Override
    public void scoreDecrement() {
        if (this.score_ > 0)
            this.score_--;
        for (Comment c : liste_c) {
            c.scoreDecrement();
        }
    }

    @Override
    public int getSumScore() {
        int sum = this.score_;
        for (Comment c : liste_c) {
            sum += c.getSumScore();
        }
        return sum;
    }

    @Override
    public boolean IsMyComment(Comment comment) {
        if (comment.getPost_commented_() == this.id_ || comment.getComment_replied_() == this.id_)
            return true;
        for (Comment c : liste_c) {
            if (c.IsMyComment(comment))
                return true;
        }
        return false;
    }

//    public void commentersUpdate(Comment comment) {
//        for (Comment c : liste_c) {
//            if (!c.IsMyUser(comment))
//                this.commenters_++;
//        }
//    }
}
