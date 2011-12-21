/**
 * Copyright (c) 2000-2002 Amoydesign Studio
 * by rongwei 
 * Copyright (c) 2002 amoydesign studio
 * URL:   http://www.amoydesign.com
 * E_MAIL: rongwei@amoydesign.com
 * M.T. :  0 13806032020     86-592-8840200
 * designer: rongwei
 */

function viewMediaPlayer(sURL, sName, sFeatures){ 
	var WMPCID = "CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"; 
	var sFeature; 
	var sWidth  = "100%"; 
	var sHeight  = "100%"; 
	var pmBoolean; 
	var sTempArray; 
	var sParamTag = ""; 

	sFeature = sFeatures.split(/\s*,\s*/); 
	for (var i=0; i< sFeature.length ; i++) { 
		sTempArray = sFeature[i].split(/\s*=\s*/); 
		if (sTempArray[0].toLowerCase() == "width"){ 
			//3Dàì 
			sWidth = sTempArray[1]; 
		} else if (sTempArray[0].toLowerCase() == "height"){ 
			sHeight = sTempArray[1]; 
		} else { 
			if (sTempArray[1].toLowerCase() == "yes" || sTempArray[1] == "1" || sTempArray[1].toLowerCase() == "true"){ 
				pmBoolean = "true"; 
			} else if (sTempArray[1].toLowerCase() == "no" || sTempArray[1] == "0" || sTempArray[1].toLowerCase() == "false"){ 
				pmBoolean = "false"; 
			} else { 
				pmBoolean = sTempArray[1]; 
			}
			sParamTag = "<PARAM NAME='"+sTempArray[0]+"'VALUE='" + pmBoolean + "'>\n"+sParamTag; 
		}
	} 
	document.write("<OBJECT ID='"+sName+"' NAME='"+sName+"' CLASSID='"+WMPCID+"' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"' >"); 
	document.write("<PARAM NAME='URL' VALUE='" + sURL + "'>"); 
	document.write(sParamTag); 
	document.write("</OBJECT>"); 
} 
function  viewFlash(sURL, sName, sFeatures){ 
	var FLASHCAB = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"; 
	var FLASHCID = "CLSID:D27CDB6E-AE6D-11CF-96B8-444553540000"; 
	var FLASHVER = "8,0,0,0"; 
	var sFeature; 
	var sWidth  = "100%"; 
	var sHeight  = "100%"; 
	var pmBoolean = "false"; 
	var sTempArray; 
	var sParamTag = ""; 

	sFeature = sFeatures.split(/\s*,\s*/); 
	for (var i=0; i< sFeature.length ; i++) { 
		sTempArray = sFeature[i].split(/\s*=\s*/); 
		if (sTempArray[0].toLowerCase() == "width"){ 
			//3Dàì 
			sWidth = sTempArray[1]; 
		} else if (sTempArray[0].toLowerCase() == "height"){ 
			sHeight = sTempArray[1]; 
		} else { 
			if (sTempArray[1].toLowerCase() == "yes" || sTempArray[1] == "1" || sTempArray[1].toLowerCase() == "true"){ 
				pmBoolean = "true"; 
			} else if (sTempArray[1].toLowerCase() == "no" || sTempArray[1] == "0" || sTempArray[1].toLowerCase() == "false"){ 
				pmBoolean = "false"; 
			} else { 
				pmBoolean = sTempArray[1]; 
			} 
			sParamTag = "<PARAM NAME='"+sTempArray[0]+"'VALUE='" + pmBoolean + "'>\n"+sParamTag; 
		} 
	} 
	document.write("<OBJECT ID='"+sName+"' NAME='"+sName+"' CLASSID='"+FLASHCID+"' CODEBASE='"+FLASHCAB+"#version="+FLASHVER+"' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"'>"); 
	document.write("<PARAM NAME='movie' VALUE='" + sURL + "'>"); 
	document.write("<PARAM NAME='wmode' VALUE='transparent'>"); 
	document.write("<PARAM NAME='wmode' VALUE='wmode'>"); 
	document.write(sParamTag); 
	document.write("<EMBED SRC='"+sURL+"' MENU='false' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"' ID='"+sName+"' NAME='"+sName+"' TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/go/getflashplayer' />") 
	document.write("</OBJECT>"); 
}



function setFocus() 
{
 document.getElementById('employeeID').focus()
 }
 
function checkLogin() {
    if(document.loginForm.employeeID.value==""){
      alert("请输入用户ID！");
      document.loginForm.employeeID.focus();
      return false;
    }
    else if(document.loginForm.password.value==""){
      alert("请输入用户密码！");
      document.loginForm.password.focus();
      return false;
    }
    return true;
  }




function indexpop(page) { 
window.open(page,"_blank","width=550, height=400, scrollbars=1, toolbar=0, resizable=1"); 
//window.focus(); 
return; 
} 

function trade(page) { 
window.open(page,"_blank","width=800, height=600, scrollbars=1, toolbar=0, resizable=1"); 
//window.focus(); 
return; 
} 

function news(page) { 
window.open(page,"_blank","width=800, height=600, scrollbars=1, toolbar=0, resizable=1"); 
//window.focus(); 
return; 
} 


function hr(page) { 
window.open(page,"_blank","width=782, height=500, scrollbars=1, toolbar=0, resizable=1"); 
//window.focus(); 
return; 
} 


function law(page) { 
window.open(page,"_blank","width=600, height=400, scrollbars=0, toolbar=0, resizable=0"); 
//window.focus(); 
return; 
} 

function ry(page) { 
window.open(page,"_blank","width=600, height=600, scrollbars=1, toolbar=0, resizable=0"); 
//window.focus(); 
return; 
} 

function show(page) { 
window.open(page,"_blank","width=750, height=600, scrollbars=1, toolbar=0, resizable=0"); 
//window.focus(); 
return; 
} 

function sendMailTo(name, company, domain1, domain2) {
   locationstring = 'mai' + 'lto:' + name + '@' + company + '.' + domain1+ '.' + domain2;
   window.location.replace(locationstring);
}

<!--
var version = "other"
browserName = navigator.appName;   
browserVer = parseInt(navigator.appVersion);

if (browserName == "Netscape" && browserVer >= 3) version = "n3";
else if (browserName == "Netscape" && browserVer < 3) version = "n2";
else if (browserName == "Microsoft Internet Explorer" && browserVer >= 4) version = "e4";
else if (browserName == "Microsoft Internet Explorer" && browserVer < 4) version = "e3";


function marquee1()
{
	if (version == "e4")
	{
		document.write("<marquee style='BOTTOM: 0px; FONT-WEIGHT: 436px; HEIGHT:90;  TEXT-ALIGN: left; TOP: 0px' id='news' scrollamount='1' scrolldelay='10' behavior='loop' direction='up' border='0' onmouseover='this.stop()' onmouseout='this.start()'>")
	}
}

function marquee2()
{
	if (version == "e4")
	{
		document.write("</marquee>")
	}
}

function marquee3()
{
	if (version == "e4")
	{
		document.write("<marquee style=' FONT-WEIGHT: 140px;   TEXT-ALIGN: left' id='news' scrollamount='2' scrolldelay='10' behavior='loop'  border='0' onmouseover='this.stop()' onmouseout='this.start()'>")
	}
}

function marquee4()
{
	if (version == "e4")
	{
		document.write("</marquee>")
	}
}

function hrView(id){
  openWindow("hr_detail.asp?id="+id,"Address",750,580);
}



function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function viewitem(id,cate){
    tempform.action="E-viewNewsItem.jsp";
    tempform.itemid.value = id;
    tempform.category.value = cate;
    tempform.submit();
  }

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

