package team.tse.hpp;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Post implements Item {

    private int score_;// record the score of this post
    private ArrayList<Comment> liste_c;

    private DateTime ts_;
    private int id_;
    private int user_id_;
    private String contenu_;
    private String user_;
    private int commenters_;// record the number of commenters (excluding the post author) for the post

    public Post(String ts, String post_id, String user_id, String post, String user) {
        /*format timestamp*/
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        DateTime dateTime = DateTime.parse("2012-12-21 23:22:45", format);
        this.ts_ = DateTime.parse(ts, format);
        this.id_ = Integer.parseInt(post_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.contenu_ = post;
        this.user_ = user;

        this.score_ = 10;
        this.commenters_ = 0;
        this.liste_c = new ArrayList<Comment>();
    }

    @Override
    public DateTime getTs_() {
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
    public void CommentersIncrement() {
        this.commenters_++;
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
    public boolean AddComment(Comment comment) {
        if (comment.getPost_commented_() == this.id_ || comment.getComment_replied_() == this.id_) {
            liste_c.add(comment);
            CommentersIncrement();
            return true;
        }
        for (Comment c : liste_c) {
            if (c.AddComment(comment)) {
                c.CommentersIncrement();
                return true;
            }
        }
        return false;
    }
}
