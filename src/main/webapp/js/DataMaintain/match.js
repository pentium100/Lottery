

Ext.BLANK_IMAGE_URL = 'js/ext-3.0.0/resources/images/default/s.gif';

var p_propertyRecordType = new Ext.data.Record.create([
                    						{name: 'id'},
                       						{name: 'text', allowBlank: false, type:'string'},
                       						{name: 'propertyType_id', allowBlank: false, type:'int', mapping:"propertyType_id"}
]);


var p_matchRecordType = new Ext.data.Record.create([
                   							 {name: 'id', allowBlank: true, type:'int'}
                   							,{name: 'id2', allowBlank: true, type:'int'}
                   							,{name: 'date', allowBlank: false, type:'date', dateFormat:'Y-m-d\TH:i:sP'}
                      						,{name: 'company', allowBlank: false, type:'int', storeKey:'company'}
                      						,{name: 'country', allowBlank: false, type:'int', storeKey:'country'}
                      						,{name: 'championship', allowBlank: false, type:'int',  storeKey:'championship'}
                      						
                      						,{name: 'score', allowBlank: true, type:'string'}
                      						,{name: 'level', allowBlank: true, type:'int'}
                      						,{name: 'isInHome', allowBlank: true, type:'boolean'}
                      						,{name: 'offerTime', allowBlank: true, type:'date', dateFormat:'Y-m-d\TH:i:sP'}
                      						,{name: 'euro_final_win', allowBlank: true, type:'float'}
                      						,{name: 'euro_final_standoff', allowBlank: true, type:'float'}
                      						,{name: 'euro_final_loss', allowBlank: true, type:'float'}

                      						,{name: 'euro_early_win', allowBlank: true, type:'float'}
                      						,{name: 'euro_early_standoff', allowBlank: true, type:'float'}
                      						,{name: 'euro_early_loss', allowBlank: true, type:'float'}

                      						,{name: 'asia_final_up', allowBlank: true, type:'float'}
                      						,{name: 'asia_final_ud_mouth', allowBlank: true, type:'int', storeKey:'asia_ud_mouth'}
                      						,{name: 'asia_final_down', allowBlank: true, type:'float'}

                      						,{name: 'asia_early_up', allowBlank: true, type:'float'}
                      						,{name: 'asia_early_ud_mouth', allowBlank: true, type:'int', storeKey:'asia_ud_mouth'}
                      						,{name: 'asia_early_down', allowBlank: true, type:'float'}

                      						,{name: 'asia_final_big', allowBlank: true, type:'float'}
                      						,{name: 'asia_final_bs_mouth', allowBlank: true, type:'int', storeKey:'asia_bs_mouth'}
                      						,{name: 'asia_final_small', allowBlank: true, type:'float'}

                      						,{name: 'asia_early_big', allowBlank: true, type:'float'}
                      						,{name: 'asia_early_bs_mouth', allowBlank: true, type:'int', storeKey:'asia_bs_mouth'}
                      						,{name: 'asia_early_small', allowBlank: true, type:'float'}

                      						,{name: 'homeTeam', allowBlank: false, type:'int', storeKey:'team'}
                      						,{name: 'awayTeam', allowBlank: false, type:'int', storeKey:'team'}
                      						
                      						,{name: 'homeTeamPosition', allowBlank: false, type:'int'}
                      						,{name: 'awayTeamPosition', allowBlank: false, type:'int'}
                      						,{name: 'rankDiff', allowBlank: false, type:'int'}

                      						,{name: 'win_standoff_loss', allowBlank: true, type:'string'}
                      						,{name: 'up_down', allowBlank: true, type:'string'}
                      						,{name: 'big_small', allowBlank: true, type:'string'}    						
                      						,{name: 'asia_win', allowBlank: true, type:'string'}

                      						,{name: 'interval', allowBlank: true, type:'string'}    						
                      						,{name: 'water_level', allowBlank: true, type:'string'}
                      						,{name: 'xing', allowBlank: true, type:'string'}
                      						,{name: 'fen', allowBlank: true, type:'string'}    						
                      						,{name: 'pan', allowBlank: true, type:'string'}

                      						,{name: 'home_power', allowBlank: true, type:'string'}    						
                      						,{name: 'away_power', allowBlank: true, type:'string'}
                      						,{name: 'remark', allowBlank: true, type:'string'}
                      						
                      						
                      
                      					]);      


