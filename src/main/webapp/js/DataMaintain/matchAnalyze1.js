



	Ext.MatchAnalyzeForm1=Ext.extend(Ext.form.FormPanel ,{
			xtype:"form",
			title:"查询条件",
			labelWidth:100,
			labelAlign:"left",
			layout:"form",
			autoScroll:true,
			flex:2,
			initComponent: function(){
		
        		this.buttons = this.buildUI();
				this.items= this.buildForm();
				Ext.MatchAnalyzeForm1.superclass.initComponent.call(this);
			},
			buildUI:function(){
				return [{
		            text: '提交',
		            iconCls: 'silk-application-go',
		            handler: this.onSubmit,
		            scope: this
		        }
		        , {
		            text: 'Reset',
		            handler: function(btn, ev){
		                this.getForm().reset();
		            },
		            scope: this
		        }];
				
				
			},
			
			onSubmit: function(){
				
				if (this.getForm().isValid()){
					
					var paramList = this.getForm().getFieldValues();
					store.setBaseParam('fromDate', paramList.fromDate);
					store.setBaseParam('toDate', paramList.toDate);
					store.setBaseParam('company', paramList.company);
					store.setBaseParam('euro_mouth', paramList.euro_mouth);
					store.setBaseParam('asia_big_small_mouth', paramList.asia_big_small_mouth);
					store.load();
				}
				
				 
				
			},
			
				
	
			buildForm: function(){
				
				return [
						{
							xtype:"container",
							autoEl:"div",
							layout:"column",
							defaults:{

								columnWidth:0.3
							},
							items:[
								{
									xtype:"container",
									autoEl:"div",
									layout:"form",
									items:[
										{
											xtype:"datefield",
											fieldLabel:"日期从",
											id:'fromDate',
											value: new Date().add('D', -360),
											allowBlank: false,	
											anchor:"100%"
										},
										{
											xtype:"numberfield",
											fieldLabel:"欧赔/初赔",
											allowBlank: false,
											value:2.37, 
											id:'euro_mouth',
											anchor:"100%"
										}
									]
								},
								{
									xtype:"container",
									autoEl:"div",
									layout:"form",
									items:[
										{
											xtype:"datefield",
											fieldLabel:"到",
											value: new Date(),
											allowBlank: false,
											id:'toDate',
											anchor:"100%"
										},
										{
											xtype:"combo",
											triggerAction:"all",
											fieldLabel:"大小盘口",
											id:'asia_big_small_mouth',
											store:getPropertyStore('asia_bs_mouth'),
											allowBlank: false,
											
											valueField:'id',
											displayField:'text',
											anchor:"100%"
										}
									]
								},
								{
									xtype:"container",
									autoEl:"div",
									layout:"form",
									items:[
										{
											xtype:"combo",
											triggerAction:"all",
											fieldLabel:"公司",
											store:getPropertyStore('company'),
											allowBlank: false,
											id:'company',
											valueField:'id',
											displayField:'text',
									
											anchor:"100%"
										 
										}
									]
								}
							]
						}
					];

				
			}
			
	
	
		});
	

var proxy = new Ext.data.HttpProxy({
	url: p_restful_url
});

// Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
var reader = new Ext.data.JsonReader({
    totalProperty: 'total',
    successProperty: 'success',
    idProperty: 'id',
    root: p_dataRoot
	}, p_recordType    
);

// The new DataWriter component.
var writer = new Ext.data.JsonWriter({
    encode: false
    ,writeAllFields: true
});

// Typical Store collecting the Proxy, Reader and Writer together.
var store = new Ext.ux.MultiGroupingStore({
    id: 'reportMemoStore',
    proxy: proxy,
    reader: reader,
    sortInfo: p_sortInfo,
    groupField: p_groupField,
    
    restful: true, 
    writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
    autoSave: true,  // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    listeners: {
        write : function(store, action, result, res, rs) {
            App.setAlert(res.success, res.message); // <-- show user-feedback for all write actions
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

var groupView = new Ext.ux.MultiGroupingView({
    hideGroupedColumn :false
   ,forceFit: true
   ,emptyGroupText: 'All Group Fields Empty'
   ,displayEmptyFields: true //you can choose to show the group fields, even when they have no values
//   ,groupTextTpl: 'Help = {text} ' //({[values.rs.length]} {[values.rs.length > 1 ? "Records" : "Record"]})',
   ,groupTextTpl: '{text} : {gvalue} ({[values.rs.length]} {[values.rs.length == 1 ? "Record" : "Records"]})'
   ,displayFieldSeparator: ', ' //you can control how the display fields are seperated
   });


Ext.MatchAnalyzeResult1=Ext.extend(Ext.ux.MultiGroupingGrid ,{
	xtype:"grid",
	initComponent: function(){
    	this.viewConfig = {
            forceFit: true
        };
		Ext.MatchAnalyzeResult1.superclass.initComponent.call(this);
	}
});


Ext.onReady(function() {
	window.focus();
    Ext.QuickTips.init();

    // create user.Form instance (@see UserForm.js)
    var userForm = new Ext.MatchAnalyzeForm1({
    	frame:true,
    	
    	region : 'north',
    	height:121


    });
    var groupSummary = new Ext.ux.grid.GroupSummary();
    var gridSummary = new Ext.ux.grid.GridSummary();
    
     var userGrid = new Ext.MatchAnalyzeResult1({
   		title:"结果",
   		columns:p_columns,
   	    store: store,
   	    view: groupView, 
   	    region : 'center',
   	   
   	    plugins:[gridSummary, groupSummary]

    	 
     });
    
	
    var viewport = new Ext.Viewport({
    	

    	layout:"border",
    	
    	autoDestroy:true,    	

		items: [
			userForm, userGrid
		
			
		], 
		renderTo: 'client22'
    });
    // create user.Grid instance (@see UserGrid.js)
    //var userGrid = new Ext.MatchAnalyzeResult1();
    
        


    
    
});


