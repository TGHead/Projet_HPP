package team.tse.hpp.data_structure;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import team.tse.hpp.data_structure.Comment;

/**
 * Created by TGHead on 2017/4/28.
 */

public interface Item {

    DateTime getTs_();

    DateTimeFormatter getFormat_();

    int getId_();

    int getUser_id_();

    String getContenu_();

    String getUser_();

    int getScore_();

    int getCommenters_();

    void CommentersIncrement(Comment comment);

    int getSumScore();

    boolean AddComment(Comment comment);

}