var p_propertyDataRoot = 'properties';
                       
// Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
var propertyReader = new Ext.data.JsonReader({
    totalProperty: 'total',
    successProperty: 'success',
    idProperty: 'id',
    root: p_propertyDataRoot
	}, p_propertyRecordType    
);

var propertyStores = new Ext.util.MixedCollection();

var propertyArray = new Array();
propertyArray["championship"] = 1;
propertyArray["team"] = 2;
propertyArray["asia_ud_mouth"] = 3;
propertyArray["asia_bs_mouth"] = 4;
propertyArray["company"] = 5;
propertyArray["country"] = 6;
function loadAllProperty(){
	
	
	getPropertyStore("championship");
	getPropertyStore("team");
	getPropertyStore("asia_ud_mouth");
	getPropertyStore("asia_bs_mouth");
	getPropertyStore("company");
	getPropertyStore("country");
}
function renderProperty(value, metadata, record, rowIndex, colIndex, store ){

	
	//var storeKey = store.fields.get(metadata.id).storeKey;
	if(this.storeKey != undefined){
		var storeKey = this.storeKey;
	}else
	{
		var storeKey = metadata.storeKey;
	}
	var propertyStore = getPropertyStore(storeKey);
	
	var index = propertyStore.findExact('id', value);


	return -1==index ? value : propertyStore.getAt(index).data.text;

}
function renderTeamName(value, metadata, record, rowIndex, colIndex, store ){
	
	//var teamName = renderProperty(value, metadata, record, rowIndex, colIndex, store);
	
	var propertyStore = getPropertyStore('team');
	
	var index = propertyStore.findExact('id', value);


	var teamName = -1==index ? value : propertyStore.getAt(index).data.text;
	
	
	if(!record.get("isInHome")){
		teamName=teamName+"[中]";
	}
	if(record.get(metadata.id+"Position") != 0){
		return teamName+"["+record.get(metadata.id+"Position")+"]";
	}else{
		return teamName;
	}
	
	
	
	
	
}

function renderRankDiff(value, metadata, record, rowIndex, colIndex, store ){

    //debugger;
	return record.get("awayTeamPosition") - record.get("homeTeamPosition");
	
    //return record.fields.get("awayTeamPosition");

}


function getPropertyStore(propertyType){
		
	if(propertyStores.item(propertyType)==null){

		propertyStores.add(propertyType, initPropertyStore(propertyArray[propertyType]));
		propertyStores.item(propertyType).load();
		
	}
	
	
	return propertyStores.item(propertyType);
	
	
}


