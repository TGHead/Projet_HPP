package team.tse.hpp;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class ResultList {
	public List<Post> listResult;
	private List<Post> listRankResult;
	
	public ResultList()
	{
		listResult=new LinkedList<Post>();
		listRankResult=new LinkedList<Post>();
	}
	
	public List<Post> consumeItem(Item item)
	{
		
		DateTime dateMoment=item.getTs_();

		for(Post p :listResult )
		{
			DateTime dateOld=p.getTs_();
			int numDate=Days.daysBetween(dateMoment, dateOld).getDays();
			while(numDate<=0)
			{
				p.scoreDecrement();//进行扣分
				//删去分为0的post
				if(p.getScore_()<=0)
				{
					listResult.remove(p);
					break;
				}
					
				numDate--;
			} 
			if(item.getClass()==Post.class)
				listResult.add((Post) item);
			else
			{
				if(p.AddComment((Comment)item))
				break;
			}
		}
		
		listResult.sort((o1,o2)->((Post)o1).getScore_()-((Post)o2).getScore_());
		if(nochangeRank())
			return null;
		else{
			for(int i=0;i<3;i++)
			{
				Post a=listResult.get(i);
				if(a!=null)
					listRankResult.add(a);
				else
					break;
			}
			return listRankResult;
		}
		
	}
		
	private boolean nochangeRank()
	  { 	
	  		for(int i=0;i<3;i++)
	  		{
	  			Post a=listResult.get(i);
	  			Post b=listRankResult.get(i);
	  			if(a==null&&b==null)
	  				return true;
	  			else if(a==null ||b==null)
	  				return false;
	  			else if(a.getScore_()!=b.getScore_())
	  				return false;
	  		}
	  		return true;
	  
	  }
}
