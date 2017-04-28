package team.tse.hpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {

	ArrayList<Item> items=new ArrayList<Item>();

    public static void main(String[] args) {
        FileRead read = new FileRead();

        read.packList();
        System.out.println(read.items.get(0).getTs_());
        System.out.println(read.items.get(0).getTs_());
        System.out.println(read.items.get(0).getTs_());
    }

    public boolean postRead(BufferedReader reader, Post post) {
        String string;
        String[] postString = null;
		try{
            if ((string = reader.readLine()) != null) {
                postString=string.split("\\|");
                post = new Post(postString[0], postString[1], postString[2], postString[3], postString[4]);
            }
			else{
                return false;
            }

		}catch(IOException e){
			e.printStackTrace();
		}
        return true;
    }

    public boolean commentRead(BufferedReader reader, Comment comment) {
        String string=null;
		String[] postString = null;
		try{
			if((string=reader.readLine())!=null){
				postString=string.split("\\|");
                comment = new Comment(postString[0], postString[1], postString[2], postString[3],
                        postString[4], postString[5], postString[6]);
            }
			else{
                return false;
            }

		}catch(IOException e){
			e.printStackTrace();
		}
        return true;
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

        boolean flagPostProcess = postRead(postReader, post);
        boolean flagCommentProcess = commentRead(commentReader, comment);

        while (flagPostProcess == true || flagCommentProcess == true) {
            if (flagPostProcess == true && flagCommentProcess == true) {
                if(post.getTs_().compareTo(comment.getTs_())>1){
					items.add(comment);
					flagCommentProcess=commentRead(commentReader,comment);
				}
				else{
					items.add(post);
					flagPostProcess=postRead(postReader,post);
				}
            } else if (flagPostProcess == true) {
                items.add(post);
				flagPostProcess=postRead(postReader,post);
            } else {
                items.add(comment);
				flagCommentProcess=commentRead(commentReader,comment);
			}
		};

    }
}
