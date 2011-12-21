<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${reportTitle} 报表注释</title>

    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/examples.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/icons/silk.css" />
    
    <!-- LIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/adapter/ext/ext-base.js"></script>
    <!-- ENDLIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/ext-all-debug.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/shared/extjs/App.js"></script>

    
    <script type="text/javascript">
    
    var p_readOnly = ${p_readOnly};
    var p_memoBy = '${userName}';
    
    
    var p_keyDate = Date.parseDate('${keyDate}', "Ymd");
    var p_keyValue = '${keyValue}';          
    var p_restful_url = 'restful/reportMemos/'+p_keyValue;
    var p_title = '报表备注';  
    var p_recordType = new Ext.data.Record.create([
 							{name: 'id'},
    						{name: 'keyDate', allowBlank: false, type:'date', dateFormat:'Y-m-d\TH:i:sP', defaultValue:p_keyDate},
    						{name: 'keyValue', allowBlank: false, type:'string', defaultValue:p_keyValue},
    						{name: 'memo', allowBlank: false, type:'string'},
    						{name: 'memoBy', allowBlank: false, type:'string', defaultValue:p_memoBy},
    						{name: 'memoTitle', allowBlank: false, type:'string'},
    						{name: 'memoAt', allowBlank: false, type:'date', dateFormat:'Y-m-d\TH:i:sP', defaultValue:new Date()}
    
    					]);      
    var p_dataRoot = 'reportMemo';
    var p_columns = [
          {id:'id',header: "id", width: 10, sortable: true, locked:false, dataIndex: 'id'},
          {header: "备注日期", width: 55, sortable: true, renderer:  Ext.util.Format.dateRenderer('Y/m/d'), dataIndex: 'memoAt'},
          {header: "备注人", width: 55, sortable: true, dataIndex: 'memoBy'},
          {header: "标题", width: 65, sortable: true, dataIndex: 'memoTitle'},
          {header: "关键日期", width: 80, sortable: true, renderer: Ext.util.Format.dateRenderer('Y/m/d'), dataIndex: 'keyDate'},
          {header: "关键值", width: 40, sortable: true, dataIndex: 'keyValue'}
	];
	
	var p_grid_row_editor = null;
	
	
	 function p_buildForm() {
        return [
                {
                	
                	layout:'column',
                	items: [{
                				columnWidth:.5, 
                				layout:'form',
                				items:[{
                					xtype:'datefield', 
                					fieldLabel: '备注日期', 
                					name:'memoAt',
                					readOnly:true,
                					anchor:'95%'
                	       			},
                	       			{
                	       			xtype:'datefield', 
                	       			fieldLabel: '关键日期', 
                	       			readOnly:true,
                	       			name:'keyDate',
                	       			anchor:'95%'
                	       			}
                	       		]
                			},
                			{
                				columnWidth:.5, 
                				layout:'form', 
                				items:[{
                						xtype:'textfield', 
                						fieldLabel: '备注人',
                						size: 20,  
                						readOnly:true,
                						name:'memoBy',
                						anchor:'95%'
                						},
                						{
                						xtype:'textfield', 
                						fieldLabel: '关键值',
                						readOnly:true,
                						name:'keyValue',
                						anchor:'95%'
                						}
      				
                				]
                			}
    
                		]

               },

                
               {
            	   layout:'form', 
            	   items:[{
            		   		xtype:'textfield',
            		   		fieldLabel:'标题', 
            		   		name: 'memoTitle',
            		   		readOnly:p_readOnly,   
            		   		anchor:'90%'
            	   		  },
            	   		  {
            	   			xtype:'htmleditor',
            	   			fieldLabel:'备注信息',
            	   			name: 'memo',
            	   			readOnly:p_readOnly,   
            	   			anchor:'90%'
            	   		  }
	       
            	  ]
            	  
              }           			

          ];
            

    } 
    </script>
    
    <script type="text/javascript" src="js/DataMaintain/writer.js"></script>
    <script type="text/javascript" src="js/DataMaintain/UserForm.js"></script>
    <script type="text/javascript" src="js/DataMaintain/UserGrid.js"></script>
    
</head>
<body>
<div id=client></div>
</body>
</html>