var Base64 = (function() {

    // private property
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // private method for UTF-8 encoding
    function utf8Encode(string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }
        return utftext;
    }

    // public method for encoding
    return {
        encode : (typeof btoa == 'function') ? function(input) { return btoa(input); } : function (input) {
            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;
            input = utf8Encode(input);
            while (i < input.length) {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output +
                keyStr.charAt(enc1) + keyStr.charAt(enc2) +
                keyStr.charAt(enc3) + keyStr.charAt(enc4);
            }
            return output;
        }
    };
})();

Ext.override(Ext.grid.GridPanel, {
	headerBgColor: '#FFFFFF',
	evenRowBgColor: '#FFFFFF',
	oddRowBgColor: '#FFFFFF',
    getExcelXml: function(includeHidden) {
        var worksheet = this.createWorksheet(includeHidden);
        var totalWidth = this.getColumnModel().getTotalWidth(includeHidden);
		var border = '<ss:Borders>' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
                    '</ss:Borders>';

		var border_gap = '<ss:Borders>' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
                        '<ss:Border ss:Color="#a0a0a0" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
                        '<ss:Border ss:Color="#ff0000" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
                    '</ss:Borders>';

        return '<?xml version="1.0" encoding="utf-8"?>' +
            '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">' +
            '<o:DocumentProperties><o:Title>' + this.title + '</o:Title></o:DocumentProperties>' +
            '<ss:ExcelWorkbook>' +
                '<ss:WindowHeight>' + worksheet.height + '</ss:WindowHeight>' +
                '<ss:WindowWidth>' + worksheet.width + '</ss:WindowWidth>' +
                '<ss:ProtectStructure>False</ss:ProtectStructure>' +
                '<ss:ProtectWindows>False</ss:ProtectWindows>' +
            '</ss:ExcelWorkbook>' +
            '<ss:Styles>' +
                '<ss:Style ss:ID="Default">' +
                    '<ss:Alignment ss:Vertical="Top" ss:WrapText="1" />' +
                    '<ss:Font ss:FontName="arial" ss:Size="10" />' +
                    '<ss:Borders>' +
                        '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
                        '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
                        '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
                        '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
                    '</ss:Borders>' +
                    '<ss:Interior />' +
                    '<ss:NumberFormat />' +
                    '<ss:Protection />' +
                '</ss:Style>' +
                '<ss:Style ss:ID="title">' +
                    '<ss:Borders />' +
                    '<ss:Font />' +
                    '<ss:Alignment ss:WrapText="1" ss:Vertical="Center" ss:Horizontal="Center" />' +
                    '<ss:NumberFormat ss:Format="@" />' +
                '</ss:Style>' +
                '<ss:Style ss:ID="headercell">' +
                    '<ss:Font ss:Bold="1" ss:Size="10" />' +
                    '<ss:Alignment ss:WrapText="1" ss:Horizontal="Center" />' +
				border +
                    '<ss:Interior ss:Pattern="Solid" ss:Color="'+this.headerBgColor+'" />' +
                '</ss:Style>' +
                '<ss:Style ss:ID="even">' +
				border +
                    '<ss:Interior ss:Pattern="Solid" ss:Color="'+this.evenRowBgColor+'" />' +
                '</ss:Style>' +

                '<ss:Style ss:ID="even_gap">' +
				border_gap +
                    '<ss:Interior ss:Pattern="Solid" ss:Color="'+this.evenRowBgColor+'" />' +
                '</ss:Style>' +
                
                '<ss:Style ss:Parent="even" ss:ID="evendate">' +
                    '<ss:NumberFormat ss:Format="[ENG][$-409]dd\-mmm\-yyyy;@" />' +
                '</ss:Style>' +
                '<ss:Style ss:Parent="even" ss:ID="evenint">' +
                    '<ss:NumberFormat ss:Format="0" />' +
                '</ss:Style>' +
                '<ss:Style ss:Parent="even" ss:ID="evenfloat">' +
                    '<ss:NumberFormat ss:Format="0.00" />' +
                '</ss:Style>' +
                '<ss:Style ss:ID="odd">' +
				border +
                    '<ss:Interior ss:Pattern="Solid" ss:Color="'+this.oddRowBgColor+'" />' +
                '</ss:Style>' +
                '<ss:Style ss:ID="odd_gap">' +
				border_gap +
                    '<ss:Interior ss:Pattern="Solid" ss:Color="'+this.oddRowBgColor+'" />' +
                '</ss:Style>' +
                
                
                '<ss:Style ss:Parent="odd" ss:ID="odddate">' +
                    '<ss:NumberFormat ss:Format="[ENG][$-409]dd\-mmm\-yyyy;@" />' +
                '</ss:Style>' +
                '<ss:Style ss:Parent="odd" ss:ID="oddint">' +
                    '<ss:NumberFormat ss:Format="0" />' +
                '</ss:Style>' +
                '<ss:Style ss:Parent="odd" ss:ID="oddfloat">' +
                    '<ss:NumberFormat ss:Format="0.00" />' +
                '</ss:Style>' +
                
            '</ss:Styles>' +
            worksheet.xml +
            '</ss:Workbook>';
    },

    createWorksheet: function(includeHidden) {

//      Calculate cell data types and extra class names which affect formatting
        var cellType = [];
        var cellTypeClass = [];
        var cm = this.getColumnModel();
        var totalWidthInPixels = 0;
        var colXml = '';
        var headerXml = '';
        for (var i = 0; i < cm.getColumnCount(); i++) {
            if (includeHidden || !cm.isHidden(i)) {
                var w = cm.getColumnWidth(i)
                totalWidthInPixels += w;
                colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="' + w + '" />';
                headerXml += '<ss:Cell ss:StyleID="headercell">' +
                    '<ss:Data ss:Type="String">' + cm.getColumnHeader(i) + '</ss:Data>' +
                    '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
                var fld = this.store.recordType.prototype.fields.get(cm.getDataIndex(i));
                if(!fld){
					fld = {type:''};
                }
                switch(fld.type) {
                    case "int":
                        cellType.push("Number");
                        cellTypeClass.push("int");
                        break;
                    case "float":
                        cellType.push("Number");
                        cellTypeClass.push("float");
                        break;
                    case "bool":
                    case "boolean":
                        cellType.push("String");
                        cellTypeClass.push("");
                        break;
                    case "date":
                        cellType.push("DateTime");
                        cellTypeClass.push("date");
                        break;
                    default:
                        cellType.push("String");
                        cellTypeClass.push("");
                        break;
                }
            }
        }
        var visibleColumnCount = cellType.length;

        var result = {
            height: 9000,
//            width: this.getInnerWidth() //Math.floor(totalWidthInPixels * 30) + 50
            width: Math.floor(totalWidthInPixels * 30) + 50
        };

	var isNumber = function (val)
	{
		// we need to explicitly handle null values
		// because isNaN returns false when it should return true	
		if (null == val) return false;
		
		// if it is an empty string or a string with just spaces
		// isNaN returns false, but we really need it to return true
		// this expression will remove spaces if the given value is a string type
		if (typeof(val) == "string")
			val = val.replace(/\s*/g, "");
			
		if (val == "") return false;
		
		return !isNaN(val);
	};  

//      Generate worksheet header details.
        var t = '<ss:Worksheet ss:Name="' + this.title + '">' +
            '<ss:Names>' +
                '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\'' + this.title + '\'!R1:R2" />' +
            '</ss:Names>' +
            '<ss:Table x:FullRows="1" x:FullColumns="1"' +
                ' ss:ExpandedColumnCount="' + visibleColumnCount +
                '" ss:ExpandedRowCount="' + (this.store.getCount() + 2) + '">' +
                colXml +
/*                '<ss:Row ss:Height="38">' +
                    '<ss:Cell ss:StyleID="title" ss:MergeAcross="' + (visibleColumnCount - 1) + '">' +
                      '<ss:Data xmlns:html="http://www.w3.org/TR/REC-html40" ss:Type="String">' +
                        '<html:B><html:U><html:Font html:Size="15">' + this.title +
                        '</html:Font></html:U></html:B>Generated by ExtJs</ss:Data><ss:NamedCell ss:Name="Print_Titles" />' +
                    '</ss:Cell>' +
                '</ss:Row>' +
*/
                '<ss:Row ss:AutoFitHeight="1">' +
                headerXml + 
                '</ss:Row>';

//      Generate the data rows from the data in the Store
        for (var i = 0, it = this.store.data.items, l = it.length; i < l; i++) {
            t += '<ss:Row>';
             
            
            var cellClass = (i & 1) ? 'odd' : 'even';
            r = it[i].data;
            var k = 0;
            for (var j = 0; j < cm.getColumnCount(); j++) {
            
                if (  cm.getColumnAt(j).id == 'score'
                  || cm.getColumnAt(j).id == 'euro_final_loss'
                  || cm.getColumnAt(j).id == 'euro_early_loss'
                  || cm.getColumnAt(j).id == 'asia_final_down'
                  || cm.getColumnAt(j).id == 'asia_early_down' 
                  || cm.getColumnAt(j).id == 'asia_final_small'
                  || cm.getColumnAt(j).id == 'asia_early_small' 
                  ){
                  
                    cellClass = (i & 1) ? 'odd_gap' : 'even_gap';
                  
                }else
                {
                   cellClass = (i & 1) ? 'odd' : 'even';
                }
                  
                  
                
                if (includeHidden || !cm.isHidden(j)) {
                    var v;
                    var renderer = cm.getRenderer(j);
                    if(!renderer){
						v = r[cm.getDataIndex(j)];
						t += '<ss:Cell ss:StyleID="' + cellClass + cellTypeClass[k] + '"><ss:Data ss:Type="' + cellType[k] + '">';
							if (cellType[k] == 'DateTime') {
								t += v.format('Y-m-d');
							} else {
								t += v;
							}
						t +='</ss:Data></ss:Cell>';
					}else{
						v = renderer(r[cm.getDataIndex(j)], cm.getColumnAt(j), it[i], i);
						
						var cType = !(isNumber(v))? 'String' : 'Number';
						t += '<ss:Cell ss:StyleID="' + cellClass + '"><ss:Data ss:Type="'+cType+'">';
						t += v;


						t +='</ss:Data></ss:Cell>';
					}
                    k++;
                }
            }
            t += '</ss:Row>';
        }

        result.xml = t + '</ss:Table>' +
            '<x:WorksheetOptions>' +
                '<x:PageSetup>' +
                    '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />' +
                    '<x:Footer x:Data="Page &amp;P of &amp;N" x:Margin="0.5" />' +
                    '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />' +
                '</x:PageSetup>' +
                '<x:FitToPage />' +
                '<x:Print>' +
                    '<x:PrintErrors>Blank</x:PrintErrors>' +
                    '<x:FitWidth>1</x:FitWidth>' +
                    '<x:FitHeight>32767</x:FitHeight>' +
                    '<x:ValidPrinterInfo />' +
                    '<x:VerticalResolution>600</x:VerticalResolution>' +
                '</x:Print>' +
                '<x:Selected />' +
                '<x:DoNotDisplayGridlines />' +
                '<x:ProtectObjects>False</x:ProtectObjects>' +
                '<x:ProtectScenarios>False</x:ProtectScenarios>' +
            '</x:WorksheetOptions>' +
        '</ss:Worksheet>';
        return result;
    }
});