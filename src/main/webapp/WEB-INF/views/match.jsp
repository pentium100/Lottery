<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}盘口数据输入</title>

<link rel="stylesheet" type="text/css"
	href="js/ext-3.0.0/resources/css/ext-all.css" />

<!-- overrides to base library -->
<link rel="stylesheet" type="text/css"
	href="js/ext-3.0.0/examples/ux/gridfilters/css/GridFilters.css" />
<link rel="stylesheet" type="text/css"
	href="js/ext-3.0.0/examples/ux/gridfilters/css/RangeMenu.css" />

<link rel="stylesheet" type="text/css"
	href="js/ext-3.0.0/examples/shared/examples.css" />
<link rel="stylesheet" type="text/css"
	href="js/ext-3.0.0/examples/shared/icons/silk.css" />

<!-- LIBS -->
<script type="text/javascript"
	src="js/ext-3.0.0/adapter/ext/ext-base-debug.js"></script>
<!-- ENDLIBS -->
<script type="text/javascript" src="js/ext-3.0.0/ext-all-debug.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/shared/extjs/App.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/RowEditor.js"></script>

<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/menu/RangeMenu.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/menu/ListMenu.js"></script>

<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/GridFilters.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/Filter.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/StringFilter.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/DateFilter.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/ListFilter.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/NumericFilter.js"></script>
<script type="text/javascript"
	src="js/ext-3.0.0/examples/ux/gridfilters/filter/BooleanFilter.js"></script>


<link rel="stylesheet" type="text/css" href="restful.css" />
<link rel="stylesheet" href="js/ext-3.0.0/examples/ux/css/RowEditor.css" />

<script type="text/javascript" src="js/DataMaintain/match.js"></script>
<script type="text/javascript"
	src="js/DataMaintain/HistoryAnalyzeWindow.js"></script>
<script type="text/javascript"
	src="js/DataMaintain/SameMatchDifferenceCompanyAnalyzeWindow.js"></script>

