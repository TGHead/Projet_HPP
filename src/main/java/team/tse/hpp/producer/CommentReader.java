package team.tse.hpp.producer;

import team.tse.hpp.data_structure.Comment;
import team.tse.hpp.data_structure.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class CommentReader implements Runnable {

	
	private BlockingQueue<Item> commentList_;
	private String commentfilepath_;
	private boolean flagCommentProcess_;
	
	public CommentReader(BlockingQueue<Item> commentList, String commentfilepath_)
	{
		this.commentList_=commentList;
		this.commentfilepath_=commentfilepath_;
        this.flagCommentProcess_ = false;
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
    
	
	
	@Override
	public void run() {
        File fileComment = new File(commentfilepath_);
        BufferedReader commentReader = null;

        try {
			commentReader = new BufferedReader(new FileReader(fileComment));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        Comment comment = commentRead(commentReader);
        while(this.flagCommentProcess_)
        {
	        try {
				commentList_.put(comment);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			comment = commentRead(commentReader);

		}
		try {
			commentReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
            commentList_.put(new Comment("2010-02-01T05:12:32.921+0000", "-1", "-1", " ", " ", "-1", "-1"));
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        
			
		}
	}

}
