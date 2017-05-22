package team.tse.hpp.data_structure;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Comment extends Post {

    private long comment_replied_;
    private long post_commented_;

    public Comment(String ts, String comment_id, String user_id, String comment, String user, String comment_replied, String post_commented) {
        super(ts, comment_id, user_id, comment, user);
        if (!comment_replied.isEmpty()) {
            this.comment_replied_ = Long.parseLong(comment_replied);
        } else {
            this.comment_replied_ = -1;
        }

        if (!post_commented.isEmpty()) {
            this.post_commented_ = Long.parseLong(post_commented);
        } else {
            this.post_commented_ = -1;
        }
    }

    @Override
    public String toString() {
        return getTs_().toString(getFormat_()) +
                "|" + getId_() +
                "|" + getUser_id_() +
                "|" + getContenu_() +
                "|" + getUser_() +
                "|" + ((getComment_replied_() == -1) ? "" : getComment_replied_()) +
                "|" + ((getPost_commented_() == -1) ? "" : getPost_commented_());
    }

    public long getComment_replied_() {
        return this.comment_replied_;
    }

    public long getPost_commented_() {
        return this.post_commented_;
    }

    public boolean commentscoreDecrement(DateTime cur_time) {
        int numDate = Days.daysBetween(getTs_(), cur_time).getDays();
        boolean score_zero = false;

//        for (Comment c : liste_c) {
//            if (c.scoreDecrement(cur_time, user_id_list)) {
//                for (long zero_score_user_id : user_id_list) {
//                    commentersIndex_.put(zero_score_user_id, commentersIndex_.get(zero_score_user_id) - 1);
//                    if (commentersIndex_.get(zero_score_user_id) == 0)
//                        commentersIndex_.remove(zero_score_user_id);
//                }
//                score_zero = true;
//            }
//        }

        if (getScore_() > 0) {
            this.score_ = 10 - numDate;
            if (this.score_ <= 0) {
                this.score_ = 0;
//                user_id_list.add(getUser_id_());
                score_zero = true;
            }
        }
        return score_zero;
//        setLifeDays(Days.daysBetween(getTs_(), cur_time).getDays() - numDate);
    }

}
