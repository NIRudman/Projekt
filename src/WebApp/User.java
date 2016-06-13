package WebApp;

public class User extends UserComponent
{
	Object[] user;
	int id = 0;
	
	public User() {	}
	
	public User(int newItemType, Object[] item) 
	{
		this.userType.setType(newItemType);
		this.user = item;
		
		if(item[0].getClass().equals(Integer.class))
		{
			this.id = (int) item[0];
		}
	}
	
	public int getId()
	{
		return id;	                 
	}

	public void setId(int value)
    {
		id = value;
    }
	
    public void setUser(Object[] item)
    {
    	this.user = item;
    }
	
	public Object[] getUser()
	{	
		return user;
	}
	
	public int getUserType()
	{
		return userType.getType();
	}
	
	public void setUserType(int value) 
    {
		userType.setType(value);
    }
}


