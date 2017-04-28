package team.tse.hpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {

	ArrayList<Item> items=new ArrayList<Item>();

    private boolean flagPostProcess_;
    private boolean flagCommentProcess_;

    public FileRead() {
        this.flagPostProcess_ = false;
        this.flagCommentProcess_ = false;
    }

    public static void main(String[] args) {
        FileRead read = new FileRead();
        
        File fileComment =new File("D:\\comments.dat");
		BufferedReader commentReader = null;

        try {
			commentReader = new BufferedReader(new FileReader(fileComment));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        Comment comment=read.commentRead(commentReader);
        int i=0;
        while(read.flagCommentProcess_==true){
        	read.items.add(comment);
        	System.out.println(read.items.get(i).getTs_());
        	comment=read.commentRead(commentReader);
        	i++;
        }
        
    }

    public Post postRead(BufferedReader reader) {
        String string;
        String[] postString;
        Post post = null;
        try{
            if ((string = reader.readLine()) != null) {
                postString=string.split("\\|");
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

    public Comment commentRead(BufferedReader reader) {
        String string;
        String[] postString;
        Comment comment = null;
        try{
			if((string=reader.readLine())!=null){
				postString=string.split("\\|",-1);
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

        File filePost =new File("D:\\posts.dat");
		BufferedReader postReader = null;

        try {
			postReader = new BufferedReader(new FileReader(filePost));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        File fileComment =new File("D:\\comments.dat");
		BufferedReader commentReader = null;

        try {
			commentReader = new BufferedReader(new FileReader(fileComment));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        Post post = null;
		Comment comment = null;
		comment = commentRead(commentReader);
		post = postRead(postReader);
        while (flagPostProcess_ == true || flagCommentProcess_ == true) {
            if (flagPostProcess_ == true && flagCommentProcess_ == true) {
                if (post.getTs_().compareTo(comment.getTs_()) > 1) {
                    items.add(comment);
                    comment = commentRead(commentReader);
                }
				else{
					items.add(post);
                    post = postRead(postReader);
                }
            } else if (flagPostProcess_ == true) {
                items.add(post);
                post = postRead(postReader);
            } else {
                items.add(comment);
                comment = commentRead(commentReader);
            }
		};

    }
}
