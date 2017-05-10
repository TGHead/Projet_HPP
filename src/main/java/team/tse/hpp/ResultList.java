package team.tse.hpp;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResultList {

	private List<Post> listResult_;
	private boolean listeChanged_;

	private DateTime currentTime_;
	private Map<Integer,Post> postMap_;
	private Map<Integer,Comment> commentMap_;

	public ResultList(List<Post> listResult)
	{
		this.listResult_ = listResult;
		this.listeChanged_ = false;

		this.currentTime_ = null;
		this.postMap_=new HashMap<Integer,Post>();
		this.commentMap_=new HashMap<Integer,Comment>();
	}

	public DateTime getCurrentTime() {
		return this.currentTime_;
	}

	public boolean getListeChanged() {
		return this.listeChanged_;
	}

	private void itemUpdate(Item item) {
//		for (int i = 0; i < this.listResult_.size(); i++) {
//
//			Post p = this.listResult_.get(i);
//
//			p.scoreDecrement(item.getTs_());
//
//			//删去分为0的post
//			if (p.getSumScore() == 0) {
//				this.listResult_.remove(p);
//				i--;
//			}
//		}
		List<Post> newResult=new LinkedList<Post>();
		for (Entry<Integer,Post> entry : postMap_.entrySet()) 
		{
			Post p =entry.getValue();
			p.scoreDecrement(item.getTs_());
			if(p.getSumScore()==0)
				this.postMap_.remove(entry.getKey());
			else
			{
				//listeChanged_=listResult_.removeIf(e->e.getSumScore()==0);
				if(newResult.size()==0){
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
		if(newResult.equals(listResult_)){
			listeChanged_=false;
		}else{
			listeChanged_=true;
			listResult_.clear();
			for(Post p:newResult){
				listResult_.add(p);
			}
			}
	}

	private void AddPost(Post post) {
//		int idx = this.listResult_.size();
//		for (Post p : this.listResult_) {
//			if (p.getSumScore() <= post.getSumScore()) {
//				idx = this.listResult_.indexOf(p);
//				break;
//			}
//		}
//		if (idx < 3 && oldIdx != idx) {
//			this.listeChanged_ = true;
//		}
//		this.listResult_.add(idx, post);
		postMap_.put(post.getId_(), post);
	}

	private void AddComment(Comment comment) {
//		for (Post p : this.listResult_) {
//			if (p.AddComment(comment)) {
//				int idx = this.listResult_.indexOf(p);
//				this.listResult_.remove(p);
//				AddPost(p, idx);
//				break;
//			}
//		}
		commentMap_.put(comment.getId_(),comment);
		Comment flag=comment;
		while(flag.getPost_commented_()==-1){
			flag=commentMap_.get(flag.getComment_replied_());
		}
		Post post=postMap_.get(flag.getPost_commented_());
		post.AddComment(flag);
	}

	public void consumeItem(Item item) {
//		if (this.listResult_.size() == 0 || Days.daysBetween(this.currentTime_, item.getTs_()).getDays() > 0) {
//			this.listeChanged_ = true;
//		} else {
//			this.listeChanged_ = false;
//		}

		this.currentTime_ = item.getTs_();



		if (item.getClass() == Post.class)
			AddPost((Post) item);
		else {
			AddComment((Comment) item);
		}
		
		itemUpdate(item);
	}

}
