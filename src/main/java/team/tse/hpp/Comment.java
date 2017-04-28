package team.tse.hpp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TGHead on 2017/4/27.
 */

public class Comment extends Post {

    private int comment_replied_, post_commented_;

    public Comment(String ts, String comment_id, String user_id, String comment, String user, String comment_replied, String post_commented) {
        super(ts, comment_id, user_id, comment, user);
        this.comment_replied_ = Integer.parseInt(comment_replied);
        this.post_commented_ = Integer.parseInt(post_commented);
    }

    public int getComment_replied_() {
        return this.comment_replied_;
    }

    public int getPost_commented_() {
        return this.post_commented_;
    }
//
//    public boolean IsMyUser(Comment comment) {
//        if (comment.getUser_id_() == this.user_id_)
//            return true;
//        for (Comment c : liste_c) {
//            if (c.IsMyUser(comment))
//                return true;
//        }
//        return false;
//    }
}
