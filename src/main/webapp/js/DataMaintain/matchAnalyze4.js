MyPanelUi = Ext.extend(Ext.form.FormPanel, {
    title: '查询条件',
    height: 140,
    layout: 'hbox',
    id: 'condition',
    collapsible:true,
    onReset: function(){
	
	Ext.getCmp("company1").clearValue();
	Ext.getCmp("company2").clearValue();
	Ext.getCmp("company3").clearValue();
	//Ext.getCmp("company4").clearValue();
	
	Ext.getCmp("euroWinFrom1").setValue();
	Ext.getCmp("euroWinFrom2").setValue();
	Ext.getCmp("euroWinFrom3").setValue();
	//Ext.getCmp("euroWinFrom4").setValue();

	Ext.getCmp("euroWinTo1").setValue();
	Ext.getCmp("euroWinTo2").setValue();
	Ext.getCmp("euroWinTo3").setValue();
	//Ext.getCmp("euroWinTo4").setValue();
	
	Ext.getCmp("euroStandFrom1").setValue();
	Ext.getCmp("euroStandFrom2").setValue();
	Ext.getCmp("euroStandFrom3").setValue();
	//Ext.getCmp("euroStandFrom4").setValue();
	
	Ext.getCmp("euroStandTo1").setValue();
	Ext.getCmp("euroStandTo2").setValue();
	Ext.getCmp("euroStandTo3").setValue();
	//Ext.getCmp("euroStandTo4").setValue();

	Ext.getCmp("euroLossFrom1").setValue();
	Ext.getCmp("euroLossFrom2").setValue();
    Ext.getCmp("euroLossFrom3").setValue();
	//Ext.getCmp("euroLossFrom4").setValue();
	
	Ext.getCmp("euroLossTo1").setValue();
	Ext.getCmp("euroLossTo2").setValue();
    Ext.getCmp("euroLossTo3").setValue();
	//Ext.getCmp("euroLossTo4").setValue();

	    
	},
    onSubmit : function(){
		
		//if (this.getForm().isValid())
		{
			
			//var paramList = this.getForm().getFieldValues();
			
			store.setBaseParam('company1', Ext.getCmp("company1").getValue());
			store.setBaseParam('company2', Ext.getCmp("company2").getValue());
			store.setBaseParam('company3', Ext.getCmp("company3").getValue());
			//store.setBaseParam('company4', Ext.getCmp("company4").getValue());
			
			store.setBaseParam('euroWinFrom1', Ext.getCmp("euroWinFrom1").getValue());
			store.setBaseParam('euroWinFrom2', Ext.getCmp("euroWinFrom2").getValue());
			store.setBaseParam('euroWinFrom3', Ext.getCmp("euroWinFrom3").getValue());
			//store.setBaseParam('euroWinFrom4', Ext.getCmp("euroWinFrom4").getValue());
			
			store.setBaseParam('euroWinTo1', (Ext.getCmp("euroWinTo1").getValue()!=""?Ext.getCmp("euroWinTo1").getValue():Ext.getCmp("euroWinFrom1").getValue()));
			store.setBaseParam('euroWinTo2', (Ext.getCmp("euroWinTo2").getValue()!=""?Ext.getCmp("euroWinTo2").getValue():Ext.getCmp("euroWinFrom2").getValue()));
			store.setBaseParam('euroWinTo3', (Ext.getCmp("euroWinTo3").getValue()!=""?Ext.getCmp("euroWinTo3").getValue():Ext.getCmp("euroWinFrom3").getValue()));
			//store.setBaseParam('euroWinTo4', (Ext.getCmp("euroWinTo4").getValue()!=""?Ext.getCmp("euroWinTo4").getValue():Ext.getCmp("euroWinFrom4").getValue()));
			
			store.setBaseParam('euroStandFrom1', Ext.getCmp("euroStandFrom1").getValue());
			store.setBaseParam('euroStandFrom2', Ext.getCmp("euroStandFrom2").getValue());
			store.setBaseParam('euroStandFrom3', Ext.getCmp("euroStandFrom3").getValue());
			//store.setBaseParam('euroStandFrom4', Ext.getCmp("euroStandFrom4").getValue());
			
			store.setBaseParam('euroStandTo1', (Ext.getCmp("euroStandTo1").getValue()!=""?Ext.getCmp("euroStandTo1").getValue():Ext.getCmp("euroStandFrom1").getValue()));
			store.setBaseParam('euroStandTo2', (Ext.getCmp("euroStandTo2").getValue()!=""?Ext.getCmp("euroStandTo2").getValue():Ext.getCmp("euroStandFrom2").getValue()));
			store.setBaseParam('euroStandTo3', (Ext.getCmp("euroStandTo3").getValue()!=""?Ext.getCmp("euroStandTo3").getValue():Ext.getCmp("euroStandFrom3").getValue()));
			//store.setBaseParam('euroStandTo4', (Ext.getCmp("euroStandTo4").getValue()!=""?Ext.getCmp("euroStandTo4").getValue():Ext.getCmp("euroStandFrom4").getValue()));


			store.setBaseParam('euroLossFrom1', Ext.getCmp("euroLossFrom1").getValue());
			store.setBaseParam('euroLossFrom2', Ext.getCmp("euroLossFrom2").getValue());
			store.setBaseParam('euroLossFrom3', Ext.getCmp("euroLossFrom3").getValue());
			//store.setBaseParam('euroLossFrom4', Ext.getCmp("euroLossFrom4").getValue());
			
			store.setBaseParam('euroLossTo1', (Ext.getCmp("euroLossTo1").getValue()!=""?Ext.getCmp("euroLossTo1").getValue():Ext.getCmp("euroLossFrom1").getValue()));
			store.setBaseParam('euroLossTo2', (Ext.getCmp("euroLossTo2").getValue()!=""?Ext.getCmp("euroLossTo2").getValue():Ext.getCmp("euroLossFrom2").getValue()));
			store.setBaseParam('euroLossTo3', (Ext.getCmp("euroLossTo3").getValue()!=""?Ext.getCmp("euroLossTo3").getValue():Ext.getCmp("euroLossFrom3").getValue()));
			//store.setBaseParam('euroLossTo4', (Ext.getCmp("euroLossTo4").getValue()!=""?Ext.getCmp("euroLossTo4").getValue():Ext.getCmp("euroLossFrom4").getValue()));
			
//			store.setBaseParam('asiaMouth1', Ext.getCmp("asiaMouth1").getValue());
//			store.setBaseParam('asiaMouth2', Ext.getCmp("asiaMouth2").getValue());
//			store.setBaseParam('asiaMouth3', Ext.getCmp("asiaMouth3").getValue());
//			store.setBaseParam('asiaMouth4', Ext.getCmp("asiaMouth4").getValue());
			//store.setBaseParam('start', 0);
			//store.setBaseParam('limit', 24);
			//store.setBaseParam('sort', 'date');
			//store.setBaseParam('dir', 'DESC');
			store.load();
		}
		
		
	
	}, 
    
    initComponent: function() {
        this.initialConfig = Ext.apply({
            url: 'getResultAnalyze4'
        }, this.initialConfig);
        this.layoutConfig = {
            padding: 10
        };
        this.items = [
            {
                xtype: 'container',
                layout: 'form',
                height: 103,
                autoHeight: true,
                labelWidth: 50,
                flex: 1,
                items: [
                    {
                        xtype: 'combo',
                        fieldLabel: '公司1',
                        anchor: '100%',
                        name: 'company1',
                        tabIndex:1,	
					    triggerAction:"all",
							
						store:getPropertyStore('company'),
						allowBlank: false,
						id:'company1',
						valueField:'id',
						displayField:'text'                        	
                    },
                    {
                        xtype: 'combo',
                        fieldLabel: '公司2',
                        anchor: '100%',
                        name: 'company2',
                        tabIndex:8,	
    					triggerAction:"all",
							
    					store:getPropertyStore('company'),
    					allowBlank: false,
    					id:'company2',
    					valueField:'id',
    					displayField:'text'                        	
                        	
                    },
                    {
                        xtype: 'combo',
                        fieldLabel: '公司3',
                        anchor: '100%',
                        name: 'company3',
    					triggerAction:"all",
    					tabIndex:15,	
    					store:getPropertyStore('company'),
    					allowBlank: false,
    					id:'company3',
    					valueField:'id',
    					displayField:'text'                        	
                        	
                    }
//                    ,
//                    {
//                        xtype: 'combo',
//                        fieldLabel: '公司4',
//                        anchor: '100%',
//                        name: 'company4',
//    					triggerAction:"all",
//    					tabIndex:22,	
//    					store:getPropertyStore('company'),
//    					allowBlank: false,
//    					id:'company4',
//    					valueField:'id',
//    					displayField:'text'                        	
//                        	
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔胜',
                        anchor: '100%',
                        tabIndex:2,
                        name: 'euroWinFrom1',
                        id: 'euroWinFrom1' 
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔胜',
                        anchor: '100%',
                        tabIndex:9,
                        id: 'euroWinFrom2'
                        , decimalPrecision : 3	
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔胜',
                        anchor: '100%',
                        tabIndex:16,
                        id: 'euroWinFrom3'
                        , decimalPrecision : 3
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '欧赔胜',
//                        anchor: '100%',
//                        tabIndex:23,
//                        id: 'euroWinFrom4'
//                        , decimalPrecision : 3
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:3,
                        id: 'euroWinTo1'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:10,
                        id: 'euroWinTo2'
                        , decimalPrecision : 3	
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:17,
                        id: 'euroWinTo3'
                        , decimalPrecision : 3
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '到',
//                        anchor: '100%',
//                        tabIndex:24,
//                        id: 'euroWinTo4'
//                        , decimalPrecision : 3
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔平',
                        tabIndex:4,
                        anchor: '100%',
                        id: 'euroStandFrom1'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔平',
                        anchor: '100%',
                        tabIndex:11,
                        id: 'euroStandFrom2'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔平',
                        anchor: '100%',
                        tabIndex:18,
                        id: 'euroStandFrom3'
                        , decimalPrecision : 3
                        	
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '欧赔平',
//                        anchor: '100%',
//                        tabIndex:25,
//                        id: 'euroStandFrom4'
//                        , decimalPrecision : 3
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:5,
                        id: 'euroStandTo1'
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:12,
                        id: 'euroStandTo2'
                       	, decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:19,
                        id: 'euroStandTo3'
                        , decimalPrecision : 3
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '到',
//                        anchor: '100%',
//                        tabIndex:26,
//                        id: 'euroStandTo4'
//                        , decimalPrecision : 3
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔负',
                        anchor: '100%',
                        tabIndex:6,
                        id: 'euroLossFrom1'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔负',
                        anchor: '100%',
                        tabIndex:13,
                        id: 'euroLossFrom2'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '欧赔负',
                        anchor: '100%',
                        tabIndex:20,
                        id: 'euroLossFrom3'
                        , decimalPrecision : 3
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '欧赔负',
//                        anchor: '100%',
//                        tabIndex:27,
//                        id: 'euroLossFrom4'
//                        , decimalPrecision : 3
//                    }
                ]
            },
            {
                xtype: 'container',
                flex: 1,
                autoHeight: true,
                layout: 'form',
                labelWidth: 50,
                items: [
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:7,
                        id: 'euroLossTo1'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:14,
                        id: 'euroLossTo2'
                        , decimalPrecision : 3
                    },
                    {
                        xtype: 'numberfield',
                        fieldLabel: '到',
                        anchor: '100%',
                        tabIndex:21,
                        id: 'euroLossTo3'
                        , decimalPrecision : 3                        	
                    }
//                    ,
//                    {
//                        xtype: 'numberfield',
//                        fieldLabel: '到',
//                        anchor: '100%',
//                        tabIndex:28,
//                        id: 'euroLossTo4'
//                        , decimalPrecision : 3                        	
//                    }
                ]
            }
        ];
        
        
        this.bbar = {
            xtype: 'toolbar',
            bubbleEvents: '',
            height: 25,
            items: [
                {
                    xtype: 'button',
                    text: '查询',
                    width: 68,
                    handler: this.onSubmit, 
                    id: 'queryBtn'
                },
                {
                    xtype: 'button',
                    text: '重置',
                    width: 68,
                    id: 'resetBtn',
                    handler: this.onReset
                }
            ]
        };
        MyPanelUi.superclass.initComponent.call(this);
    }
});



