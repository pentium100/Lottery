/*!
 * Ext JS Library 3.0+
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
// Application instance for showing user-feedback messages.
var App = new Ext.App({});

// Create a standard HttpProxy instance.
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
    encode: false   // <-- don't return encoded JSON -- causes Ext.Ajax#request to send data using jsonData config rather than HTTP params
    ,writeAllFields: true
});

// Typical Store collecting the Proxy, Reader and Writer together.
var store = new Ext.data.Store({
    id: 'user',
    restful: true,     // <-- This Store is RESTful
    proxy: proxy,
    reader: reader,
    writer: writer,    // <-- plug a DataWriter into the store just as you would a Reader
    autoSave: true, 
    listeners: {
        write : function(store, action, result, response, rs) {
			if(res.message==undefined){res.message = res.raw.message;}
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

// Let's pretend we rendered our grid-columns with meta-data from our ORM framework.


// load the store immeditately
store.load();

Ext.onReady(function() {
	window.focus();
    Ext.QuickTips.init();

    // use RowEditor for editing
    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    // Create a typical GridPanel with RowEditor plugin
    var userGrid = new Ext.grid.GridPanel({
    	//renderTo: 'client', 
        iconCls: 'icon-grid',
        frame: true,
        height: 300, 
        //layout: 'fit',
        //autoHeight:true, 

        title: p_grid_title,
        autoScroll: true,
        store: store,
        plugins: [editor],
        columns : p_columns,
        tbar: [{
            text: 'Add',
            iconCls: 'silk-add',
            handler: onAdd
        }, '-', {
            text: 'Delete',
            iconCls: 'silk-delete',
            handler: onDelete
        }, '-'],
        viewConfig: {
            forceFit: true
        }
    
    	, listener:{
    		resize : function(t, adjWidth, adjHeight,  rawWidth, rawHeight ) {
    	
    			t.doLayout();
     
    		}

    	}
    });

    /**
     * onAdd
     */
    function onAdd(btn, ev) {
        var store = userGrid.store;
        orec = new store.recordType();            
        orec.data = {};            
        orec.fields.each(function(field) {                
        	orec.data[field.name] = field.defaultValue;            
        	});            
        orec.data.newRecord = true;            
        orec.commit();
        
        editor.stopEditing();
        userGrid.store.insert(0, orec);
        editor.startEditing(0);
    }
    /**
     * onDelete
     */
    function onDelete() {
        var rec = userGrid.getSelectionModel().getSelected();
        if (!rec) {
            return false;
        }
        userGrid.store.remove(rec);
    }
    
    function onSave() {
    	
    	userGrid.store.save();
    	
    }
    
    var viewport = new Ext.Viewport({
        layout: 'fit'
        ,renderTo: 'client'
		,items: [
		// create instance immediately
			{region : 'center',
			 items: [userGrid]

            
			}	
			
		] 
		
		
    });
         


         


});
