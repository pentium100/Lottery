/*
 * ! Ext JS Library 3.0+ Copyright(c) 2006-2009 Ext JS, LLC licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('App', 'App.user');
/**
 * App.user.Grid A typical EditorGridPanel extension.
 */
App.user.Grid = Ext.extend(Ext.grid.GridPanel, {
	// renderTo: 'user-grid',
	// region:'north',
	iconCls : 'silk-grid',
	flex : 8,
	frame : true,
	title : p_title,
	stripeRows : true,
	loadMask : true,
	sm : new Ext.grid.RowSelectionModel({
				singleSelect : false
			}),

	// width: 500,
	// style: 'margin-top: 10px',
	filters : new Ext.ux.grid.GridFilters({
				// encode and local configuration options defined previously for
				// easier reuse
				encode : false, // json encode the filter query
				local : false // defaults to false (remote filtering)
				,
				filters : p_filterConfig

			}),

	plugins : [p_editor],

	initComponent : function() {

		// typical viewConfig
		this.viewConfig = {
		// forceFit: true
		};

		// relay the Store's CRUD events into this grid so these events can be
		// conveniently listened-to in our application-code.
		this.relayEvents(this.store, ['destroy', 'save', 'update']);

		// build toolbars and buttons.
		// this.tbar = this.buildTopToolbar();
		// this.bbar = this.buildBottomToolbar();
		this.bbar = this.buildPaggingToolbar(this);
		// this.buttons = this.buildUI();

		this.plugins[this.plugins.length] = this.filters;

		// super
		App.user.Grid.superclass.initComponent.call(this);
	},

	/**
	 * buildPaggingToolbar
	 */

	buildPaggingToolbar : function(grid) {

		var pagging = new Ext.PagingToolbar({
			pageSize : 80,
			store : store,
			parentGrid : grid,
			displayInfo : true,
			displayMsg : '显示记录 {0} - {1} of {2}',
			emptyMsg : "无记录",
			plugins : [this.filters],
			items : [{
						xtype : "button",
						text : "显示初盘",
						enableToggle : true,
						toggleHandler : function(btn, pressed) {

							cm = grid.getColumnModel();
							for (i = 0; i < cm.getColumnCount(false); i++) {
								if (cm.getDataIndex(i).indexOf('early') >= 0) {
									cm.setHidden(i, !pressed);
								}
							}

						}

					},

					{
						xtype : "button",
						text : "显示未开赛的比赛",
						handler : function(btn) {

							filter = grid.filters.getFilter('score');
							filter.setActive(true, false);
							filter.setValue('null');

							filter = grid.filters.getFilter('company');
							filter.setActive(true, false);
							filter.setValue([31, 33], false);

						}

					}, {
						xtype : "button",
						text : "显示联赛",
						handler : function(btn) {

							filter = grid.filters.getFilter('championship');
							filter.setActive(true, false);
							filter.setValue([49, 50, 53, 55, 57, 64, 1272]);
							
							//49	1	英超
							//50	1	英冠
							//53	1	意甲
							//55	1	西甲
							//57	1	德甲
							//64	1	法甲
							//1272	1	歐冠盃

						}

					}, 
					{
						xtype : "button",
						text : "显示赛会",
						handler : function(btn) {

							filter = grid.filters.getFilter('championship');
							filter.setActive(true, false);
							filter.setValue([1272, 6662,6663,6664,6703]);
							
							//1272	1	歐冠盃
							//6662	1	世界杯
							//6663	1	欧洲预选
							//6664	1	南美预选
							//6703	1	美洲杯

						}

					},	
							
						{
						xtype : "button",
						text : "显示历史统计",
						handler : function(btn) {

							var sm = grid.sm;
							if (sm.getCount() <= 0) {
								return;
							}

							//var rec = g.store.getAt(rowIndex);
							var records = sm.getSelections()
							var rec = records[0];

							var queryWindow = new Ext.HistoryAnalyzeWindow({
										listeners : {
											show : function(t) {
												grid.disable();
											},
											close : function(p) {
												grid.enable();

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

					}, {
						xtype : "button",
						text : "显示其他公司",
						handler : function(btn) {

							var sm = grid.sm;
							if (sm.getCount() > 0) {

								records = sm.getSelections();

								var queryWindow = new Ext.SMDCAnalyzeWindow({
									records : records,
									listeners : {
										show : function(t) {
											grid.disable();

											// filter =
											// this.sMDCGrid.filters.getFilter('id2');
											// filter.setActive(true, false);
											matchStore.setBaseParam(
													'filter[0][data][type]',
													'list');

											matchStore.setBaseParam(
													'filter[0][field]', 'id2');
											matchStore.setBaseParam('start', 0);
											matchStore.setBaseParam('limit',
													200);
											id2List = [];
											for (var i = 0; i < records.length; i++) {
												id2List[i] = records[i].data.id2;
											}
											matchStore.setBaseParam(
													'filter[0][data][value]',
													id2List);
											// filter.setValue(id2List);
											matchStore.load();
										},
										close : function(p) {
											grid.enable();

										}

									}
								});

								queryWindow.show();

							}

						}

					},

					{
						xtype : "button",
						text : "清除过滤器",
						handler : function(btn) {
							// debugger;
							filters = grid.filters;
							store.setBaseParam('company1', "");
							// alert(Ext.encode(filters.getFilterData()));
							filters.clearFilters();

						}

					},

					{
						text : 'Export to Excel',
						iconCls : 'excel',
						handler : function() {
							if (grid.store.data.length < 1)
								return;
							var vExportContent = grid.getExcelXml(0);
							// if (Ext.isIE6 || Ext.isIE7 || Ext.isIE8 ||
							// Ext.isSafari || Ext.isSafari2 || Ext.isSafari3){
							if (1 == 1) {
								var fd = Ext.get('excel');
								if (!fd) {
									fd = Ext.DomHelper.append(Ext.getBody(), {
												tag : 'form',
												method : 'post',
												id : 'excel',
												action : 'export2Excel.jsp',
												target : '_blank',
												name : 'excel',
												cls : 'x-hidden',
												cn : [{
															tag : 'input',
															name : 'exportContent',
															id : 'exportContent',
															type : 'hidden'
														}]
											}, true);
								}
								fd.child('#exportContent').set({
											value : vExportContent
										});
								fd.dom.submit();
							} else {
								document.location = 'data:application/vnd.ms-excel;base64,'
										+ Base64.encode(vExportContent);
							}
						}
					},

					{
						xtype : "button",
						text : "保存数据",
						handler : function(btn) {
							// debugger;
							
							
							store.save();

						}

					}]
		});

		return pagging;

	},

	/**
	 * buildTopToolbar
	 */
	buildTopToolbar : function() {
		return [{
					text : 'Add',
					iconCls : 'silk-add',
					handler : this.onAdd,
					scope : this
				}, '-', {
					text : 'Delete',
					iconCls : 'silk-delete',
					handler : this.onDelete,
					scope : this
				}, '-'];
	},

	/**
	 * buildBottomToolbar
	 */
	buildBottomToolbar : function() {
		return ['<b>@cfg:</b>', '-', {
			text : 'autoSave',
			enableToggle : true,
			pressed : true,
			tooltip : 'When enabled, Store will execute Ajax requests as soon as a Record becomes dirty.',
			toggleHandler : function(btn, pressed) {
				this.store.autoSave = pressed;
			},
			scope : this
		}, '-', {
			text : 'batch',
			enableToggle : false,
			pressed : false,
			tooltip : 'When enabled, Store will batch all records for each type of CRUD verb into a single Ajax request.',
			toggleHandler : function(btn, pressed) {
				this.store.batch = pressed;
			},
			scope : this
		}, '-', {
			text : 'writeAllFields',
			enableToggle : true,
			tooltip : 'When enabled, Writer will write *all* fields to the server -- not just those that changed.',
			toggleHandler : function(btn, pressed) {
				store.writer.writeAllFields = pressed;
			},
			scope : this
		}, '-'];
	},

	/**
	 * buildUI
	 */
	buildUI : function() {
		return [{
					text : 'Save',
					iconCls : 'icon-save',
					handler : this.onSave,
					scope : this
				}];
	},

	/**
	 * onSave
	 */
	onSave : function(btn, ev) {
		this.store.save();
	},

	/**
	 * onAdd
	 */
	onAdd : function(btn, ev) {

		var store = this.store;
		orec = new store.recordType();
		orec.data = {};
		orec.fields.each(function(field) {
					orec.data[field.name] = field.defaultValue;
				});
		orec.data.newRecord = true;
		orec.commit();
		store.insert(0, orec);
		this.fireEvent('rowclick', this, 0);

		// this.store.insert(0, orec);
	},

	/**
	 * onDelete
	 */
	onDelete : function(btn, ev) {
		var index = this.getSelectionModel().getSelectedCell();
		if (!index) {
			return false;
		}
		var rec = this.store.getAt(index[0]);
		this.store.remove(rec);
	},

	showSMDCGrid : function(btn) {

	}

});