//MyGridUi = Ext.extend(Ext.grid.GridPanel, {
//    title: '查询结果',
//    flex: 8,
//    id: 'result',
//    plugins:[],
//    filters: new Ext.ux.grid.GridFilters({
//        encode: false, // json encode the filter query
//        local: false   // defaults to false (remote filtering)
//        , filters:p_matchFilterConfig 
//
//    }), 
//    
//    buildPaggingToolbar: function(grid){
//	
//	var pagging = new Ext.PagingToolbar({
//        pageSize: 24,
//        store: store,
//        displayInfo: true,
//        displayMsg: '显示记录 {0} - {1} of {2}',
//        emptyMsg: "无记录"
//    	});
//	
//	return pagging;
//	
//	
//	},
//    initComponent: function() {
//		this.bbar = this.buildPaggingToolbar(this);
//		this.plugins[this.plugins.length] = this.filters;
//        this.columns = [
//                      	{id:'id',header: "id", width: 60, fixed:true, sortable: true, locked:true, dataIndex: 'id', hidden:true}
//                        ,{id:'date',header: "日期", width: 80, sortable: true,  dataIndex: 'date', renderer:Ext.util.Format.dateRenderer('m-d H:i'), filter:{type:'date'} }
//                        ,{id:'country',header: "国家", width: 50, sortable: true,  dataIndex: 'country', renderer: renderProperty, filter:{type:'list',store:getPropertyStore("country")}}           
//                        ,{id:'company',header: "公司", width: 50, sortable: true,  dataIndex: 'company', renderer: renderProperty, filter:{type:'date'}}
//                        ,{id:'championship',header: "赛事", width: 100, sortable: true,  dataIndex: 'championship', renderer: renderProperty, filter:{type:'list',store:getPropertyStore("company")}}
//                        ,{id:'level',header: "级别", width: 40, sortable: true,  dataIndex: 'level', filter:{type:'numeric'}}
//                        ,{id:'isInHome',header: "主场", width: 40, sortable: true,  dataIndex: 'isInHome', filter:{type:'boolean'}}
//                        ,{id:'homeTeam',dataIndex:'homeTeam',header: "主队", width: 100, sortable: true, renderer: renderTeamName}
//                        ,{id:'awayTeam',dataIndex:'awayTeam',header: "客队", width: 100, sortable: true, renderer: renderTeamName}           
//                        ,{id:'rankDiff',dataIndex:'rankDiff',header: "排名差", width: 50, sortable: true}          
//
//                        ,{id:'score',header: "比分", width: 50, sortable: true,  dataIndex: 'score', filter:{type:'string'}, css:'border-right:1px  solid  #FF0000;'}
//
//                        ,{id:'euro_final_win',header: "欧赔_胜", width: 50, sortable: true,  dataIndex: 'euro_final_win', renderer:renderResult, filter:{type:'numeric', decimalPrecision: 3}}
//                        ,{id:'euro_final_standoff',header: "欧赔_平", width: 50, sortable: true,  dataIndex: 'euro_final_standoff', renderer:renderResult, filter:{type:'numeric'}}           
//                        ,{id:'euro_final_loss',header: "欧赔_负", width: 50, sortable: true,  dataIndex: 'euro_final_loss', renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;'}
//
//                        ,{id:'euro_early_win',dataIndex:'euro_early_win', header: "欧初_胜", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}}
//                        ,{id:'euro_early_standoff',dataIndex:'euro_early_standoff',header: "欧初_平", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}}           
//                        ,{id:'euro_early_loss',dataIndex:'euro_early_loss',header: "欧初_负", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;'}           
//                        
//                        ,{id:'asia_final_up',dataIndex:'asia_final_up', header: "亚赔_上", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}}
//                        ,{id:'asia_final_ud_mouth',dataIndex:'asia_final_ud_mouth',header: "亚赔_盘口", width: 50, sortable: true, renderer: renderProperty, filter:{type:'list', store:getPropertyStore("asia_ud_mouth")}}           
//                        ,{id:'asia_final_down',dataIndex:'asia_final_down',header: "亚赔_下", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;'}
//
//                        ,{id:'asia_early_up',dataIndex:'asia_early_up',header: "亚初_上", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}}
//                        ,{id:'asia_early_ud_mouth',dataIndex:'asia_early_ud_mouth',header: "亚初_盘口", width: 50, sortable: true, renderer: renderProperty, filter:{type:'list', store:getPropertyStore("asia_ud_mouth")}}           
//                        ,{id:'asia_early_down',dataIndex:'asia_early_down', header: "亚初_下", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;'}           
//                       
//                        ,{id:'asia_final_big',dataIndex:'asia_final_big',header: "大小_大", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}}
//                        ,{id:'asia_final_bs_mouth',dataIndex:'asia_final_bs_mouth',header: "大小_盘口", width: 50, sortable: true, renderer: renderProperty, filter:{type:'list', store:getPropertyStore("asia_bs_mouth")}}           
//                        ,{id:'asia_final_small',dataIndex:'asia_final_small', header: "大小_小", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;'}
//
//                        ,{id:'asia_early_big',dataIndex:'asia_early_big',header: "大小初_大", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, hidden: true}
//                        ,{id:'asia_early_bs_mouth',dataIndex:'asia_early_bs_mouth',header: "大小初_盘口", width: 50, sortable: true, renderer: renderProperty, filter:{type:'list', store:getPropertyStore("asia_bs_mouth")}, hidden: true}           
//                        ,{id:'asia_early_small',dataIndex:'asia_early_small',header: "大小初_小", width: 50, sortable: true, renderer:renderResult, filter:{type:'numeric'}, css:'border-right:1px  solid  #FF0000;', hidden: true}           
//
//                        ,{id:'win_standoff_loss',dataIndex:'win_standoff_loss',header: "胜负平", width: 50, sortable: true, filter:{type:'list', options:['胜', '平', '负']}}
//                        ,{id:'up_down',dataIndex:'up_down',header: "上下", width: 50, sortable: true, filter:{type:'list', options:['上', '平', '下']}}            
//                        ,{id:'big_small',dataIndex:'big_small',header: "大小", width: 50, sortable: true, filter:{type:'list', options:['大', '平', '小']}}           
//                       
//        ];
//        MyGridUi.superclass.initComponent.call(this);
//    }
//});

