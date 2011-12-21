
var historyProxy = new Ext.data.HttpProxy({
	url: 'getAnalyzeResult3.do'
});

var historyRecordType =new Ext.data.Record.create([
 							 {name: 'company', allowBlank: true, type:'int', storeKey:'company'}
 							,{name: 'championship', allowBlank: true, type:'int', storeKey:'championship'}
 							,{name: 'country', allowBlank: true, type:'int', storeKey:'country'}
 							,{name: 'totalMatches', allowBlank: true, type:'int'}
 							,{name: 'totalUbMatches', allowBlank: true, type:'int'}
 							
 							
 							,{name: 'winCount', allowBlank: true, type:'int'}
 							,{name: 'standoffCount', allowBlank: true, type:'int'}
 							,{name: 'lossCount', allowBlank: true, type:'int'}
 							
 							,{name: 'winPercent', allowBlank: true, type:'float'}
 							,{name: 'standoffPercent', allowBlank: true, type:'float'}
 							,{name: 'lossPercent', allowBlank: true, type:'float'}

 							
 							,{name: 'upCount', allowBlank: true, type:'int'}
 							,{name: 'udStandoffCount', allowBlank: true, type:'int'}
 							,{name: 'downCount', allowBlank: true, type:'int'}

 							,{name: 'upPercent', allowBlank: true, type:'float'}
 							,{name: 'udStandoffPercent', allowBlank: true, type:'float'}
 							,{name: 'downPercent', allowBlank: true, type:'float'}
 							
 							,{name: 'bigCount', allowBlank: true, type:'int'}
 							,{name: 'bsStandoffCount', allowBlank: true, type:'int'}
 							,{name: 'smallCount', allowBlank: true, type:'int'}

 							,{name: 'bigPercent', allowBlank: true, type:'float'}
 							,{name: 'bsStandoffPercent', allowBlank: true, type:'float'}
 							,{name: 'smallPercent', allowBlank: true, type:'float'}
 							
 						]); 

// Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
var historyReader = new Ext.data.JsonReader({
    totalProperty: 'total',
    successProperty: 'success',
    idProperty: 'id',
    root: ''
	}, historyRecordType    
);


var historyStore = new Ext.data.Store({
    id: 'historyStore',
    proxy: historyProxy,
    reader: historyReader,
    restful: true, 
    remoteSort:true
});


Ext.ResultGrid=Ext.extend(Ext.grid.GridPanel, {
	title:"统计信息",
	region:"center",
	width:100,
	height:461,
	loadMask: true,
	initComponent: function(){
	this.ds = historyStore;	
	this.columns=[
		{
			xtype:"gridcolumn",
			header:"公司",
			renderer: renderProperty, 
			sortable:true,
			resizable:true,
			width:100,
			dataIndex:"company",
			fixed:true,
   			storeKey:"company",
   		    hidden: true
		},
		{
			xtype:"gridcolumn",
			header:"赛事",
			renderer: renderProperty, 
			sortable:true,
			resizable:true,
			width:100,
			dataIndex:"championship",
   			storeKey:"championship"
		},

   		{
   			xtype:"gridcolumn",
   			header:"国家",
   			renderer: renderProperty, 
   			sortable:true,
   			resizable:true,
   			width:100,
   			dataIndex:"country",
   			storeKey:"country"
   		},
	              

		
		{
			xtype:"numbercolumn",
			header:"总场次",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"totalMatches",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"胜",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"winCount",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"平",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"standoffCount",
			align:"right"
		},
		
		{
			xtype:"numbercolumn",
			header:"负",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"lossCount",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"胜比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"winPercent",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"平比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"standoffPercent",
			align:"right"
		},
		
		{
			xtype:"numbercolumn",
			header:"负比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"lossPercent",
			align:"right"
		},

		{
			xtype:"numbercolumn",
			header:"上下盘总场次",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"totalUbMatches",
			align:"right"
		},
		
		{
			xtype:"numbercolumn",
			header:"上盘",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"upCount",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"上下平盘",
			sortable:true,
			resizable:true,
			width:40,
			format:"0,000",
			dataIndex:"udStandoffCount",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"下盘",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000",
			dataIndex:"downCount",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"上盘比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"upPercent",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"上下平盘比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"udStandoffPercent",
			align:"right"
		},
		{
			xtype:"numbercolumn",
			header:"下盘比",
			sortable:true,
			resizable:true,
			width:45,
			format:"0,000.00%",
			dataIndex:"downPercent",
			align:"right"
		},

		{
			xtype:"numbercolumn",
			header:"大小大",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000",
			dataIndex:"bigCount",
			align:"right"
			,hidden: true
		},
		{
			xtype:"numbercolumn",
			header:"大小平",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000",
			dataIndex:"bsStandoffCount",
			align:"right"
			,hidden: true
		},
		{
			xtype:"numbercolumn",
			header:"大小小",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000",
			dataIndex:"smallCount",
			align:"right"
			,hidden: true	
		},
		{
			xtype:"numbercolumn",
			header:"大比",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000.00%",
			dataIndex:"bigPercent",
			align:"right"
			,hidden: true	
		},
		{
			xtype:"numbercolumn",
			header:"大小平比",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000.00%",
			dataIndex:"bsStandoffPercent",
			align:"right"
			,hidden: true
		},
		{
			xtype:"numbercolumn",
			header:"小比",
			sortable:true,
			resizable:true,
			width:30,
			format:"0,000.00%",
			dataIndex:"smallPercent",
			align:"right"
			,hidden: true
		}
	]
	Ext.ResultGrid.superclass.initComponent.call(this);
	
	}
});

