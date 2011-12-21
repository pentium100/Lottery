<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>2.37赔率主/客队数据分析</title>

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
    
    var p_restful_url = 'getAnalyzeResult2.do';
    var p_recordType = new Ext.data.Record.create([
 							 {name: 'euro_final_loss', type:'float'}
 							,{name: 'euro_final_win', type:'float'}
 							,{name: 'win_count', type:'int'}
 							,{name: 'standoff_count', type:'int'}
 							,{name: 'loss_count', type:'int'}
 							,{name: 'total_count', type:'int'}

 							,{name: 'win_pct', type:'float'}
 							,{name: 'standoff_pct', type:'float'}
 							,{name: 'loss_pct', type:'float'}
    						
    
    					]);      
    var p_dataRoot = '';
                       
    
    var p_columns = [
            {id:'euro_final_loss',header: "欧赔客队", width: 15, sortable: true,  dataIndex: 'euro_final_loss', groupable:true }
           ,{id:'euro_final_win',header: "欧赔主队", width: 15, sortable: true,  dataIndex: 'euro_final_win', groupable:true }
           ,{id:'win_count',header: "胜(次)", width: 10, sortable: true,  dataIndex: 'win_count', align:'right', summaryType:'sum'}
           ,{id:'standoff_count',header: "平(次)", width: 10, sortable: true,  dataIndex: 'standoff_count', align:'right', summaryType:'sum'}
           ,{id:'loss_count',header: "负(次)", width: 10, sortable: true,  dataIndex: 'loss_count', align:'right', summaryType:'sum'}
           ,{id:'total_count',header: "总计", width: 10, sortable: true,  dataIndex: 'total_count', align:'right', summaryType:'sum'}           
           ,{id:'win_pct',header: "胜(百分比)", width: 10, sortable: true,  dataIndex: 'win_pct', renderer:renderPct, align:'right', summaryType:'totalAvg'}
           ,{id:'standoff_pct',header: "平(百分比)", width: 10, sortable: true, dataIndex: 'standoff_pct', renderer:renderPct, align:'right', summaryType:'totalAvg'}
           ,{id:'loss_pct',header: "负(百分比)", width: 10, sortable: true, dataIndex:'loss_pct', renderer:renderPct, align:'right', summaryType:'totalAvg'}
           
          
          
	];
	
	p_sortInfo = {field: 'euro_final_win', direction: 'ASC'};
	//p_sortInfo = {field: 'big_count', direction: 'ASC'};
	p_groupField = ['euro_final_win'];
	
    Ext.ux.grid.GroupSummary.Calculations['totalAvg'] = function(v, record, colName, data, rowIdx){
        var col2 = '';
 		if(colName=='win_pct')
 		{
 			col2 = 'win_count';
 		}
 		if(colName=='standoff_pct')
 		{
 			col2 = 'standoff_count';
 		}   
 		if(colName=='loss_pct')
 		{
 			col2 = 'loss_count';
 		}    		
 		 		   
        return data[col2]/data['total_count']*100;
    };
    	

	
    Ext.ux.grid.GridSummary.Calculations['totalAvg'] = function(v, record, colName, data, rowIdx){
        var col2 = '';
 		if(colName=='win_pct')
 		{
 			col2 = 'win_count';
 		}
 		if(colName=='standoff_pct')
 		{
 			col2 = 'standoff_count';
 		}   
 		if(colName=='loss_pct')
 		{
 			col2 = 'loss_count';
 		}    		
 		 		   
        return data[col2]/data['total_count']*100;
    };
    	
	
	
	
</script>    
	<script type="text/javascript" src="js/DataMaintain/matchAnalyze2.js"></script>    
</head>
<body>
<div id=client22></div>
</body>
</html>