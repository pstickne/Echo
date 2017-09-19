package com.pstickney.echo.core.tokens.literal;

import java.util.HashMap;
import java.util.Map;

public class ObjectLiteral extends Literal 
{
	public static final ObjectLiteral NULL = new ObjectLiteral(null);
	
	private Map<String, Object> value = null;
	
	public ObjectLiteral() 
	{
		super(T_OBJECT);
		value = new HashMap<String, Object>();
	}
	
	public ObjectLiteral(Map<String, Object> map)
	{
		super(T_OBJECT);
		value = map;
	}
	
	public Object get(String key)
	{
		return has(key) ? value.get(key) : null;
	}
	
	public Object put(String key, Object value)
	{
		return this.value.put(key, value);
	}
	
	public void value(Map<String, Object> map)
	{
		value = map;
	}
	
	@Override
	public Map<String, Object> value()
	{
		return value;
	}
	
	public Boolean has(String key)
	{
		return value == null ? false : value.containsKey(key);
	}

	@Override
	public String toString() 
	{
		return value == null ? "null" : value.toString();
	}
}
