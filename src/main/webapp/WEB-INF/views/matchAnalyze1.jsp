<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欧赔2.37盘口数据分析</title>

    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/examples.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/shared/icons/silk.css" />
    <link rel="stylesheet" type="text/css" href="js/ext-3.0.0/examples/ux/css/Ext.ux.grid.GridSummary.css" />
    
    <!-- LIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/adapter/ext/ext-base-debug.js"></script>
    <!-- ENDLIBS -->
    <script type="text/javascript" src="js/ext-3.0.0/ext-all-debug.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/ux/Ext.ux.grid.GridSummary.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/ux/MultiGrouping.js"></script>
    <script type="text/javascript" src="js/ext-3.0.0/examples/ux/GroupSummary.js"></script>    
	<script type="text/javascript" src="js/DataMaintain/match.js"></script>
   
    <script type="text/javascript">

    var p_readOnly = false;
    
    var p_restful_url = 'getAnalyzeResult1.do';
    var p_recordType = new Ext.data.Record.create([
 							 {name: 'euro_mouth', type:'string'}
 							,{name: 'asia_final_ud_mouth', type:'int', storeKey:'asia_ud_mouth'}
 							,{name: 'big_count', type:'int'}
 							,{name: 'standoff_count', type:'int'}
 							,{name: 'small_count', type:'int'}
 							,{name: 'total_count', type:'int'}
 							,{name: 'big_pct', type:'float'}
 							,{name: 'standoff_pct', type:'float'}
 							,{name: 'small_pct', type:'float'} 							
    						
    
    					]);      
    var p_dataRoot = '';
    
    var p_columns = [
            {id:'euro_mouth',header: "欧赔盘口", width: 15, sortable: true,  dataIndex: 'euro_mouth',groupable:true }
           ,{id:'asia_final_ud_mouth',header: "亚赔盘口", width: 15, sortable: true,  dataIndex: 'asia_final_ud_mouth', renderer: renderProperty,groupable:true}
           ,{id:'big_count',header: "大(次)", width: 10, sortable: true,  dataIndex: 'big_count', align:'right', summaryType : 'sum'}
           ,{id:'standoff_count',header: "平(次)", width: 10, sortable: true,  dataIndex: 'standoff_count', align:'right', summaryType : 'sum'}
           ,{id:'small_count',header: "小(次)", width: 10, sortable: true,  dataIndex: 'small_count', align:'right', summaryType : 'sum'}
           ,{id:'total_count',header: "总计", width: 10, sortable: true,  dataIndex: 'total_count', align:'right', summaryType : 'sum'}           
           ,{id:'big_pct',header: "大(百分比)", width: 10, sortable: true,  dataIndex: 'big_pct', renderer:renderPct, align:'right', summaryType : 'totalAvg'}
           ,{id:'standoff_pct',header: "平(百分比)", width: 10, sortable: true, dataIndex: 'standoff_pct', renderer:renderPct, align:'right', summaryType : 'totalAvg'}
           ,{id:'small_pct',header: "小(百分比)", width: 10, sortable: true, dataIndex:'small_pct', renderer:renderPct, align:'right', summaryType : 'totalAvg'}
           
          
          
	];
	
	p_sortInfo = {field: 'euro_mouth', direction: 'ASC'};
	//p_sortInfo = {field: 'big_count', direction: 'ASC'};
	p_groupField = ['euro_mouth'];

    Ext.ux.grid.GroupSummary.Calculations['totalAvg'] = function(v, record, colName, data, rowIdx){
        var col2 = '';
 		if(colName=='big_pct')
 		{
 			col2 = 'big_count';
 		}
 		if(colName=='standoff_pct')
 		{
 			col2 = 'standoff_count';
 		}   
 		if(colName=='small_pct')
 		{
 			col2 = 'small_count';
 		}    		
 		 		   
        return data[col2]/data['total_count']*100;
    };
    	

	
    Ext.ux.grid.GridSummary.Calculations['totalAvg'] = function(v, record, colName, data, rowIdx){
        var col2 = '';
 		if(colName=='big_pct')
 		{
 			col2 = 'big_count';
 		}
 		if(colName=='standoff_pct')
 		{
 			col2 = 'standoff_count';
 		}   
 		if(colName=='small_pct')
 		{
 			col2 = 'small_count';
 		}    		
 		 		   
        return data[col2]/data['total_count']*100;
    };
    	
	
	
</script>    
	<script type="text/javascript" src="js/DataMaintain/matchAnalyze1.js"></script>    
</head>
<body>
<div id=client22></div>
</body>
</html>