package team.tse.hpp;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface Item {

    DateTime getTs_();

    int getId_();

    int getUser_id_();

    String getContenu_();

    String getUser_();

    int getScore_();

    int getCommenters_();

    void CommentersIncrement();

    void scoreDecrement();

    int getSumScore();

    boolean AddComment(Comment comment);

}