Ext.HistoryAnalyzeWindow=Ext.extend(Ext.Window ,{
	title:"历史统计信息",
	width:825,
	height:574,
	layout:"border",
	initComponent: function(){
	    historyStore.removeAll();
	    thisWindow = this;
		this.tbar={
			xtype:"toolbar",
			items:[
				{
					xtype:"button",
					text:"查询",
					iconCls:"silk-zoom",
					handler: function(){
					
					    historyStore.setBaseParam('company', Ext.getCmp('query_company').value);
					    historyStore.setBaseParam('championship', Ext.getCmp('query_championship').value);
					    historyStore.setBaseParam('country', Ext.getCmp('query_country').value);
					    historyStore.setBaseParam('euro_final_win', Ext.getCmp('query_euro_final_win').value);
					    historyStore.setBaseParam('euro_final_standoff', Ext.getCmp('query_euro_final_standoff').value);
					    historyStore.setBaseParam('euro_final_loss', Ext.getCmp('query_euro_final_loss').value);
					    historyStore.setBaseParam('asia_final_bs_mouth', Ext.getCmp('query_asia_final_bs_mouth').value);
					    historyStore.setBaseParam('asia_final_ud_mouth', Ext.getCmp('query_asia_final_ud_mouth').value);
					    
					    historyStore.load();
					    
					    
					
					}
				},
				{
					xtype:"button",
					text:"关闭",
					iconCls:"silk-cancel",
					
					handler: function(b,e){
					
					   
					   thisWindow.close();
					
					
					}
				}
			]
		}
		this.items=[
			{
				xtype:"container",
				autoEl:"div",
				layout:"hbox",
				region:"north",
				height:93,
				labelPad:"",
				labelWidth:100,
				margins:"",
				split:false,
				layoutConfig:{
					align:"stretch"
				},
				items:[
					{
						xtype:"container",
						autoEl:"div",
						layout:"form",
						flex:1,
						labelAlign:"right",
						margins:"10",
						hideBorders:false,
						items:[
							{
								xtype:"combo",
								fieldLabel:"公司",
								store:getPropertyStore('company'),
								triggerAction:"all",
								allowBlank:false,
								anchor:"100%",
								valueField:'id',
								displayField:'text', 
								id:"query_company"
							},
							{
								xtype:"numberfield",
								fieldLabel:"欧赔_胜",
								anchor:"100%",
								decimalPrecision:3,
								id:"query_euro_final_win"
							},
							{
								xtype:"combo",
								fieldLabel:"亚赔",
								anchor:"100%",
								triggerAction:"all",
								id:"query_asia_final_ud_mouth",
								store:getPropertyStore('asia_ud_mouth'),
								valueField:'id',
								displayField:'text'
							}
						]
					},
					{
						xtype:"container",
						autoEl:"div",
						layout:"form",
						flex:1,
						labelAlign:"right",
						margins:"10",
						items:[
							{
								xtype:"combo",
								fieldLabel:"赛事",
								anchor:"100%",
								triggerAction:"all",
								id:"query_championship",
								store:getPropertyStore('championship'),
								valueField:'id',
								displayField:'text'
								
							},
							{
								xtype:"numberfield",
								fieldLabel:"欧赔_平",
								anchor:"100%",
								decimalPrecision:3,
								id:"query_euro_final_standoff"
								
							},
							{
								xtype:"combo",
								fieldLabel:"大小",
								anchor:"100%",
								id:"query_asia_final_bs_mouth",
								triggerAction:"all",
								store:getPropertyStore('asia_bs_mouth'),
								valueField:'id',
								displayField:'text'
								
							}
						]
					},
					{
						xtype:"container",
						autoEl:"div",
						layout:"form",
						flex:1,
						height:94,
						labelAlign:"right",
						margins:"10",
						items:[
							{
								xtype:"combo",
								fieldLabel:"国家",
								anchor:"100%",
								id:"query_country",
								triggerAction:"all",
								store:getPropertyStore('country'),
								valueField:'id',
								displayField:'text'

							},
							{
								xtype:"numberfield",
								fieldLabel:"欧赔_负",
								anchor:"100%",
								decimalPrecision:3,
								id:"query_euro_final_loss"
							}
						]
					}
				]
			},
			new Ext.ResultGrid()
		]
		Ext.HistoryAnalyzeWindow.superclass.initComponent.call(this);
	}
});