//var matchReader = new Ext.data.JsonReader({
//    totalProperty: 'total',
//    successProperty: 'success',
//    idProperty: 'id',
//    root: 'matches',
//    fields:[
//            
//			     {name: 'id', allowBlank: true, type:'int'}
//				,{name: 'id2', allowBlank: true, type:'int'}
//				,{name: 'date', allowBlank: false, type:'date', dateFormat:'Y-m-d\TH:i:sP'}
//				,{name: 'company', allowBlank: false, type:'int', storeKey:'company'}
//				,{name: 'country', allowBlank: false, type:'int', storeKey:'country'}
//				,{name: 'championship', allowBlank: false, type:'int',  storeKey:'championship'}
//				
//				,{name: 'score', allowBlank: true, type:'string'}
//				,{name: 'level', allowBlank: true, type:'int'}
//				,{name: 'isInHome', allowBlank: true, type:'boolean'}
//
//				,{name: 'euro_final_win', allowBlank: true, type:'float'}
//				,{name: 'euro_final_standoff', allowBlank: true, type:'float'}
//				,{name: 'euro_final_loss', allowBlank: true, type:'float'}
//
//				,{name: 'euro_early_win', allowBlank: true, type:'float'}
//				,{name: 'euro_early_standoff', allowBlank: true, type:'float'}
//				,{name: 'euro_early_loss', allowBlank: true, type:'float'}
//
//				,{name: 'asia_final_up', allowBlank: true, type:'float'}
//				,{name: 'asia_final_ud_mouth', allowBlank: true, type:'int', storeKey:'asia_ud_mouth'}
//				,{name: 'asia_final_down', allowBlank: true, type:'float'}
//
//				,{name: 'asia_early_up', allowBlank: true, type:'float'}
//				,{name: 'asia_early_ud_mouth', allowBlank: true, type:'int', storeKey:'asia_ud_mouth'}
//				,{name: 'asia_early_down', allowBlank: true, type:'float'}
//
//				,{name: 'asia_final_big', allowBlank: true, type:'float'}
//				,{name: 'asia_final_bs_mouth', allowBlank: true, type:'int', storeKey:'asia_bs_mouth'}
//				,{name: 'asia_final_small', allowBlank: true, type:'float'}
//
//				,{name: 'asia_early_big', allowBlank: true, type:'float'}
//				,{name: 'asia_early_bs_mouth', allowBlank: true, type:'int', storeKey:'asia_bs_mouth'}
//				,{name: 'asia_early_small', allowBlank: true, type:'float'}
//
//				,{name: 'homeTeam', allowBlank: false, type:'int', storeKey:'team'}
//				,{name: 'awayTeam', allowBlank: false, type:'int', storeKey:'team'}
//				
//				,{name: 'homeTeamPosition', allowBlank: false, type:'int'}
//				,{name: 'awayTeamPosition', allowBlank: false, type:'int'}
//				,{name: 'rankDiff', allowBlank: false, type:'int'}
//
//				,{name: 'win_standoff_loss', allowBlank: true, type:'string'}
//				,{name: 'up_down', allowBlank: true, type:'string'}
//				,{name: 'big_small', allowBlank: true, type:'string'}    						
//				,{name: 'asia_win', allowBlank: true, type:'string'}
//
//
//      
//            ]
//	}
//	
//	
//);


