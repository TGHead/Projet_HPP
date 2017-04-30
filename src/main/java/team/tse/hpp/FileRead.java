package team.tse.hpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileRead {

    private boolean flagPostProcess_;
    private boolean flagCommentProcess_;
    private String postfilepath_;
    private String commentfilepath_;

    private List<Item> items_;

    public FileRead(List items, String postfilepath, String commentfilepath) {
        this.flagPostProcess_ = false;
        this.flagCommentProcess_ = false;

        this.postfilepath_ = postfilepath;
        this.commentfilepath_ = commentfilepath;

        this.items_ = items;
    }

    private Post postRead(BufferedReader reader) {
        String string;
        String[] postString;
        Post post = null;
        try{
            if ((string = reader.readLine()) != null && !string.isEmpty()) {
                postString = string.split("\\|", -1);
                post = new Post(postString[0], postString[1], postString[2], postString[3], postString[4]);
                this.flagPostProcess_ = true;
            }
			else{
                this.flagPostProcess_ = false;
                return null;
            }

		}catch(IOException e){
			e.printStackTrace();
		}
        return post;
    }

    private Comment commentRead(BufferedReader reader) {
        String string;
        String[] postString;
        Comment comment = null;
        try{
            if ((string = reader.readLine()) != null && !string.isEmpty()) {
                postString = string.split("\\|", -1);
                comment = new Comment(postString[0], postString[1], postString[2], postString[3],
                        postString[4], postString[5], postString[6]);
                this.flagCommentProcess_ = true;
            }
			else{
                this.flagCommentProcess_ = false;
                return null;
            }

		}catch(IOException e){
			e.printStackTrace();
		}
        return comment;
    }

    public void packList(){

        File filePost = new File(postfilepath_);
        BufferedReader postReader = null;

        try {
			postReader = new BufferedReader(new FileReader(filePost));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
		}

        File fileComment = new File(commentfilepath_);
        BufferedReader commentReader = null;

        try {
			commentReader = new BufferedReader(new FileReader(fileComment));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        Post post = postRead(postReader);
        Comment comment = commentRead(commentReader);

        while (flagPostProcess_ == true || flagCommentProcess_ == true) {
            if (flagPostProcess_ == true && flagCommentProcess_ == true) {
                if (post.getTs_().compareTo(comment.getTs_()) > 0) {
                    items_.add(comment);
                    comment = commentRead(commentReader);
                }
				else{
                    items_.add(post);
                    post = postRead(postReader);
                }
            } else if (flagPostProcess_ == true) {
                items_.add(post);
                post = postRead(postReader);
            } else {
                items_.add(comment);
                comment = commentRead(commentReader);
            }
        }

        try {
            postReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            commentReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
