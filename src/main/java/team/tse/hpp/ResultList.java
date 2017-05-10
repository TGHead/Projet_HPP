package team.tse.hpp;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		for (int i = 0; i < this.listResult_.size(); i++) {

			Post p = this.listResult_.get(i);

			p.scoreDecrement(item.getTs_());

			//删去分为0的post
			if (p.getSumScore() == 0) {
				this.listResult_.remove(p);
				i--;
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
		AddPost(post);
	}

	public void consumeItem(Item item) {
		if (this.listResult_.size() == 0 || Days.daysBetween(this.currentTime_, item.getTs_()).getDays() > 0) {
			this.listeChanged_ = true;
		} else {
			this.listeChanged_ = false;
		}

		this.currentTime_ = item.getTs_();

		//itemUpdate(item);

		if (item.getClass() == Post.class)
			AddPost((Post) item);
		else {
			AddComment((Comment) item);
		}
	}

}
