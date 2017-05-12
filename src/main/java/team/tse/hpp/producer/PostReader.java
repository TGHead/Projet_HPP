package team.tse.hpp.producer;

import team.tse.hpp.data_structure.Item;
import team.tse.hpp.data_structure.Post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class PostReader implements Runnable{
	
	private BlockingQueue<Item> queuePost;
	private boolean flagPostProcess_;
	private String postfilepath_;
	public PostReader(BlockingQueue<Item> queuePost,String postfilepath){
		this.queuePost=queuePost;
		this.postfilepath_=postfilepath;
	}
	public void run(){
		File filePost = new File(postfilepath_);
        BufferedReader postReader = null;

        try {
			postReader = new BufferedReader(new FileReader(filePost));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
		}
        Post post = postRead(postReader);
        while (flagPostProcess_ == true) {
            try{
        	queuePost.put(post);
            }catch(InterruptedException e) {
				e.printStackTrace();
			}
            post = postRead(postReader);
        }
        try {
            postReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Post endRead=new Post("2010-02-01T05:12:32.921+0000","-1","3981","photo299101.jpg","Michael Wang");
        
        try {
			queuePost.put(endRead);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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

}
