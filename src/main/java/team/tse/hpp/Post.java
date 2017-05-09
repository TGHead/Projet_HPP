package team.tse.hpp;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Post implements Item {

    DateTimeFormatter format_;
    private ArrayList<Comment> liste_c;
    private DateTime ts_;
    private int id_;
    private int user_id_;
    private String contenu_;
    private String user_;
    private int lifeDays_;
    private int score_;// record the score of this post
    private int commenters_;// record the number of commenters (excluding the post author) for the post

    public Post(String ts, String post_id, String user_id, String post, String user) {
        /*format timestamp*/
        this.format_ = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.ts_ = DateTime.parse(ts, this.format_).withZone(DateTimeZone.UTC);
//        DateTimeZone correctTimeZone = this.ts_.getZone();
//        this.ts_ = this.ts_.withZoneRetainFields(correctTimeZone);

        this.id_ = Integer.parseInt(post_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.contenu_ = post;
        this.user_ = user;

        this.score_ = 10;
        this.lifeDays_ = 0;
        this.commenters_ = 0;
        this.liste_c = new ArrayList<Comment>();
    }

    @Override
    public String toString() {
        return ts_.toString(getFormat_()) +
                "|" + id_ +
                "|" + user_id_ +
                "|" + contenu_ +
                "|" + user_;
    }

    @Override
    public DateTime getTs_() {
        return ts_;
    }

    @Override
    public DateTimeFormatter getFormat_() {
        return format_;
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
    public int getLifeDays() {
        return lifeDays_;
    }

    private void setLifeDays(int days) {
        this.lifeDays_ = days;
    }

    @Override
    public int getCommenters_() {
        return commenters_;
    }

    @Override
    public void CommentersIncrement(Comment comment) {
        for (Comment c : liste_c) {
            if (comment.getUser_id_() == c.getUser_id_()) {
                return;
            }
        }
        this.commenters_++;
    }

    @Override
    public void scoreDecrement(DateTime cur_time) {
        int numDate = Days.daysBetween(getTs_(), cur_time).getDays() - getLifeDays();
        while (numDate > 0) {
            if (this.score_ > 0) {
                this.score_--;
            }
            numDate--;
        }
        setLifeDays(Days.daysBetween(getTs_(), cur_time).getDays());
        for (Comment c : liste_c) {
            c.scoreDecrement(cur_time);
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
            CommentersIncrement(comment);
            liste_c.add(comment);
            return true;
        }
        for (Comment c : liste_c) {
            if (c.AddComment(comment)) {
                c.CommentersIncrement(comment);
                return true;
            }
        }
        return false;
    }
}