function initPropertyStore(propertyType){
	
	var proxy = new Ext.data.HttpProxy({
		url: '../restful/properties/'+propertyType
	});

	// Typical Store collecting the Proxy, Reader and Writer together.
	var propertyStore = new Ext.data.Store({
	    id: 'peoprtyStore'+propertyType,
	    proxy: proxy,
	    reader: propertyReader,
	    restful: true, 
	    listeners: {
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
	
	return propertyStore;
	
}


function getAllPropertyStore(){
	
	  //propertyStores.add("championship", initPropertyStore(1));
	  //propertyStores.add("team", initPropertyStore(2));
	  //propertyStores.add("asia_ud_mouth", initPropertyStore(3));
	  //propertyStores.add("asia_bs_mouth", initPropertyStore(4));
	  //propertyStores.add("company", initPropertyStore(5));
	  //getPropertyStore("championship").load();
	  //getPropertyStore("team").load();
	  //getPropertyStore("asia_ud_mouth").load();
	  //getPropertyStore("asia_bs_mouth").load();
	  //getPropertyStore("company").load();
	  


		
	}


//getAllPropertyStore();

Ext.apply(Ext.form.VTypes, {
    Score:  function(v) {
        return /^\d{1,2}\:\d{1,2}$/.test(v);
    },
    ScoreText: '请输入合法的比分，如3:1',
    ScoreMask: /[\d\:]/i
});



function getScore(matchResult)
{
	var score = matchResult.match(/^(\d{1,2})\:(\d{1,2})/);
	if(matchResult==""){return null;}
	if (score.length !=3){
		return null		
	}
	score[0]=parseInt(score[1]);
	score[1]=parseInt(score[2]);
	return score;
}
function calculateWinStandoffLoss(matchResult){
	if(matchResult==""){return null;}
    score = getScore(matchResult);
	if (score==null){return null;}
	if (score[0]>score[1])
		return '胜';
	if (score[0]==score[1])
		return '平';
	if (score[0]<score[1])
		return '负';
	
}

function calculateUpDown(matchResult, up_down_mouth, up_rate, down_rate){
	
    score = getScore(matchResult);
	if (score==null){return null;}
    
    if(up_down_mouth == '0'){   //盘口为平手时
    	
    	if(score[0]==score[1]){  //结果平时,为平手
    		return "平";
    	}
    	
    	if(score[0]>score[1]){  //主队WIN时
    		if(up_rate<=down_rate){
    			return "上";
    		}
    		else{
    			return "下";
    		}
    	}
    	
    	if(score[0]<score[1]){  //客队WIN时
    		if(up_rate>=down_rate){
    			return "下";
    		}
    		else{
    			return "上";
    		}
    	}
    	
    	
    }
    
    mouth = /^受\w*$/.test(up_down_mouth);
    if(!mouth){   //盘口为让球时
    	if(score[0]>score[1]){
    		return "上";
    	}
    	else{
    		return "下";
    	}
    	
    }
    if(mouth){   //盘口为受让球时
    	
    	if(score[0]<score[1]){
    		return "上";
    	}
    	else{
    		return "下";
    	}
    	
    }
    
	
}


function calculateBigSmall(matchResult, big_small_mouth){
	if(matchResult==""){return null;}
	if(big_small_mouth==""){return null;}
   score = getScore(matchResult);
   if (score==null){return null;}
  
   var mouth2 = big_small_mouth.match(/^(\d{1}|\d{1}\.\d{1})\/(\d{1}|\d{1}\.\d{1})$/);
   var mouth = 100.10;
   var totalScore = parseInt(score[0])+parseInt(score[1]);
   if (mouth2!=null){
	   	mouth = parseFloat(mouth2[2]);
	   }
   		else
	   {
   		mouth = parseFloat(big_small_mouth);
   }
   if (totalScore>mouth){return "大";}
   if (totalScore==mouth){return "平";}
   if (totalScore<mouth){return "小";}
	
}


function renderPct(val, metadata, record, rowIndex, colIndex, store ){
   return val+'%';	
}

function renderResult(val, metadata, record, rowIndex, colIndex, store ){
	
   if((record.data.big_small=='大') &&(/^asia_(final|early)_big$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }

   if((record.data.big_small=='小') && (/^asia_(final|early)_small$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }
   
   if((record.data.win_standoff_loss=='胜') && (/^euro_(final|early)_win$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }
   
   if((record.data.win_standoff_loss=='负') && (/^euro_(final|early)_loss$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }

   if((record.data.win_standoff_loss=='平') && (/^euro_(final|early)_standoff$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }

   if((record.data.asia_win=='主') && (/^asia_(final|early)_up$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }
   if((record.data.asia_win=='客') && (/^asia_(final|early)_down$/.test(metadata.id))){
	   return "<span style='color:red;'>"+val+"</span>";
	   
   }
   if(record.data.score!=""){   
	   return "<span style='color:blue;'>"+val+"</span>";
   }
   return val;
}

g_numberFilterCfg = {
		lt : {decimalPrecision: 3},
		gt : {decimalPrecision: 3},
		eq : {decimalPrecision: 3}
}

var p_matchFilterConfig =[
                        	
        					{
                	dataIndex: 'date',
                	type:'date'
                	},
                	{
                    	dataIndex: 'offerTime',
                    	type:'date'
                    }, 
                	{
                		dataIndex: 'company',
                		type:'list',
                		store:getPropertyStore('company')
                	}, 
                	{
                		dataIndex: 'id2',
                		type:'list',
                		store:getPropertyStore('company')
                	}, 
                	{
                		dataIndex: 'championship',
                		type:'list',
                		store:getPropertyStore('championship')
                	}, 
                	{
                		dataIndex: 'country',
                		type:'list',
                		store:getPropertyStore('country')
                	},
                	{
                		dataIndex: 'homeTeam',
                		type:'list',
                		store:getPropertyStore('team')
                	},
                	
                	{
                		dataIndex: 'awayTeam',
                		type:'list',
                		store:getPropertyStore('team')                		
                	},
                	
                	{
                		dataIndex: 'level',
                		type:'numeric'

                		
                	},

                	{
                		dataIndex: 'rankDiff',
                		type:'numeric'

                		
                	},

                	{
                		dataIndex: 'score',
                		type:'string'

                		
                	},        	

                	{
                		dataIndex: 'isInHome',
                		type:'boolean'

                		
                	},        	
                	{
                		dataIndex: 'euro_final_win',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                		
                	}

        					,					
                	{
                		dataIndex: 'euro_final_standoff',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                	}
                	,					
                	{
                		dataIndex: 'euro_final_loss',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                	}
                	,					
                	{
                		dataIndex: 'euro_early_win',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                	}
                	,					
                	{
                		dataIndex: 'euro_early_standoff',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                	}
                	,					
                	{
                		dataIndex: 'euro_early_loss',
                		type:'numeric',
                		fieldCfg : g_numberFilterCfg
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_up',
                		type:'numeric',
                		decimalPrecision: 3
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_down',
                		type:'numeric',
                		decimalPrecision: 3
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_ud_mouth',
                		type:'list',
                		store:getPropertyStore('asia_ud_mouth')
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_up',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_down',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_ud_mouth',
                		type:'list',
                		store:getPropertyStore('asia_ud_mouth')
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_big',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_small',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_final_bs_mouth',
                		type:'list',
                		store:getPropertyStore('asia_bs_mouth')
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_big',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_small',
                		type:'numeric'
                		
                	}        	        	
                	,					
                	{
                		dataIndex: 'asia_early_bs_mouth',
                		type:'list',
                		store:getPropertyStore('asia_bs_mouth')
                		
                	}        	        	


                	,					
                	{
                		dataIndex: 'win_standoff_loss',
                		type:'list',
                		options:['胜', '平','负']
                	}        	        	
                	,					
                	{
                		dataIndex: 'big_small',
                		type:'list',
                		options:['大', '平','小']
                	}        	        	
                	,	 				
                	{
                		dataIndex: 'up_down',
                		type:'list',
                		options:['上', '平','下']
                	}        	        	
                	,	 				
                	{
                		dataIndex: 'interval',
                		type:'list',
                		options:['0','1','2','3','4','5','6','7','8','9']
                		
                	}
                	,	 				
                	{
                		dataIndex: 'home_power',
                		type:'list',
                		options:['人强','普强','准强','中强','中上','中游','中下','下游']
                		
                	}
                	,	 				
                	{
                		dataIndex: 'away_power',
                		type:'list',
                		options:['人强','普强','准强','中强','中上','中游','中下','下游']
                		
                	}
                	,	 				
                	{
                		dataIndex: 'fen',
                		type:'list',
                		options:['逆','顺','中','缓']
                		
                	},	 				
                	{
                		dataIndex: 'xing',
                		type:'list',
                		options:['标','缩','涨']
                		
                	}
                	,	 				
                	{
                		dataIndex: 'pan',
                		type:'list',
                		options:['超','实','庸','韬']
                		
                	}
                	,	 				
                	{
                		dataIndex: 'water_level',
                		type:'list',
                		options:['A', 'B','C','D']
                	}        	        	
        	
        	
        	];

