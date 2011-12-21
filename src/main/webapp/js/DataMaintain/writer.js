/*
 * ! Ext JS Library 3.0+ Copyright(c) 2006-2009 Ext JS, LLC licensing@extjs.com
 * http://www.extjs.com/license
 */
// Application instance for showing user-feedback messages.
var App = new Ext.App({});
Ext.data.Record.PREFIX = '';
// Create HttpProxy instance. Notice new configuration parameter "api" here
// instead of load. However, you can still use
// the "url" paramater -- All CRUD requests will be directed to your single url
// instead.
var proxy = new Ext.data.HttpProxy({
	url: p_restful_url
});

// Typical JsonReader. Notice additional meta-data params for defining the core
// attributes of your json-response
var reader = new Ext.data.JsonReader({
    totalProperty: 'total',
    successProperty: 'success',
    idProperty: 'id',
    root:  p_dataRoot
	}, p_matchRecordType    
);


JsonRootWriter= Ext.extend(Ext.data.JsonWriter, {
	
    render: function(params, baseParams, data){
        if (this.encode === true) {
            Ext.apply(params, baseParams);
            params[this.meta.root] = Ext.encode(data);
        } else {
        	
        	
        	//var jdata = Ext.apply({}, baseParams);
        	//var jdata = {};
            //jdata[this.meta.root] = data;
            params.jsonData = data;
        	
        	//var data={data:{}};
        	//Ext.apply(data.data, recData)
            //params.jsonData = data;
        }	
     }
	
	
});



// The new DataWriter component.
var writer = new JsonRootWriter({
    encode: false
    ,writeAllFields: true
});

// Typical Store collecting the Proxy, Reader and Writer together.
var store = new Ext.data.Store({
    id: 'reportMemoStore',
    proxy: proxy,
    reader: reader,
    sortInfo: {field: 'date', direction: 'DESC'},
    restful: true, 
    remoteSort:true,
    writer: writer,  // <-- plug a DataWriter into the store just as you
						// would a Reader
    autoSave: true,  // <-- false would delay executing create, update,
						// destroy requests until specifically told to do so
						// with some [save] buton.
    listeners: {
        write : function(store, action, result, res, rs) {
	        if(res.message==undefined){res.message = res.raw.message;}
            App.setAlert(res.success, res.message); // <-- show user-feedback
													// for all write actions
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


// A new generic text field
var textField =  new Ext.form.TextField();
var dateField =  new Ext.form.TextField();

// Let's pretend we rendered our grid-columns with meta-data from our ORM
// framework.
var userColumns =  p_columns;






// load the store immeditately


Ext.onReady(function() {
	window.focus();
    Ext.QuickTips.init();

 


    // create user.Grid instance (@see UserGrid.js)
    var userGrid = new App.user.Grid({
        
        store: store,
        columns : userColumns,
        region: "center",
        plugins : [p_editor],
        resizable:true, 
        listeners: {
            // rowdblclick: showHistoryAnalyze
        }
        
   
    });
    
    filter = userGrid.filters.getFilter('company');
    filter.setActive(true, false);
    filter.setValue([31], false);
    
    filter = userGrid.filters.getFilter('championship');
    filter.setActive(true, false);
    filter.setValue([49], false);    
	store.setBaseParam('start', 0);
	store.setBaseParam('limit', 80);
    // store.load({params:{start:0, limit:23}});
    
    
    var formPanel = new MyPanelUi({
    	store: store,
    	region: "north",
		collapsible: true
		,collapsed : true
		,split: true
        ,titleCollapse : true 
        ,floatable: false
    });
    
    var viewport = new Ext.Viewport({
    	
    	layout:"border",
    	
    	autoDestroy:true,    	
    	autoShow: true,
		items: [formPanel, userGrid], 
		renderTo: 'client'
    });
         
    userGrid.getView().updateAllColumnWidths();
    
    
    
    
});
