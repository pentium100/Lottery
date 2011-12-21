<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${reportTitle} 报表注释</title>

    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/examples.css" />
    <!-- LIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/adapter/ext/ext-base.js"></script>
    <!-- ENDLIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/ext-all-debug.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/shared/extjs/App.js"></script>
    
    <script type="text/javascript">
    Ext.onReady(function(){
    
        // NOTE: This is an example showing simple state management. During development,
        // it is generally best to disable state management as dynamically-generated ids
        // can change across page loads, leading to unpredictable results.  The developer
        // should ensure that stable state ids are set for stateful components in real apps.
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        p_readOnly = ${p_readOnly};
        
	  	Ext.QuickTips.init();

    	// turn on validation errors beside the field globally
    	Ext.form.Field.prototype.msgTarget = 'side';

    	var bd = Ext.getBody();
    	
    	var App = new Ext.App({});

		// Create a standard HttpProxy instance.
		var proxy = new Ext.data.HttpProxy({
    	        url: 'resource/reportMemos'
    		
		});
		var writer = new Ext.data.JsonWriter({
    				encode: true,   
    				writeAllFields: false
		});
		var reader = new Ext.data.JsonReader({
        		    	totalProperty: 'total',
    					successProperty: 'success',
    					idProperty: 'id',
    					root: 'data'
	       		　　}, [
            		{name: 'memoAt', type: 'date', dateFormat:'n/j h:ia', allowBlank:false},
            		{name: 'id', type:'int', allowBlank:false},
            		{name: 'memoBy', allowBlank:false},
            		{name: 'memoTitle', allowBlank:false},
            		{name: 'keyDate', type: 'date', allowBlank:false},
            		{name: 'keyValue', allowBlank:false}

        		]);
        		 	
    		var ds = new Ext.data.Store({
    		         reader: reader,
    		         writer: writer,
    		         id: 'reportMemoDS',
    		         autoSave: true,
    		         restful: true, 
    				 proxy: proxy,
    				 baseParams:{keyValue:escape('{$keyValue}')}, 
    		         listeners: {
        					write : function(store, action, result, response, rs) {
            					App.setAlert(response.success, response.message);
        					},
        					exception : function(proxy, type, action, options, res, arg) {
            					if (type === 'remote') {
                					Ext.Msg.show({
                    					title: 'REMOTE EXCEPTION',
                    					msg: res.message,
                    					icon: Ext.MessageBox.ERROR,
                    					buttons: Ext.Msg.OK
                					});
            					}
        					}
        					
    				 }
    		         
        		
    		});
    		
    		
		    ds.load();


    	// the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    	// custom or reusable ColumnModels
    	var colModel = new Ext.grid.ColumnModel([
        	//{id:'id',header: "id", width: 160, sortable: true, locked:false, dataIndex: 'company'},
        	{header: "备注日期", width: 55, sortable: true, renderer: Ext.util.Format.usMoney, dataIndex: 'memoAt'},
        	{header: "备注人", width: 55, sortable: true, dataIndex: 'memoBy'},
        	{header: "主题", width: 65, sortable: true, dataIndex: 'memoTitle'},
        	{header: "关键日期", width: 80, sortable: true, renderer: Ext.util.Format.dateRenderer('Y/m/d'), dataIndex: 'keyDate'},
        	{header: "关键值", width: 40, sortable: true, dataIndex: 'keyValue'}
    	]);

    	bd.createChild({tag: 'h2', html: 'Using a Grid with a Form'});
        
        
        var viewport = new Ext.Viewport({
                    layout: 'border',
                    defaults:{
                    
                             split:true
                            },
            		items: [
            		// create instance immediately
            			{
            			region:'north', 
            			xtype:'grid',
            			layout:'fit',
		                ds: ds,
		                id: 'reportMemo_grid', 
        		        cm: colModel,
		                sm: new Ext.grid.RowSelectionModel({
        		        	singleSelect: true,
                			listeners: {
                        		rowselect: function(sm, row, rec) {
                            	  Ext.getCmp("reportMemo-form").getForm().loadRecord(rec);
                        			}
                    			}
                			}),
                		//autoExpandColumn: 'company',
                		height: 100,
                		title:'注释清单',
                		border: true,
                		tbar: [{
				        		text: 'Add',
		            			iconCls: 'silk-add',
            					handler: onAdd
        					   }, '-', {
            					text: 'Delete',
            					iconCls: 'silk-delete',
            					handler: onDelete
        				}, '-'],
                		
                		listeners: {
                    		render: function(g) {
                        			   g.getSelectionModel().selectRow(0);
                    				},
                            rowclick: function(g, index, ev) {
             						    var rec = g.store.getAt(index);
                						reportMemo_form.loadRecord(rec);
            						},
            				destroy : function() {
                						userForm.getForm().reset();
            						},
                    		delay: 10 // Allow rows to be rendered.
                			}
                			
            		
            			},{
            			 
            			region : 'center',
            			xtype:'form',
            			name:'reportMemo_form',
            			id:'reportMemo_form',
            			frame:true,
            			title: '报表注释编辑区',
            			//autoWidth: true, 
            			//monitorResize : true,
            			record:null,   
            			listeners:{
            			  resize : function(t, adjWidth, adjHeight,  rawWidth, rawHeight ){
            			    t.doLayout();
            			  
            			      
            			  }
            			
            			},
            			
          			    loadRecord : function(rec) {
        								this.record = rec;
        								this.getForm().loadRecord(rec);
    					},
            			items:[{
            			
            			        layout:'column',
            			        
            			                    
            			        
            			        
            			        items: [
            			                {columnWidth:.5, 
            			                layout:'form',
            			                 
            			                items:[
            			          				{
            			          		  		xtype:'datefield', 
            			          		  		fieldLabel: '创建日期', 
            			          		  		name:'memoAt',
            			          		  		readOnly:p_readOnly,
            			          		  		
            			          		  		anchor:'95%'
            			          				},
            			          				{
            			          		  		xtype:'datefield', 
            			          		  		fieldLabel: '报表日期', 
            			          		  		readOnly:p_readOnly,
            			          		  		name:'keyDate',
            			          		  		
            			          		  		anchor:'95%'
            			          				}
            			          				
            			          			  ]
            			          		} ,
            			          		
            			                {columnWidth:.5, 
            			                layout:'form', 
            			                items:[
            			          				{
            			          		  		xtype:'textfield', 
            			          		  		fieldLabel: '创建人',
            			          		  		size: 20,  
            			          		  		readOnly:p_readOnly,
            			          		  		 
            			          		  		name:'memoBy',
            			          		  		anchor:'95%'
            			          				},
            			          				{
            			          		  		xtype:'textfield', 
            			          		  		fieldLabel: '关健值',
            			          		  		readOnly:p_readOnly,
            			          		  		  
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
            					        fieldLabel:'主题', 
            					        name: 'memoTitle',
            					        readOnly:p_readOnly,   
            					        
            					        anchor:'90%'
            					       },
            					       {
            					        xtype:'htmleditor',
            					        fieldLabel:'注释内容',
            					         
            					        name: 'memo',
            					        readOnly:p_readOnly,   
            					        anchor:'90%'
            					       }
            					       
            					       ]
            					
            					
            					
            					}           			
            			
            				],
						buttons: [{
            					text: 'Save'
        						},{
            					text: 'Cancel'
					      	  }]            				
            				
            			}], 
    	             renderTo: 'client'
    	             });
    	             
    	             
    	             
    	             
        });
        
    	                 /**
     * onAdd
     */
    function onAdd(btn, ev) {
        var u = new reportMemo_grid.store.recordType({
               memoAt : new Date(),
                 //id : null, 

               memoBy : ${userName}, 
            memoTitle : '', 
              keyDate : ${keyDate},
            keyValue  : ${keyValue}          
            
        });
        Ext.getCmp("reportMemo_form").getForm().loadRecord(u);
    }
    /**
     * onDelete
     */
    function onDelete() {
        var rec = Ext.getCmp('reportMemo_grid').getSelectionModel().getSelected();
        if (!rec) {
            return false;
        }
        Ext.getCmp('reportMemo_grid').store.remove(rec);
    }
    	             
        
    </script>

</head>
<body>
<div id=client></div>

</body>
</html>