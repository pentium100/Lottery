package com.itg.dao;
import java.util.List;


public interface IMenuItemDAO {

	public abstract void insertMenuItem(MenuItem menuItem);
    public abstract MenuItem selectMenuItemByID(Integer ID);
    public abstract List<MenuItem> selectMenuItem(MenuItem parentMenuItem);
    public abstract List<MenuItem> selectMenuItem(String userName, MenuItem parentMenuItem);

    

}
