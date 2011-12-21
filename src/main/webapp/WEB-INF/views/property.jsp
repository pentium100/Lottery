<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}基础数据维护 </title>

    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/examples.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/icons/silk.css" />
    
    <!-- LIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/adapter/ext/ext-base.js"></script>
    <!-- ENDLIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/ext-all-debug.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/shared/extjs/App.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/ux/RowEditor.js"></script>

    <link rel="stylesheet" type="text/css" href="restful.css" />
    <link rel="stylesheet" href="js/ext-3.0.0/examples/ux/css/RowEditor.css" />


    
    <script type="text/javascript">
    var p_grid_title = "${title}";
    var p_readOnly = false;
    var p_propertyType_id = ${propertyType_id};
    var p_propertyType_text = "${propertyType_text}";
    var p_restful_url = 'restful/properties/'+p_propertyType_id;
    var p_recordType = new Ext.data.Record.create([
 							{name: 'id'},
    						{name: 'text', allowBlank: false, type:'string'},
    						{name: 'propertyType_id', allowBlank: false, type:'int', mapping:"propertyType_id", defaultValue:p_propertyType_id}
    						//,{name: 'propertyType_text', allowBlank: false, type:'string', mapping:"propertyType_text"}
    
    					]);      
    var p_dataRoot = 'properties';
    var p_columns = [
          {id:'id',header: "id", width: 5, sortable: true, locked:true, dataIndex: 'id', editor: new Ext.form.NumberField({})},
          {header: p_propertyType_text, width: 55, sortable: true, dataIndex: 'text', editor: new Ext.form.TextField({})},
          {header: "参数类型", width: 55, sortable: true, dataIndex: 'propertyType_id', renderer: renderPropertyTypeText}
	];
	
    function renderPropertyTypeText(val){
    
    
      switch(val){
      	case 1:
      		return "赛事";
      		break;
      	case 2:
      		return "队伍";
      		break;
      	case 3:
      		return "亚赔盘口";
      		break;
      	case 4:
      	  	return "大小盘口";
      	  	break;
      	case 5:
      		return "公司";
      		break;
      		 
          
      }
      
 
    }
    </script>
    
    <script type="text/javascript" src="js/DataMaintain/restful.js"></script>
    
</head>
<body>
<div id=client></div>
</body>
</html>