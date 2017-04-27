package team.tse.hpp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Comment {

    private Date ts_;
    private int comment_id_, user_id_;
    private String comment_, user_;
    private int comment_replied_, post_commented_;

    private int score_;// record the score of this comment

    private ArrayList<Comment> liste_c;

    public Comment(String ts, String comment_id, String user_id, String comment, String user, String comment_replied, String post_commented) {
        this.ts_ = new Date(Long.parseLong(ts));
        this.comment_id_ = Integer.parseInt(comment_id);
        this.user_id_ = Integer.parseInt(user_id);
        this.comment_ = comment;
        this.user_ = user;
        this.comment_replied_ = Integer.parseInt(comment_replied);
        this.post_commented_ = Integer.parseInt(post_commented);

        this.score_ = 10;

        this.liste_c = new ArrayList<Comment>();
    }

    public Date getTs_() {
        return this.ts_;
    }

    public int getComment_id_() {
        return this.comment_id_;
    }

    public int getUser_id_() {
        return this.user_id_;
    }

    public String getComment_() {
        return comment_;
    }

    public String getUser_() {
        return user_;
    }

    public int getComment_replied_() {
        return this.comment_replied_;
    }

    public int getPost_commented_() {
        return this.post_commented_;
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

    public boolean IsMyUser(Comment comment) {
        if (comment.getUser_id_() == this.user_id_)
            return true;
        for (Comment c : liste_c) {
            if (c.IsMyUser(comment))
                return true;
        }
        return false;
    }
}
