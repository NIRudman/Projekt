package WebApp;
import java.util.ArrayList;
import java.util.Iterator;

public class UserGroup extends UserComponent {

	ArrayList<UserComponent> userComponents = new ArrayList<UserComponent>();

	String groupName;

	public UserGroup(String newGroupName, int newGroupType) {
		groupName = newGroupName;
		this.userType.setType(newGroupType);
	}

	public void add(UserComponent newuserComponent) 
	{
		userComponents.add(newuserComponent);
	}

	public void remove(UserComponent newuserComponent) 
	{
		userComponents.remove(newuserComponent);
	}
	
	public void clear() 
	{
		userComponents.clear();
	}

	public String getGroupName() 
	{
		return groupName;
	}
	
	public UserComponent getComponent(int componentIndex) 
	{
		return (UserComponent) userComponents.get(componentIndex);
	}
	
	public UserComponent getByIdComponent(int id) 
	{
		Iterator<UserComponent> userIterator = userComponents.iterator();
		
		while (userIterator.hasNext()) 
		{
			UserComponent userInfo = (UserComponent) userIterator.next();
			
			if(userInfo.getId() == id)
				return userInfo;
		}
		return null;
	}
	
	public Object[][] getTable()
	{
		if(userComponents.size() > 0)
		{
			Object[][] data = new Object[userComponents.size()][getComponent(0).getUser().length];
			
			Iterator<UserComponent> userIterator = userComponents.iterator();
			
			int i = 0;
			
			while (userIterator.hasNext()) 
			{
				UserComponent userInfo = (UserComponent) userIterator.next();
				
				data[i] = userInfo.getUser();
				
				i++;
			}
			return data;
		}
		else
			return null;
	}

}