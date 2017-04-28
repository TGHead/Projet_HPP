package team.tse.hpp;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface Item {

    Date getTs_();

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
