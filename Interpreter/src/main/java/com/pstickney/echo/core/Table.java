package com.pstickney.echo.core;

import java.util.HashMap;
import java.util.Map;

public class Table<RowType, ColType, DataType>
{
	private Map<RowType, Map<ColType, DataType>> map;
	
	public Table()
	{
		map = new HashMap<RowType, Map<ColType, DataType>>();
	}
	
	public DataType get(RowType row, ColType col)
	{
		if( map.containsKey(row) )
			return map.get(row).get(col);
		
		return null;
	}
	
	public DataType put(RowType row, ColType col, DataType data)
	{
		Map<ColType, DataType> rowMap;
		
		if( map.containsKey(row) ) {
			rowMap = map.get(row);
		} else {
			rowMap = new HashMap<ColType, DataType>();
			map.put(row, rowMap);
		}
		
		return rowMap.put(col, data);
	}
	
	public boolean containsKeys(RowType row, ColType col)
	{
		return map.containsKey(row) && map.get(row).containsKey(col);
	}
	
	public void clear()
	{
		map.clear();
	}
}