QueryForm3Ui = Ext.extend(Ext.Viewport, {
    layout: 'vbox',
    autoShow: true,
    
    initComponent: function() {
        this.layoutConfig = {
            align: 'stretch'
        };
        this.items = [];
        QueryForm3Ui.superclass.initComponent.call(this);
    }
});


//var proxy = new Ext.data.HttpProxy({
//	url: 'getAnalyzeResult4.do'
//});
//
//
//
//
//
//var p_sortInfo = {field: 'date', direction: 'DESC'};
////p_sortInfo = {field: 'big_count', direction: 'ASC'};
//var p_groupField = ['date'];
//
//
//
//var store = new Ext.data.Store({
//	id: 'matchStore',
//    proxy: proxy,
//    sortInfo: p_sortInfo,
//    groupField: p_groupField,
//    remoteSort: true, 
//    restful: false,
//     
//    reader: matchReader,
// 
//
//    listeners: {
//        write : function(store, action, result, res, rs) {
//            App.setAlert(res.success, res.message); // <-- show user-feedback for all write actions
//        },
//        exception : function(proxy, type, action, options, res, arg) {
//            if (type === 'remote') {
//                Ext.Msg.show({
//                    title: 'REMOTE EXCEPTION',
//                    msg: res.message,
//                    icon: Ext.MessageBox.ERROR,
//                    buttons: Ext.Msg.OK
//                });
//            }
//        }
//    }
//});


//Ext.onReady(function() {
//	window.focus();
//    Ext.QuickTips.init();
//    
//	
//
//    
//    var formPanel = new MyPanelUi();
//
//    var resultGrid = new MyGridUi({
//    	store:store
//    });
//
//    
//	
//    var viewport = new Ext.Viewport({
//    	
//
//    	layout:"vbox",
//    	
//    	autoDestroy:true,    	
//    	autoShow: true,
//    	align: 'top',
//        pack  : 'start',
//
//
//		items: [
//		        formPanel, resultGrid
//		
//			
//		], 
//		renderTo: 'client22'
//    });
//
//    
//    
//});

