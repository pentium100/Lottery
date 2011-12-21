/**
 * @author Andrei
 */

Ext.onReady(function(){

	Ext.QuickTips.init();

	// turn on validation errors beside the field globally
	Ext.form.Field.prototype.msgTarget = 'side';

	var singleS, singleSvalue,
			multiS, multiSvalue,
			singleSF, singleFSvalue,
			singleSDOM, singleSDOMvalue,
			multiSDOM, multiSDOMvalue,
			combo, comboValue,
			comboF, comboFvalue,
			singleSH, singleSHvalue;
	var ds = new Ext.data.SimpleStore({
		fields:['countryCode', 'countryName'],
		data:[
			['US', 'United States'],
			['DE', 'Germany'],
			['FR', 'France']
		]
	});

	var config = {
	};
		
	var formSelect = new Ext.form.FormPanel({
		labelAlign:'right',
		bodyStyle:'border:0px',
		labelWidth:100,
		width:350,
		items:[{// combobox
				xtype:'fieldset',
				autoHeight:true,
				title:'ComboBox',
				defaults:{
					width:200
				},
				items:[					
					comboValue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
					
					combo = new Ext.form.ComboBox(Ext.applyIf({
						fieldLabel:'Field'
					}, config))
				]
			},{// combobox - forceSelection:true
				xtype:'fieldset',
				autoHeight:true,
				title:'ComboBox (Force)',
				defaults:{
					width:200
				},
				items:[					
					comboFvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
					
					comboF = new Ext.form.ComboBox(Ext.applyIf({
						fieldLabel:'Field',
						forceSelection:true
					}, config))
				]
			},{// single select
				xtype:'fieldset',
				autoHeight:true,
				title:'Select',
				defaults:{
					width:200
				},
				items:[					
					singleSvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
					
					singleS = new Ext.ux.Andrie.Select(Ext.applyIf({
						fieldLabel:'Field',
						value:'test'
					}, config))
				]
			},{// single select - forceSelection:true
				xtype:'fieldset',
				autoHeight:true,
				title:'Select (Force)',
				defaults:{
					width:200
				},
				items:[					
					singleSFvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
					
					singleSF = new Ext.ux.Andrie.Select(Ext.applyIf({
						fieldLabel:'Field',
						forceSelection:true,
						value:'test'
					}, config))
				]
			},{// multi select
				xtype:'fieldset',
				autoHeight:true,
				title:'Multi Select',
				defaults:{
					width:200
				},
				items:[					
					multiSvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
				
					multiS = new Ext.ux.Andrie.Select(Ext.applyIf({
						fieldLabel:'Field',
						multiSelect:true,
						minLength:2
					}, config))
				]
			},{// history enabled select
				xtype:'fieldset',
				autoHeight:true,
				title:'Select - history enabled',
				defaults:{
					width:200
				},
				items:[					
					singleSHvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
				
					singleSH = new Ext.ux.Andrie.Select(Ext.applyIf({
						fieldLabel:'Field',
						history:true,
						store:Ext.apply({}, ds),
						selectOnFocus:false,
						valueField:'countryCode',
						displayField:'countryName'
					}, config))
				]
			},{// select - from markup
				xtype:'fieldset',
				autoHeight:true,
				title:'Select - from markup',
				defaults:{
					width:200
				},
				items:[					
					singleSDOMvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
					
					singleSDOM = new Ext.ux.Andrie.Select({
						fieldLabel:'Field',
						transform:'singleSDOM',
						msgTarget:'qtip'
					})
				]
			},{// multi select - from markup
				xtype:'fieldset',
				autoHeight:true,
				title:'Multi Select - from markup',
				defaults:{
					width:200
				},
				items:[					
					multiSDOMvalue = new Ext.form.TextField({
						fieldLabel:'Value',
						readOnly:true
					}),
				
					multiSDOM = new Ext.ux.Andrie.Select({
						fieldLabel:'Field',
						minLength:2,
						transform:'multiSDOM',
						msgTarget:'qtip'/*,
						multiSelect:true*/ // this value is implicit as the SELECT tag has the MULTIPLE attribute
					})
				]
			}
		],
		
		buttons:[{
				text:'set(US)',
				handler:function(){
					singleS.setValue('US');
					singleSF.setValue('US');
					combo.setValue('US');
					comboF.setValue('US');
					multiS.setValue('US');
					multiSDOM.setValue('US');
					singleSDOM.setValue('US');
					singleSH.setValue('US');
				}
		},{
				text:'set(FR, US)',
				handler:function(){
					singleS.setValue(['FR', 'US']);
					singleSF.setValue(['FR', 'US']);
					multiS.setValue(['FR', 'US']);
					singleSDOM.setValue(['FR', 'US']);
					multiSDOM.setValue(['FR', 'US']);
					singleSH.setValue(['FR', 'US']);
				}
		},{
				text:'add(DE)',
				handler:function(){
					singleS.addValue('DE');
					singleSF.addValue('DE');
					multiS.addValue('DE');
					singleSDOM.addValue('DE');
					multiSDOM.addValue('DE');
					singleSH.addValue('DE');
				}
		},{
				text:'remove(DE)',
				handler:function(){
					singleS.removeValue('DE');
					singleSF.removeValue('DE');
					multiS.removeValue('DE');
					singleSDOM.removeValue('DE');
					multiSDOM.removeValue('DE');
					singleSH.removeValue('DE');
				}
		}]
	});

	singleS.on('change', function(){
		singleSvalue.setValue(this.getValue());
	}, singleS);

	singleSF.on('change', function(){
		singleSFvalue.setValue(this.getValue());
	}, singleSF);

	combo.on('change', function(){
		comboValue.setValue(this.getValue());
	}, combo);

	comboF.on('change', function(){
		comboFvalue.setValue(this.getValue());
	}, comboF);

	multiS.on('change', function(){
		multiSvalue.setValue(this.getValue());
	}, multiS);

	singleSH.on('change', function(){
		singleSHvalue.setValue(this.getValue());
	}, singleSH);

	multiSDOM.on('change', function(){
		multiSDOMvalue.setValue(this.getValue());
	}, multiSDOM);

	singleSDOM.on('change', function(){
		singleSDOMvalue.setValue(this.getValue());
	}, singleSDOM);

	formSelect.render('form-ct');
	
	new Ext.Panel({
		title:'Usage - Single Select (acts as the usual ComboBox)',
		renderTo:'singles-ct',
		contentEl:'singles',
		frame:true,
		width:450
	});
	
	new Ext.Panel({
		title:'Usage - Multi Select',
		renderTo:'multis-ct',
		contentEl:'multis',
		frame:true,
		width:450
	});
	
	new Ext.Panel({
		title:'Ext.ux.Andrie.Select',
		renderTo:'info-ct',
		contentEl:'info',
		bodyStyle:'padding: 5px',
		width:450
	});
	
	new Ext.Panel({
		title:'New Config Properties (from Ext.form.ComboBox)',
		renderTo:'config-ct',
		contentEl:'config',
		frame:true,
		width:450
	});
	
	new Ext.Panel({
		title:'New Public Properties and Methods (from Ext.form.ComboBox)',
		renderTo:'public-ct',
		contentEl:'public',
		frame:true,
		width:450
	});
	
	new Ext.Panel({
		title:'ChangeLog',
		renderTo:'changelog-ct',
		contentEl:'changelog',
		frame:true,
		width:450,
		height:250,
		autoScroll:true
	});
});