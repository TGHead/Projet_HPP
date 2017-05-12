package team.tse.hpp.data_structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    protected ArrayList<Comment> liste_c;
    protected DateTime ts_;
    protected int id_;
    protected int user_id_;
    protected String contenu_;
    protected String user_;
    protected int score_;// record the score of this post

    protected HashMap<Integer, Integer> commentersIndex_;


    public Post(String ts, String post_id, String user_id, String post, String user) {
        /*format timestamp*/
        this.format_ = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.ts_ = DateTime.parse(ts, this.format_).withZone(DateTimeZone.UTC);

        this.id_ = Integer.parseInt(post_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.contenu_ = post;
        this.user_ = user;

        this.score_ = 10;
        this.liste_c = new ArrayList<Comment>();

        this.commentersIndex_ = new HashMap<Integer, Integer>();
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
    public int getCommenters_() {
//        return commenters_;
        return commentersIndex_.size();
    }

    @Override
    public void CommentersIncrement(Comment comment) {
        if (this.commentersIndex_.containsKey(comment.getUser_id_())) {
            commentersIndex_.put(comment.getUser_id_(), commentersIndex_.get(comment.getUser_id_()) + 1);
        } else {
            commentersIndex_.put(comment.getUser_id_(), 1);
        }
    }

    public void scoreDecrement(DateTime cur_time, int user_id) {
        int numDate = Days.daysBetween(getTs_(), cur_time).getDays();
        List<Integer> user_id_list = new ArrayList<Integer>();
        if (this.score_ > 0) {
            this.score_ = 10 - numDate;
            if (this.score_ < 0) {
                this.score_ = 0;
            }
        }
//        setLifeDays(Days.daysBetween(getTs_(), cur_time).getDays() - numDate);
        for (Comment c : liste_c) {
            if (c.scoreDecrement(cur_time, user_id, user_id_list)) {
                for (int zero_score_user_id : user_id_list) {
                    commentersIndex_.put(zero_score_user_id, commentersIndex_.get(zero_score_user_id) - 1);
                    if (commentersIndex_.get(zero_score_user_id) == 0)
                        commentersIndex_.remove(zero_score_user_id);
                }
                user_id_list.clear();
            }
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
//        if (comment.getPost_commented_() == this.id_ || comment.getComment_replied_() == this.id_) {
            CommentersIncrement(comment);
            liste_c.add(comment);
            return true;
//        }
//        for (Comment c : liste_c) {
//            if (c.AddComment(comment)) {
////                c.CommentersIncrement(comment);
//                return true;
//            }
//        }
//        return false;
    }
}
