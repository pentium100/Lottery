package com.itg.web.ctl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;

import com.itg.dao.IMenuItemDAO;

import com.itg.dao.MenuItem;

@Controller
public class MenuController {
	private String viewName;


	private IMenuItemDAO menuItemDAO;

    

//在ascweb-servlet.xml里配置的,该属性已经被注入userDAOProxy接口了

    public IMenuItemDAO getMenuItemDAO() {

         return menuItemDAO;

    }

    public void setMenuItemDAO(IMenuItemDAO menuItemDAO) {

         this.menuItemDAO = menuItemDAO;

    }

    public String getViewName() {

         return viewName;

    }

    public void setViewName(String viewName) {

         this.viewName = viewName;

    }

    

//注解其url映射

    @SuppressWarnings("unchecked")
    @RequestMapping("/getMenu.do") 
    public String index(ModelMap map,HttpServletRequest request,HttpServletResponse response) {

         // map是用来设置View层数据的
     	 MenuItem parent = menuItemDAO.selectMenuItemByID(Integer.parseInt(request.getParameter("node")));
     	 
     	 

         List<MenuItem> l = menuItemDAO.selectMenuItem(parent);
         
         
         ArrayList al = new ArrayList();
         
         for(int i=0; i<l.size();i++){
        	 Map m = new HashMap();
        	 
        	 m.put("text", l.get(i).getMenuText());
        	 m.put("leaf", l.get(i).isLeaf());
        	 
        	 if (l.get(i).isLeaf()){
        		 m.put("cls", "file");
        	 }
        	 else
        	 {
        		 m.put("cls", "folder");
        	 }
        	 
        	 m.put("id", l.get(i).getID());
        	 m.put("url", l.get(i).getUrl());

        	 al.add(m);
        	 
        	 
        	 
         }
         
         JSONArray json =  JSONArray.fromObject(al);
         map.put("menuList", json);
         

         return this.viewName;  //该属性被注入值hello了,就是渲染视图hello.jsp

    }


}
