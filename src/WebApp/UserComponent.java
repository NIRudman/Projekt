package WebApp;

public abstract class UserComponent 
{
	UserType userType = UserType.UNDEFINED;
	
	public void add(UserComponent newUserComponent)
	{
		throw new UnsupportedOperationException();
	}
	
	public void remove(UserComponent newUserComponent) 
	{
		throw new UnsupportedOperationException();
	}
	
	public void clear()
	{
		throw new UnsupportedOperationException();
	}
	
	public String getGroupName() 
	{
		throw new UnsupportedOperationException();
	}
	
	public Object[][] getTable()
	{
		throw new UnsupportedOperationException();
	}
	
	public UserComponent getComponent(int componentIndex) 
	{
		throw new UnsupportedOperationException();
	}

	public UserComponent getByIdComponent(int id2) 
	{
		throw new UnsupportedOperationException();
	}
	
	public int getId()
	{
		throw new UnsupportedOperationException();                
	}

	public void setId(int value)
    {
		throw new UnsupportedOperationException();
    }
	
    public void setUser(Object[] user)
    {
    	throw new UnsupportedOperationException();
    }
	
	public Object[] getUser()
	{	
		throw new UnsupportedOperationException();
	}
	
	public int getUserType()
	{
		throw new UnsupportedOperationException();
	}
	
	public void setUserType(int value) 
    {
		throw new UnsupportedOperationException();
    }
	
	public enum UserType
	{
		UNDEFINED(0), USER(1), SEARCH(2);  
		private int type;
		private UserType(int type)
		{
			this.type = type;
		}
		
		public void setType(int type)
		{
			this.type = type;
		}
		
		public int getType()
		{
			return type;
		}
	}
}
