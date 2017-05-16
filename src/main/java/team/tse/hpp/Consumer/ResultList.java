package team.tse.hpp.Consumer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import team.tse.hpp.data_structure.Comment;
import team.tse.hpp.data_structure.Item;
import team.tse.hpp.data_structure.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;

public class ResultList implements Runnable{

	private List<Post> listResult_;
	private boolean listeChanged_;
	private BlockingQueue<Item> itemsQueue_;
	private DateTime currentTime_;
	private Map<Long,Post> postMap_;
	private Map<Long,Comment> commentMap_;
	ArrayList<String> resLine ;
	public ResultList(List<Post> listResult,BlockingQueue<Item> itemsQueue,ArrayList<String> resLine)
	{
		this.listResult_ = listResult;
		this.listeChanged_ = false;
		this.itemsQueue_=itemsQueue;
		this.currentTime_ = null;
		this.postMap_=new HashMap<Long,Post>();
		this.commentMap_=new HashMap<Long,Comment>();
		this.resLine=resLine;
	}

	@Override
	public void run() {
		Item item = null;
		try {
			item = itemsQueue_.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (item.getId_() != -1) {
			consumeItem(item);
			if (getListeChanged()) {
				resLine.add(showResult(item.getFormat_()));
			}
			try {
				item = itemsQueue_.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
    private String showResult(DateTimeFormatter formatter) {
        String sout = this.getCurrentTime().toString(formatter) + ",";
        for (int i = 0; i < Math.min(3, this.listResult_.size()); i++) {
            sout += this.listResult_.get(i).getId_() + ","
                    + this.listResult_.get(i).getUser_() + ","
                    + this.listResult_.get(i).getSumScore() + ","
                    + this.listResult_.get(i).getCommenters_();
            if (i < 2) {
                sout += ",";
            }
        }
        for (int i = 0; i < 3 - this.listResult_.size(); i++) {
            sout += "-,-,-,-";
            if (i < 2 - this.listResult_.size()) {
                sout += ",";
            }
        }
        System.out.println(sout);
        return sout;
    }
	public DateTime getCurrentTime() {
		return this.currentTime_;
	}

	public boolean getListeChanged() {
		return this.listeChanged_;
	}

	private void itemUpdate(Item item) {

		List<Post> newResult=new LinkedList<Post>();
//		for (Entry<Integer,Post> entry : postMap_.entrySet())
		Iterator<Entry<Long, Post>> it = postMap_.entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, Post> entry = it.next();
			Post p =entry.getValue();
			p.scoreDecrement(item.getTs_(),item.getUser_id_());
			if(p.getSumScore() == 0 || (p.getSumScore() == 10 && p.getScore_() == 0)) {
//				this.postMap_.remove(entry.getKey());
				for(int i=0;i<p.getCommentSize();i++){
					commentMap_.remove(p.getCommentId(i));
				}
				it.remove();
			}
			else
			{
				//listeChanged_=listResult_.removeIf(e->e.getSumScore()==0);
				if(newResult.isEmpty()){
					newResult.add(p);
					//listeChanged_=true;
				}
				
				else for(int i=0;i<newResult.size();i++)
				{
					if(newResult.get(i).getSumScore()<p.getSumScore())
					{
						newResult.add(i, p);
						//listeChanged_=true;
						if(newResult.size()>3)
							newResult=newResult.subList(0, 3);
						break;
					}else if(newResult.get(i).getSumScore()==p.getSumScore()){
						if(newResult.get(i).getTs_().compareTo(p.getTs_())<0){
							newResult.add(i, p);
							//listeChanged_=true;
							if(newResult.size()>3)
								newResult=newResult.subList(0, 3);
							break;
						}
					}
					else if(newResult.size()<3&&i==newResult.size()-1){
						newResult.add(newResult.size(),p);
						break;
					}
				}

			}

		}
		if (newResult.equals(listResult_)) {
			listeChanged_ = false;
		} else {
			listeChanged_ = true;
			listResult_.clear();
			for (Post p : newResult) {
				listResult_.add(p);
			}
		}
	}

	private void AddPost(Post post) {
		postMap_.put(post.getId_(), post);
	}

	private void AddComment(Comment comment) {
		commentMap_.put(comment.getId_(),comment);
		Comment flag=comment;
		while(flag.getPost_commented_()==-1){
			flag=commentMap_.get(flag.getComment_replied_());
			if(flag==null)
				return;
		}
		Post post=postMap_.get(flag.getPost_commented_());
		if(post!=null){
			post.AddComment(comment);
		}
	}

	public void consumeItem(Item item) {

		this.currentTime_ = item.getTs_();
		if (item.getClass() == Post.class)
			AddPost((Post) item);
		else{
			AddComment((Comment) item);
		}
		
		itemUpdate(item);
	}

}
