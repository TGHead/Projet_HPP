package team.tse.hpp;

import java.util.LinkedList;
import java.util.List;

public class ResultList {
	public List<Post> listResult;
	private List<Post> listRankResult;
	
	public ResultList()
	{
		listResult=new LinkedList<Post>();
	}
	
	/*public LinkedList consumeItem(Item item)
	{
		
		Date dateMoment=item.getDate();

		for(Post p :listResult )
		{
			Date dateOld=p.getDate();
			int numDate=dateMoment-dateOld;
			while(numDate<=0)
			{
				p.reduire();//进行扣分
				//删去分为0的post
				if(p.getScore<=0)
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
				if(p.addComment((Comment)item))
				break;
			}
		}
		
		listResult.sort();
		if(nochangeRank())
			return null
		else{
		
			listRankResult=listResult.subList(0,4);
			return listRankResult
		}
		
	}*/
		
	/*private boolean nochangeRank()
	 * { 	
	 * 		for(int i=0;i<3;i++)
	 * 		{
	 * 			Post a=listResult.get(i);
	 * 			Post b=listRankResult.get(i);
	 * 			if(a.postId!=b.postId)
	 * 				return false;
	 * 		}
	 * 		return true;
	 * 
	 * }
	 * */
}