<script type="text/javascript">
    var p_grid_title = "盘口数据输入";
    var p_title = "盘口数据";
    var p_readOnly = false;
    
    var p_restful_url = 'getAnalyzeResult4';
    var p_dataRoot = 'matches';
    
    loadAllProperty();

  
    
    var p_columns = [
          	{id:'id',header: "id", width: 60, fixed:true, sortable: true, locked:true, dataIndex: 'id', hidden:true

          	}
           ,{id:'date',header: "日期", width: 110, sortable: true,  dataIndex: 'date', renderer:Ext.util.Format.dateRenderer('Y-m-d H:i')
               ,editor:{xtype: 'timefield'
            	   
            	   , format: 'Y-m-d\\TH:i:s.uO'
            	   
               }   
               
           
           }
           
           ,{id:'country',header: "国家", width: 50, sortable: true,  dataIndex: 'country', storeKey:'country', renderer: renderProperty, hidden:true
        	   ,editor:{
        		   xtype: 'combo'
        		   ,store: getPropertyStore('country')
        		   ,valueField: 'id'
        		   ,displayField:'text'
        		   ,triggerAction:'all'
        	  }
           }           
           ,{id:'company',header: "公司", width: 50, sortable: true,  dataIndex: 'company', storeKey:'company', renderer: renderProperty
        	   ,editor:{
        		   xtype: 'combo'
        		   ,store: getPropertyStore('company')
        		   ,valueField: 'id'
            	   ,displayField:'text'
            	   ,triggerAction:'all'
        	  }

       	   }
           ,{id:'championship',header: "赛事", width: 100, sortable: true,  dataIndex: 'championship', storeKey:'championship', renderer: renderProperty
        	   ,editor:{
        		   xtype: 'combo',
        		   store: getPropertyStore('championship')
        		   ,valueField: 'id'
           		   ,displayField:'text'
           		   ,triggerAction:'all'

        	  }

           }
           ,{id:'level',header: "级别", width: 40, sortable: true,  dataIndex: 'level', filter:{type:'numeric'}, hidden:true}
           ,{id:'isInHome',header: "主场", width: 40, sortable: true,  dataIndex: 'isInHome', filter:{type:'boolean'}, hidden: true
				,editor:{
        		   
        		   //xtype: 'textfield'
        		   xtype: 'combo',
        		   
        		   store:['true','false']
        	   }   
           }
           
           ,{id:'homeTeam',dataIndex:'homeTeam',header: "主队", width: 100, sortable: true, storeKey:'team', renderer: renderTeamName
        	   ,editor:{
        		   xtype: 'combo',
        		   store: getPropertyStore('team')
        		   ,valueField: 'id'
           		   ,displayField:'text'
           		   ,triggerAction:'all'
        		   
        	  }

        	}
           ,{id:'homeTeamPosition',header: "主队排名", width: 20, sortable: true,  dataIndex: 'homeTeamPosition', filter:{type:'numeric'}, hidden: true
        	   , editor:{xtype:'numberfield'}
        	   
           }           
           ,{id:'awayTeam',dataIndex:'awayTeam',header: "客队", width: 100, sortable: true, storeKey:'team', renderer: renderTeamName
        	   ,editor:{
        		   xtype: 'combo',
        		   store: getPropertyStore('team')
        		   ,valueField: 'id'
           		   ,displayField:'text'
           		   ,triggerAction:'all'
        		   
        	  }

           }
           ,{id:'awayTeamPosition',header: "客队排名", width: 20, sortable: true,  dataIndex: 'awayTeamPosition', filter:{type:'numeric'}, hidden: true
        	   , editor:{xtype:'numberfield'}
        	   
           }           
           
           ,{id:'rankDiff',dataIndex:'rankDiff',header: "差", width: 30, sortable: true, hidden:false, align:'right'
        	   ,editor:{
        		   xtype: 'numberfield'
        		   
        	  }

        	   }          

           ,{id:'score',header: "比分", width: 50, sortable: true,  dataIndex: 'score', filter:{type:'string'}, css:'border-right:1px  solid  #FF0000;'
        	   ,editor:{
        		   xtype: 'textfield'
        		   
        	  }
           
           }


           ,{id:'euro_final_win',header: "欧赔_胜", width: 50, sortable: true,  dataIndex: 'euro_final_win', renderer:renderResult, hidden: true
        	   ,editor:{
        		   xtype: 'numberfield'    
            		   ,decimalPrecision:3        		   
        	  }
           }
           ,{id:'euro_final_standoff',header: "欧赔_平", width: 50, sortable: true,  dataIndex: 'euro_final_standoff', renderer:renderResult, hidden: true
        	   ,editor:{
        		   xtype: 'numberfield'   
            		   ,decimalPrecision:3        		   
        	  }

        	}           
           ,{id:'euro_final_loss',header: "欧赔_负", width: 50, sortable: true,  dataIndex: 'euro_final_loss', renderer:renderResult, css:'border-right:1px  solid  #FF0000;', hidden: true
        	   ,editor:{
        		   xtype: 'numberfield'  
            		   ,decimalPrecision:3        		   
        	  }
           
           }

           
           ,{id:'asia_final_up',dataIndex:'asia_final_up', header: "亚赔_上", width: 50, sortable: true, renderer:renderResult,  hidden: true}
           ,{id:'asia_final_ud_mouth',dataIndex:'asia_final_ud_mouth',header: "亚赔_盘口", width: 50, sortable: true, storeKey:'asia_ud_mouth', renderer: renderProperty,  hidden: true}           
           ,{id:'asia_final_down',dataIndex:'asia_final_down',header: "亚赔_下", width: 50, sortable: true, renderer:renderResult,  css:'border-right:1px  solid  #FF0000;',  hidden: true}

           
           ,{id:'euro_early_win',dataIndex:'euro_early_win', header: "欧初_胜", width: 50, sortable: true, renderer:renderResult, hidden: false
        	   ,editor:{
        		   xtype: 'numberfield'
        		   ,decimalPrecision:3
        	  }
        	   
           }
           ,{id:'euro_early_standoff',dataIndex:'euro_early_standoff',header: "欧初_平", width: 50, sortable: true, renderer:renderResult, hidden: false
        	   ,editor:{
        		   xtype: 'numberfield'   
        		   ,decimalPrecision:3
        	  }
   
           }           
           ,{id:'euro_early_loss',dataIndex:'euro_early_loss',header: "欧初_负", width: 50, sortable: true, renderer:renderResult,  css:'border-right:1px  solid  #FF0000;', hidden: false
        	   ,editor:{
        		   xtype: 'numberfield'  
        		   ,decimalPrecision:3
        	  }

           }           
           

           ,{id:'asia_early_up',dataIndex:'asia_early_up',header: "亚初_上", width: 50, sortable: true, renderer:renderResult,  hidden: true}
           ,{id:'asia_early_ud_mouth',dataIndex:'asia_early_ud_mouth',header: "亚初_盘口", width: 50, sortable: true, storeKey:'asia_ud_mouth', renderer: renderProperty,  hidden: true}           
           ,{id:'asia_early_down',dataIndex:'asia_early_down', header: "亚初_下", width: 50, sortable: true, renderer:renderResult, css:'border-right:1px  solid  #FF0000;',  hidden: true}           
           ,{id:'offerTime',header: "初盘时间", width: 80, sortable: true,  dataIndex: 'offerTime', filter:{type:'date'}, renderer:Ext.util.Format.dateRenderer('m-d H:i'), css:'border-right:1px  solid  #FF0000;', hidden:true
        	   

           }
           
           ,{id:'asia_final_big',dataIndex:'asia_final_big',header: "大小_大", width: 50, sortable: true, renderer:renderResult, hidden: true}
           ,{id:'asia_final_bs_mouth',dataIndex:'asia_final_bs_mouth',header: "大小_盘口", width: 50, sortable: true, storeKey:'asia_bs_mouth', renderer: renderProperty, hidden: true}           
           ,{id:'asia_final_small',dataIndex:'asia_final_small', header: "大小_小", width: 50, sortable: true, renderer:renderResult,  css:'border-right:1px  solid  #FF0000;', hidden: true}

           ,{id:'asia_early_big',dataIndex:'asia_early_big',header: "大小初_大", width: 50, sortable: true, renderer:renderResult, hidden: true}
           ,{id:'asia_early_bs_mouth',dataIndex:'asia_early_bs_mouth',header: "大小初_盘口", width: 50, sortable: true, storeKey:'asia_bs_mouth', renderer: renderProperty, hidden:  true}           
           ,{id:'asia_early_small',dataIndex:'asia_early_small',header: "大小初_小", width: 50, sortable: true, renderer:renderResult,  css:'border-right:1px  solid  #FF0000;', hidden:  true}           

           ,{id:'win_standoff_loss',dataIndex:'win_standoff_loss',header: "胜负平", width: 50, sortable: true
        	   ,editor:{
        		   xtype: 'textfield'        		   
        	  }
			}
           ,{id:'up_down',dataIndex:'up_down',header: "上下", width: 50, sortable: true, hidden:true
        	   ,editor:{
        		   xtype: 'textfield'        		   
        	  }
            }            
           ,{id:'big_small',dataIndex:'big_small',header: "大小", width: 50, sortable: true, hidden:  true
        	   ,editor:{
        		   xtype: 'textfield'        		   
        	  }

        	}           
           ,{id:'xing',dataIndex:'xing',header: "型", width: 40, sortable: true, hidden:  false
         	  ,editor:{
         		   
         		   //xtype: 'textfield'
         		   xtype: 'combo',
         		   
         		   store:['标','缩','涨']
         		   
         	   }
            }
           
           
           ,{id:'interval',dataIndex:'interval',header: "区", width: 25, sortable: true, hidden:  false
        	   ,editor:{
        		   xtype: 'textfield'        		   
        	  }

        	   }
           ,{id:'water_level',dataIndex:'water_level',header: "水", width: 25, sortable: true, hidden:  false
        	   ,editor:{
        		   xtype: 'textfield'        		   
        	  }

        	   }
           
           ,{id:'home_power',dataIndex:'home_power',header: "广主", width: 50, sortable: true, hidden:  false
        	  ,editor:{
        		   
        		   //xtype: 'textfield'
        		   xtype: 'combo',
        		   
        		   store:['人强','普强','准强','中强','中上','中游','中下','下游']
        		   
        	   }
           }
           ,{id:'away_power',dataIndex:'away_power',header: "广客", width: 50, sortable: true, hidden:  false
        	   ,editor:{
        		   
        		   //xtype: 'textfield'
        		   xtype: 'combo',
        		   
        		   store:['人强','普强','准强','中强','中上','中游','中下','下游']
        	   }

        	}
           
           ,{id:'fen',dataIndex:'fen',header: "分", width: 50, sortable: true, hidden:  false
        	   ,editor:{
        		   
        		   //xtype: 'textfield'
        		   xtype: 'combo',
        		   
        		   store:['逆','顺','中','缓']
        	   }

        	}
           
           ,{id:'pan',dataIndex:'pan',header: "盘", width: 50, sortable: true, hidden:  false
        	   ,editor:{
        		   
        		   //xtype: 'textfield'
        		   xtype: 'combo',
        		   
        		   store:['超','实','庸','韬']
        	   }

        	}
           
           ,{id:'remark',dataIndex:'remark',header: "备注", width: 500, sortable: true, hidden:  false
        	   ,editor:{
        		   
        		   xtype: 'htmleditor'
        		   //xtype: 'textfield'
        		   
        	   }
   
           }           
           
          
	];
    var p_filterConfig = p_matchFilterConfig;
	function p_buildForm() {
	 	return [];
	}
	
	var p_editor = new Ext.ux.grid.RowEditor({
	        saveText: 'Update',
	        frame: false
	    });		
	
	function p_buildForm2() {
        return [
        

	{
				xtype:"container",
				autoEl:"div",
				layout:"column",
				labelAlign:"left",
				autoHeight:true,
				autoWidth:true,
				defaults:{
				  columnWidth:0.5
				},
				items:[
					{
						xtype:"fieldset",
						title:"基本信息",
						layout:"form",
						padding:"0",
						items:[
							{
								xtype:"container",
								autoEl:"div",
								layout:"column",
								items:[
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelPad:"",
										labelWidth:50,
										items:[
											{
												xtype:"datefield",
												fieldLabel:"日期",
												anchor:"100%",
												name:'date', 
												columnWidth:5,
												width:128
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												triggerAction:"all",
												fieldLabel:"公司",
												anchor:"100%",
												store: getPropertyStore("company"),
												name: "company",
												lazyInit:false,
												forceSelection:true, 
												valueField: "id",
												displayField: "text",
												width:128
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												triggerAction:"all",
												fieldLabel:"赛事",
												store: getPropertyStore("championship"),
												forceSelection:true,
												anchor:"100%",
												name: "championship",
												valueField: "id",
												displayField: "text",
												width:128
											}
										]
									}
								]
							},
							{
								xtype:"container",
								autoEl:"div",
								layout:"column",
								items:[
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												triggerAction:"all",
												fieldLabel:"主队",
												store: getPropertyStore("team"),
												forceSelection:true,
												name: "homeTeam",
												valueField: "id",
												displayField: "text",
												lazyInit:false,
												anchor:"100%",
												width:128
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												triggerAction:"all",
												fieldLabel:"客队",
												lazyInit:false,
												name: "awayTeam",
												forceSelection:true,
												valueField: "id",
												displayField: "text",
												
												store: getPropertyStore("team"),
												anchor:"100%",
												width:128
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"textfield",
												fieldLabel:"比分",
												name:'score',
												id:'score', 
												vtype:'Score',  
												anchor:"100%",
												listeners:{
												  blur:function(score){
												  
												            if (!this.isValid()){return;}
												        
    	
        													matchResult = score.getValue();
        													
        													Ext.getCmp("win_standoff_loss").setValue(calculateWinStandoffLoss(matchResult));
        													
    	
                                                            var combo = Ext.getCmp("asia_final_ud_mouth");
                                                            if(combo.getValue()!=""){
               													up_down_mouth = combo.findRecord('id', combo.getValue()).get('text'); 
        														up_rate = Ext.getCmp("asia_final_up").getValue();
        														down_rate = Ext.getCmp("asia_final_down").getValue();    

        														Ext.getCmp("up_down").setValue(calculateUpDown(matchResult, up_down_mouth, up_rate, down_rate));
        													}
    	
        													combo = Ext.getCmp("asia_final_bs_mouth");
        													if(combo.getValue()!=""){
        	        											big_small_mouth = combo.findRecord('id', combo.getValue()).get('text');
    	        												Ext.getCmp("big_small").setValue(calculateBigSmall(matchResult, big_small_mouth));
    	        											}
    	
    											}
												  
												},

												width:128
											}
										]
									}
								]
							}
						]
					},
					{
						xtype:"fieldset",
						title:"欧赔",
						layout:"form",
						items:[
							{
								xtype:"container",
								autoEl:"div",
								layout:"column",
								items:[
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"欧赔胜",
												anchor:"100%",
												name:'euro_final_win',
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"欧初胜",
												anchor:"100%",
												name:'euro_early_win',
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"欧赔平",
												anchor:"100%",
												name:'euro_final_standoff',
												
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"欧初平",
												anchor:"100%",
												name:'euro_early_standoff',
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"欧赔负",
												name:'euro_final_loss',
												anchor:"100%",
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"欧初负",
												name:'euro_early_loss',
												anchor:"100%",
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"textfield",
												fieldLabel:"胜负平",
												name:'win_standoff_loss',
												id:'win_standoff_loss',
												anchor:"100%",
												readOnly:true,
												width:58
											}
										]
									}
								]
							}
						]
					}
				]
			},
			{
				xtype:"container",
				autoEl:"div",
				layout:"column",
				defaults:{
				  columnWidth:0.5
				},
				
				autoWidth:true,
				autoHeight:true,
				items:[
					{
						xtype:"fieldset",
						title:"亚赔",
						layout:"form",
						items:[
							{
								xtype:"container",
								autoEl:"div",
								layout:"column",
								autoWidth:true,
								autoHeight:false,
								items:[
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										columnWidth:"",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"亚赔上",
												anchor:"100%",
												name:'asia_final_up',
												id:'asia_final_up',
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"亚初上",
												anchor:"100%",
												name:'asia_early_up',
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										columnWidth:"",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												fieldLabel:"亚赔盘",
												triggerAction:"all",
												store: getPropertyStore("asia_ud_mouth"),
												anchor:"100%",
												name: "asia_final_ud_mouth",
												id: "asia_final_ud_mouth",
												forceSelection:true,
												valueField: "id",
												displayField: "text",
												
												width:58
											},
											{
												xtype:"combo",
												fieldLabel:"亚初盘",
												triggerAction:"all",
												store: getPropertyStore("asia_ud_mouth"),
												name: "asia_early_ud_mouth",
												forceSelection:true,
												valueField: "id",
												displayField: "text",

												anchor:"100%",
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"亚赔下",
												name:'asia_final_down',
												id:'asia_final_down',
												anchor:"100%",
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"亚初下",
												anchor:"100%",
												name:'asia_early_down',
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										height:"",
										width:"",
										items:[
											{
												xtype:"textfield",
												fieldLabel:"上下",
												anchor:"100%",
												name:'up_down',
												id:'up_down',
												readOnly: true,
												width:58
											}
										]
									}
								]
							}
						]
					},
					{
						xtype:"fieldset",
						title:"大小",
						layout:"form",
						items:[
							{
								xtype:"container",
								autoEl:"div",
								layout:"column",
								items:[
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										columnWidth:"",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"大小大",
												name:'asia_final_big',
												anchor:"100%",
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"大初大",
												name:'asia_early_big',
												anchor:"100%",
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"combo",
												fieldLabel:"大小盘",
												triggerAction:"all",
												store: getPropertyStore("asia_bs_mouth"),
												name: "asia_final_bs_mouth",
												id: "asia_final_bs_mouth",
												valueField: "id",
												displayField: "text",
												forceSelection:true,

												anchor:"100%",
												width:58
											},
											{
												xtype:"combo",
												fieldLabel:"大初盘",
												triggerAction:"all",
												store: getPropertyStore("asia_bs_mouth"),
												name: "asia_early_bs_mouth",
												valueField: "id",
												displayField: "text",
												forceSelection:true,

												anchor:"100%",
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"numberfield",
												fieldLabel:"大小小",
												anchor:"100%",
												name:'asia_final_small',
												width:58
											},
											{
												xtype:"numberfield",
												fieldLabel:"大初小",
												anchor:"100%",
												name:'asia_early_small',
												width:58
											}
										]
									},
									{
										xtype:"container",
										autoEl:"div",
										layout:"form",
										labelWidth:50,
										items:[
											{
												xtype:"textfield",
												fieldLabel:"大小",
												anchor:"100%",
												name:'big_small',
												id:'big_small',
												readOnly: true,
												width:58
											}
										]
									}
								]
							}
						]
					}
				]
			}
			];
            

    }
	
    </script>
<script type="text/javascript" src="js/DataMaintain/matchAnalyze4.js"></script>
<script type="text/javascript" src="js/DataMaintain/writer.js"></script>
<script type="text/javascript" src="js/DataMaintain/UserForm.js"></script>
<script type="text/javascript" src="js/DataMaintain/export2Excel.js"></script>
<script type="text/javascript" src="js/DataMaintain/UserGrid.js"></script>


</head>
<body>
	<div id=client></div>
</body>
</html>