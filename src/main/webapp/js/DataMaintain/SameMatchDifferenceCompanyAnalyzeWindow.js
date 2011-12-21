
var matchProxy = new Ext.data.HttpProxy({
	url: '../restful/matches'
});

// Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
var matchReader = new Ext.data.JsonReader({
    totalProperty: 'total',
    successProperty: 'success',
    idProperty: 'id',
    root: 'matches'
	}, p_matchRecordType    
);


var matchStore = new Ext.data.Store({
    id: 'matchStore',
    proxy: matchProxy,
    reader: matchReader,
    restful: true, 
    remoteSort:true,
    
    sortInfo: {
    field: 'company, date desc , homeTeam ',
    direction: 'ASC' 
    	
}

});




Ext.SMDCResultGrid=Ext.extend(Ext.grid.GridPanel, {
	title:"同场次其他公司盘口",
	region:"center",
	width:100,
	height:461,
	loadMask: true,
	
	plugins:[], 
    //filters: new Ext.ux.grid.GridFilters({
        // encode and local configuration options defined previously for easier reuse
    //    encode: false, // json encode the filter query
    //    local: false   // defaults to false (remote filtering)
    //    , filters:p_matchFilterConfig 

    //}), 
	buildToolbar: function(grid){
	
	toolbar =  new Ext.Toolbar({
		
		items:[
				{
					xtype:"button",
					text:"显示初盘",
					enableToggle: true,
				    toggleHandler: function(btn, pressed) {
				        
				        cm = grid.getColumnModel();
				        for(i=0;i<cm.getColumnCount(false);i++){
				            if(cm.getDataIndex(i).indexOf('early')>=0){
				        		cm.setHidden(i, !pressed); 
				        	}
				        }
			            
		            }
					

				}]
		
	});
	return toolbar;

	      
	},  
	initComponent: function(){
	this.ds = matchStore;	
	this.bbar = this.buildToolbar(this);
	//this.plugins[this.plugins.length] = this.filters;
	
	this.columns=[

	            	{id:'id',header: "id", width: 60, fixed:true, sortable: true, locked:true, dataIndex: 'id', hidden:true}
	                ,{id:'date',header: "日期", width: 80, sortable: true,  dataIndex: 'date', renderer:Ext.util.Format.dateRenderer('m-d H:i') }
	                ,{id:'country',header: "国家", width: 50, sortable: true,  storeKey:'country', dataIndex: 'country', renderer: renderProperty}           
	                ,{id:'company',header: "公司", width: 50, sortable: true,  storeKey:'company', dataIndex: 'company', renderer: renderProperty}
	                ,{id:'championship',header: "赛事", width: 100, sortable: true,  storeKey:'championship', dataIndex: 'championship', renderer: renderProperty}
	                ,{id:'level',header: "级别", width: 40, sortable: true,  dataIndex: 'level', hidden:true}
	                ,{id:'isInHome',header: "主场", width: 40, sortable: true,  dataIndex: 'isInHome', hidden: true}
	                ,{id:'homeTeam',dataIndex:'homeTeam',header: "主队", width: 100, sortable: true, renderer: renderTeamName}
	                ,{id:'awayTeam',dataIndex:'awayTeam',header: "客队", width: 100, sortable: true, renderer: renderTeamName}           
	                ,{id:'rankDiff',dataIndex:'rankDiff',header: "排名差", width: 50, sortable: true}          

	                ,{id:'score',header: "比分", width: 50, sortable: true,  dataIndex: 'score',  css:'border-right:1px  solid  #FF0000;'}


	                ,{id:'euro_final_win',header: "欧赔_胜", width: 50, sortable: true,  dataIndex: 'euro_final_win', renderer:renderResult}
	                ,{id:'euro_final_standoff',header: "欧赔_平", width: 50, sortable: true,  dataIndex: 'euro_final_standoff', renderer:renderResult}           
	                ,{id:'euro_final_loss',header: "欧赔_负", width: 50, sortable: true,  dataIndex: 'euro_final_loss', renderer:renderResult, css:'border-right:1px  solid  #FF0000;'}

	                
	                ,{id:'asia_final_up',dataIndex:'asia_final_up', header: "亚赔_上", width: 50, sortable: true, renderer:renderResult}
	                ,{id:'asia_final_ud_mouth',dataIndex:'asia_final_ud_mouth',header: "亚赔_盘口", width: 50, sortable: true, storeKey:'asia_ud_mouth', renderer: renderProperty}           
	                ,{id:'asia_final_down',dataIndex:'asia_final_down',header: "亚赔_下", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;'}

	                
	                ,{id:'euro_early_win',dataIndex:'euro_early_win', header: "欧初_胜", width: 50, sortable: true, renderer:renderResult, hidden: true}
	                ,{id:'euro_early_standoff',dataIndex:'euro_early_standoff',header: "欧初_平", width: 50, sortable: true, renderer:renderResult, hidden: true}           
	                ,{id:'euro_early_loss',dataIndex:'euro_early_loss',header: "欧初_负", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;', hidden: true}           
	                

	                ,{id:'asia_early_up',dataIndex:'asia_early_up',header: "亚初_上", width: 50, sortable: true, renderer:renderResult, hidden: false}
	                ,{id:'asia_early_ud_mouth',dataIndex:'asia_early_ud_mouth',header: "亚初_盘口", width: 50, sortable: true, storeKey:'asia_ud_mouth', renderer: renderProperty, hidden: false}           
	                ,{id:'asia_early_down',dataIndex:'asia_early_down', header: "亚初_下", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;', hidden: false}           

	                ,{id:'offerTime',header: "初盘时间", width: 80, sortable: true,  dataIndex: 'offerTime', filter:{type:'date'}, renderer:Ext.util.Format.dateRenderer('m-d H:i'), css:'border-right:1px  solid  #FF0000;'}
	                
	                
	                ,{id:'asia_final_big',dataIndex:'asia_final_big',header: "大小_大", width: 50, sortable: true, renderer:renderResult}
	                ,{id:'asia_final_bs_mouth',dataIndex:'asia_final_bs_mouth',header: "大小_盘口", width: 50, sortable: true, storeKey:'asia_bs_mouth', renderer: renderProperty}           
	                ,{id:'asia_final_small',dataIndex:'asia_final_small', header: "大小_小", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;'}

	                ,{id:'asia_early_big',dataIndex:'asia_early_big',header: "大小初_大", width: 50, sortable: true, renderer:renderResult, hidden: true}
	                ,{id:'asia_early_bs_mouth',dataIndex:'asia_early_bs_mouth',header: "大小初_盘口", width: 50, sortable: true, storeKey:'asia_bs_mouth', renderer: renderProperty,  hidden: true}           
	                ,{id:'asia_early_small',dataIndex:'asia_early_small',header: "大小初_小", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;', hidden: true}           

	                ,{id:'win_standoff_loss',dataIndex:'win_standoff_loss',header: "胜负平", width: 50, sortable: true}
	                ,{id:'up_down',dataIndex:'up_down',header: "上下", width: 50, sortable: true }            
	                ,{id:'big_small',dataIndex:'big_small',header: "大小", width: 50, sortable: true}           
	               

	];
	Ext.SMDCResultGrid.superclass.initComponent.call(this);
	
	}
});


function showHistoryAnalyze(g, rowIndex, e ){
    
	var rec = g.store.getAt(rowIndex);
	
	var queryWindow = new Ext.HistoryAnalyzeWindow({
		listeners: {
		    show: function(t){
		     	//userGrid.disable();
		    },
			close: function(p){
			 	//userGrid.enable();
			    
			}
		
		}
	
	
	
	
	});
	Ext.getCmp("query_company").value = rec.data.company;
	Ext.getCmp("query_championship").value = rec.data.championship;
	Ext.getCmp("query_country").value = rec.data.country;
	Ext.getCmp("query_euro_final_win").value = rec.data.euro_final_win;
	Ext.getCmp("query_euro_final_standoff").value = rec.data.euro_final_standoff;
	Ext.getCmp("query_euro_final_loss").value = rec.data.euro_final_loss;
	
	Ext.getCmp("query_asia_final_bs_mouth").value = rec.data.asia_final_bs_mouth;
	Ext.getCmp("query_asia_final_ud_mouth").value = rec.data.asia_final_ud_mouth;
	
	
	queryWindow.show();
    
    

   
}



Ext.SMDCAnalyzeWindow=Ext.extend(Ext.Window ,{
	title:"同场次其他公司盘口",
	width:1150,
	height:574,
	layout:"border",
	//sMDCGrid : , 
	initComponent: function(){
	    matchStore.removeAll();
	    thisWindow = this;
		this.items=[
			new Ext.SMDCResultGrid({
					
					listeners: {
						rowdblclick: showHistoryAnalyze
	        		}
	        
			})
		];
		Ext.SMDCAnalyzeWindow.superclass.initComponent.call(this);
		
	}
});