package team.tse.hpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileRead {
	ArrayList<Item> items=new ArrayList<Item>();
	public int postRead(BufferedReader reader,Post post){
		String string=null;
		String[] postString = null;
		try{
			if((string=reader.readLine())!=null){
				postString=string.split("\\|");
			}
			else{
				return -1;
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		post=new Post(postString[0], postString[1], postString[2], postString[3], postString[4]);
		return 1;
	}
	public int commentRead(BufferedReader reader,Comment comment){
		String string=null;
		String[] postString = null;
		try{
			if((string=reader.readLine())!=null){
				postString=string.split("\\|");
			}
			else{
				return -1;
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		comment=new Comment(postString[0], postString[1], postString[2], postString[3], postString[4], postString[5], postString[6]);
		return 1;
	}
	public void packList(){
		File filePost =new File("D:\\posts.dat");
		BufferedReader postReader = null;
		try {
			postReader = new BufferedReader(new FileReader(filePost));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File fileComment =new File("D:\\comments.dat");
		BufferedReader commentReader = null;
		try {
			commentReader = new BufferedReader(new FileReader(fileComment));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date timeflag;
		Post post = null;
		Comment comment = null;
		int flagPostProcess=postRead(postReader,post);
		int flagCommentProcess=commentRead(commentReader,comment);
		while(flagPostProcess>0||flagCommentProcess>0){
			if(flagPostProcess>0&&flagCommentProcess>0){
				if(post.getTs_().compareTo(comment.getTs_())>1){
					items.add(comment);
					flagCommentProcess=commentRead(commentReader,comment);
				}
				else{
					items.add(post);
					flagPostProcess=postRead(postReader,post);
				}
			}else if(flagPostProcess>0){
				items.add(post);
				flagPostProcess=postRead(postReader,post);
			}else{
				items.add(comment);
				flagCommentProcess=commentRead(commentReader,comment);
			}
		};
		
	}
	public static void main(String[] args){
			FileRead read=new FileRead();
			
			read.packList();
			System.out.println(read.items.get(0).getTs_());
			System.out.println(read.items.get(0).getTs_());
			System.out.println(read.items.get(0).getTs_());
	}
}
