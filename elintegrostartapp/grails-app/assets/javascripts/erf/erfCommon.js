// initialize dataframe namespace
jQuery(document).ready(function() {
	if (window['Dataframe'] == undefined){
		Dataframe = {};
	}

	if (window['Widget'] == undefined){
		Widget = {};
		Widget.JqxComboboxLoad = {};
		Widget.JqTextField = {};
	}


	Dataframe.setHiddenField = function(fieldFullName, fieldValue, dataFrameName){
		//TODO: use lowDash library to check _.isNil()!!!
		if(!(fieldValue == 'undefined' || fieldValue == undefined || fieldFullName == 'undefined' || fieldFullName == undefined || fieldFullName == '')){
			var fieldInputElementId = '#' + fieldFullName;
			var formElementId = '#' + dataFrameName + '-form';
            var wrapInDiv = Dataframe[dataFrameName]['wrapInDiv'];
            if(wrapInDiv){
                formElementId = '#' + dataFrameName+ '-div'
            }
			var fieldInputElement = jQuery(formElementId).find(fieldInputElementId);
			var fieldInputElementVal = null;
			var fieldInputElementLength = fieldInputElement.length;
			if(!fieldInputElement){
				fieldInputElementVal = fieldInputElement.val();
			}
			//if(!fieldInputElementVal || fieldInputElementVal == 'undefined' || fieldInputElementVal == undefined){
			if(!fieldInputElementLength){
				jQuery(formElementId).append("<input id='" + fieldFullName + "' type='hidden' name='" + fieldFullName+"' >");
				fieldInputElement = jQuery(formElementId).find(fieldInputElementId);
			}
			fieldInputElement.val(fieldValue);
		}
	};

	Dataframe.initHiddenValuesForInsert = function(dataForRefresh, dataframeName, formParamsReady){
        if(!(dataForRefresh.parentFieldName == '' || dataForRefresh.parentFieldName == 'undefined' || dataForRefresh.parentFieldName == undefined)){
           Dataframe.setHiddenField(dataForRefresh.parentFieldName, dataForRefresh.parentNodeId, dataframeName);
        }
		Dataframe.setHiddenField(dataframeName+"-parentNode", dataForRefresh.parentNodeLevel+"-"+dataForRefresh.parentNodeId, dataframeName);
		Dataframe.setHiddenField(dataframeName+"-parentNodeId", dataForRefresh.parentNodeId, dataframeName);
		Dataframe.setHiddenFieldsForInsert(dataframeName, formParamsReady);
	};

	Dataframe.getFormparams = function(formParams){
		var allParams = {};
		jQuery.each(formParams.serializeArray(), function(i, field) {
			allParams[field.name] = field.value;
		});
		return allParams;
	}

	Dataframe.setHiddenFieldsForInsert = function(dataframeName, formParams){
		var fldPref = 'key-';
		for (var fld in formParams) {
			if(fld.startsWith(fldPref)){ //We are having key field, let us put its value to ""!
				Dataframe.setHiddenField(fld, "", dataframeName);
			}
		}

	}//Endo of function setHiddenFieldsForInsert



	/**
	 * JS Source is from erfCommon.js to populate Default data from the server (domain classes)
	 */

	Dataframe.defaultDataframeInit = function(dataframeName){
		var allParams = {'dataframe':dataframeName};  // TODO automatically add the id's for this dataframe
		var urlDefault = "/elintegrostartapp/dataframe/ajaxCreateNew";

		jQuery.ajax({
			url: urlDefault,
			data: allParams,
			type: 'POST',
			success: Dataframe.returnedRefreshData
		});

	}


	Dataframe.returnedRefreshData = function(returnedData) {
		var dataframeName = returnedData.dataframe;
		try{
			// Dataframe[dataframeName].insertHtml(returnedData.html);
			Dataframe[dataframeName].currentData = returnedData.data;

			jQuery.each(returnedData.data, function(key, value){
				if(key != 'keys'){
					Dataframe[dataframeName][key] = value;
				}
			});


			Dataframe[dataframeName].defaultData = returnedData.default;
			Dataframe[dataframeName].parentDataframe = returnedData.parentDataframe;
			Dataframe[dataframeName].dataFrameParamsToRefresh = returnedData.dataFrameParamsToRefresh;
			Dataframe.showDataframe(returnedData.parentDataframe, dataframeName);
			Dataframe[dataframeName].initValues(returnedData.data);
			jQuery.each(returnedData.data.keys, function(key, value){
				if(key != 'now'){
					//TODO: please consider to remove it: we are going to use full key field name, like key-<df>-<domainAlias>-<fieldName>-<namedParam>
					Dataframe.setHiddenField(key, value, dataframeName);
					var keyFldName = fullKeyFields[key];
					if(keyFldName.startsWith("key-") ){
						Dataframe.setHiddenField(keyFldName, value, dataframeName);
					}
				}
			});

			if(!(returnedData.dataFrameParamsToRefresh.parentFieldName == '' || returnedData.dataFrameParamsToRefresh.parentFieldName == 'undefined' || returnedData.dataFrameParamsToRefresh.parentFieldName == undefined)){
				var parentFldName = dataframeName + "-" + returnedData.dataFrameParamsToRefresh.parentFieldName;
				Dataframe.setHiddenField(parentFldName, returnedData.dataFrameParamsToRefresh.parentNodeId, dataframeName);
				Dataframe.setHiddenField(dataframeName + "-parentFieldName", returnedData.dataFrameParamsToRefresh.parentFieldName, dataframeName);
			}

			Dataframe.setHiddenField(dataframeName + "-default", returnedData.dataFrameParamsToRefresh.level, dataframeName);
			Dataframe.setHiddenField(dataframeName + "-level", returnedData.dataFrameParamsToRefresh.level, dataframeName);
			//Dataframe.setHiddenField(dataframeName + "-parentNode", returnedData.dataFrameParamsToRefresh.parentNode, dataframeName);
			//Dataframe.setHiddenField(dataframeName + "-parentNodeId", returnedData.dataFrameParamsToRefresh.parentNodeId, dataframeName);
			Dataframe.setHiddenField(dataframeName + "-parentNodeLevel", returnedData.dataFrameParamsToRefresh.parentNodeLevel, dataframeName);

			if(returnedData.insertHtmlTo && returnedData.divIdToDisable){
				Dataframe.loadPopup(returnedData.insertHtmlTo , returnedData.divIdToDisable,returnedData.popUpTitle);
			}
		}catch(e){
			alert(" FE: Error while refreshing Dataframe: "+ dataframeName + ", error is: " + e);
		}
	}//EndOf feedback function


//Widget.JqTextField.setValue = function(var dataframeName, var fieldName, var json){
	Widget.JqTextField.setValue = function(json){
		alert("From erfCommon.js: Im in  + Widget.JqTextField.setValue !");
	}

	/**
	 * This method is created in erfCommon.js
	 */

	Widget.JqxComboboxLoad.load = function(dataframeName, fieldName, url){
		//jQuery.get('/elintegrostartapp/dataframe/ajaxLoadAdditionalDataForField?dataframe=$dataframe.dataframeName&&reload="""+field.name+""", function(jsonData){
		alert("I'm in Widget.jqxComboboxLoad for dtaframe = "+dataframeName+" field = " + fieldName);
		var comboboxJasonData = null;
		jQuery.get(url+'?dataframe='+dataframeName+'&reload=' + fieldName, function(jsonData){
			comboboxJasonData = jsonData;

			//
			jQuery('#'+dataframeName+'-'+fieldName).jqxComboBox
			({
				source:jsonData,
				displayMember: 'name',
				valueMember: 'id',
				renderSelectedItem: function(index, item)
				{
					var item = jsonData[index];
					if(item !=null)
					{
						var value =item.id
						jQuery('input[name="'+field.name+'"]').val(value)
					}

				}
			});
			console.log("Combobox of dataframe: "+dataframeName+" and field: "+fieldName+" has been loaded successfully ...");
		},'json');

		return comboboxJasonData;

	}


	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.showDataframe  = function(parentDataframe, dataframeName) {
		if(parentDataframe != undefined){
			var prntDfDiv = '#'+parentDataframe+'-detail-div';
			jQuery(prntDfDiv).children().hide();
			jQuery(prntDfDiv).show();
		}
		jQuery('#'+ dataframeName + '-div').show();
	};

	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.showDataframe2  = function(parentDataframe, dataframeName, displayType) {
		if(parentDataframe != undefined){
			var prntDfDiv = '#'+parentDataframe+'-div';
			var dfDiv = '#'+dataframeName+'-div';
			if(!displayType == "popup"){
				jQuery(prntDfDiv).hide();
			}
			//jQuery(dfDiv).hide();
			jQuery(dfDiv).show();
		}
		jQuery('#' + dataframeName + '-div').show();
	};

	/**
	 *  This method is created in erfCommon.js
	 */

	Dataframe.enableInputField = function(inputFld){
		inputFld.attr('disabled', false);
		inputFld.css({'opacity' : 1.0});
		inputFld.css({'background-color' : '#FFFFFF'})
	}
	
	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.disableInputField = function(inputFld){
		inputFld.attr('disabled', true);
        inputFld.children().attr('disabled', true);
        inputFld.css({'opacity' : 0.7});
		// inputFld.css({'background-color' : '#B2B2B2'})
	}

    Dataframe.findInputField = function(fieldFullName, dataFrameName){

        if(!(fieldFullName == 'undefined' || fieldFullName == undefined || fieldFullName == '')){
            var fieldInputElementId = '#' + fieldFullName;
            var formElementId = '#' + dataFrameName + '-form';
            var wrapInDiv = Dataframe[dataFrameName]['wrapInDiv'];
            if(wrapInDiv){
                formElementId = '#' + dataFrameName+ '-div'
            }
            var fieldInputElement = jQuery(formElementId).find(fieldInputElementId);
            if(fieldInputElement){
               return fieldInputElement;
            }
        }
	}

    Dataframe.buildFieldIdFromField = function(fieldKeys){
		var fieldIdList = [];
        fieldKeys.forEach(function (k,i) {
			if(k.indexOf('.')>-1){
				fieldIdList.push(k.split('.').join('-'));
			}
        });
        return fieldIdList;
	}
	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.loadPopup = function(insertToHtml, divIdToDisable, popUpTitle){

		var insertToHtmlSelector = jQuery('#'+insertToHtml);
		insertToHtmlSelector.show();

		insertToHtmlSelector.jqxWindow({
			resizable: true,
			autoOpen: false,
			width: 500,
			height: 'auto',
			isModal: true
		});
		insertToHtmlSelector.jqxWindow('setTitle', "<strong>"+popUpTitle+"</strong>");
		insertToHtmlSelector.jqxWindow('open');
		//jQuery('#'+divIdToDisable).css('pointer-events','none');
	}

	Dataframe.closePopup = function(insertHtmlTo, divIdToDisable){
		if(insertHtmlTo && divIdToDisable){
			var insertHtmlToSelector = jQuery('#'+insertHtmlTo);
			insertHtmlToSelector.on('close', function(event){
				event.preventDefault();
				insertToHtmlSelector.hide();
				jQuery('#'+divIdToDisable).css('pointer-events','auto');
			});
		}
	}

	Dataframe.jqxModalClose = function(insertHtmlTo, divIdToDisable){
		var insertHtmlToSelector = jQuery('#'+insertHtmlTo);
		insertHtmlToSelector.jqxWindow('close');
		insertToHtmlSelector.hide();
		jQuery('#'+divIdToDisable).css('pointer-events','auto');
	}

	/**
	 *  This method is created in erfCommon.js
	 */
    Dataframe.validateGoogleAddress = function( addressValue ){
        var googleAddressObject = "";
        try {
            var geocoder = new google.maps.Geocoder();
            var address = addressValue;
            geocoder.geocode( { 'address': address}, function(results, status) {
                if (status == 'OK') {
                    Dataframe.afterAddressValidationSuccess(results);
                } else {
                    alert('Geocode was not successful for the following reason: ' + status);
                }
            });
        }catch(e){
            console.log("Error Occurred"+e);
        }

        return googleAddressObject;
    }

	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.getDefaultDateValueFormat = function( dateValue ){
		//var dformat = new Date(dateValue).toLocaleDateString('en-GB', {
		//	day : 'numeric',
		//	month : 'short',
		//	year : 'numeric'
		//}).split(' ').join('-');
		var monthNames = [
			"January", "February", "March",
			"April", "May", "June", "July",
			"August", "September", "October",
			"November", "December"
		];
        var date = new Date(dateValue);
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();

		return day + '-' + monthNames[monthIndex] + '-' + year;
	}



	/**
	 *  This method is created in erfCommon.js
	 */
	Dataframe.getGridValues = function(gridId, dataframeName){
		var pk = Dataframe[dataframeName].pk;
		var gridId1 = '#' + gridId;
		var rows = jQuery(gridId1).jqxGrid('getrows');
		var result = Dataframe.processValues(pk, rows);
		//return "1,2,3";		
		return result;
	}

	Dataframe.processValues = function(pk, rows){
		if(pk && rows){
			var res = new Array();
			rows.forEach(function(row){
				var resRow = new Map();
				pk.forEach(function(pkFld){
					// var pkFldUpC = pkFld;
					resRow.set(pkFld, row[pkFld]);
				});
				res.push(Dataframe.mapStringify(resRow));
			});
		}
		return res.toString();
	}

	Dataframe.mapStringify = function(j){
		var dq='"';
		var json="{";
		var last=Object.keys(j).length;
		var count=0;
		j.forEach(function(val, key){
			json += dq + key + dq+":" + dq + val + dq;
			count++;
			if(count<last){
				json +=",";
			}
		});
		json+="}";
		return json;
	}
	
	/**
	 *  This method is created in erfCommon.js
	 */	
    Dataframe.refreshDataForGrid = function(data, fldId, parentDataframeName, refDataframeName){
    	var fldId_ = "#" + fldId;
        if(data.operation == 'D' || data.operation == 'E'){
            var selectedrowindex = jQuery(fldId_).jqxGrid('getselectedrowindex');
            var rowscount = jQuery(fldId_).jqxGrid('getdatainformation').rowscount;
            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                var id = jQuery(fldId_).jqxGrid('getrowid', selectedrowindex);
                var commit = jQuery(fldId_).jqxGrid('deleterow', id);
                jQuery(fldId_).jqxGrid('selectrow', selectedrowindex - 1);
             }
        }else{
	        var row = {};
	        var isUpdated = false;
	        var isInsert = data.operation == 'I';
	        var dataFieldsName = Dataframe[parentDataframeName].dataFieldsName;
	        var newData = data.newData;
	        var refDf = Dataframe[refDataframeName];

	        //var rowData = Dataframe[refDataframeName].parentRowData;
	        var rowData = {};	        
	        var selectedrowindex = jQuery(fldId_).jqxGrid('getselectedrowindex');

/*	        if(!selectedrowindex){
	        	isInsert = true;
	        }else{
		        rowData =  jQuery(fldId_).jqxGrid('getrowid', selectedrowindex);
	        }
	        
	        if(rowData == undefined){
	        	isInsert = true;
	        }
*/	     jQuery.each(newData, function(key, value) {
	         var dataMap = value;
                jQuery.each(dataMap, function (key, value) {
                    if (key in dataFieldsName) {
                        var fieldAlais = dataFieldsName[key][0];
                        var fieldType = dataFieldsName[key][1];
                        var newValue = value;
                        if (fieldType == 'date') {
                            newValue = Dataframe.getDefaultDateValueFormat(newValue);
                        }
                        if (isInsert) {
                            row[fieldAlais] = newValue;
                        } else {
                            var oldValue = rowData[fieldAlais];
                            if (oldValue != newValue) {
                                row[fieldAlais] = newValue;
                                isUpdated = true;
                            }
                        }
                    }
                });
            });
	        if(isInsert){	        	
	           jQuery(fldId_).jqxGrid('addrow', null, row);
	           var rows = jQuery(fldId_).jqxGrid('getrows');
	           jQuery(fldId_).jqxGrid('selectrow', rows.length-1);
	           jQuery(fldId_).jqxGrid('ensurerowvisible', rows.length-1);	           
	        }else if(isUpdated){	        	
	           jQuery.extend(rowData, row);
	           jQuery(fldId_).jqxGrid('updaterow',Dataframe[refDataframeName].parentRowId, rowData);
	        }
        }
                
        //Dataframe.closePopup(Dataframe[refDataframeName].insertHtmlTo, Dataframe[refDataframeName].divIdToDisable);
        
        if(Dataframe[refDataframeName].insertHtmlTo){
        	jQuery('#' + Dataframe[refDataframeName].insertHtmlTo).jqxWindow('close');
        }
                
   }//refreshDataForGrid end

	Dataframe.getComboboxValues = function(gridId, dataframeName){
		var pk = Dataframe[dataframeName].pk;
		var gridId1 = '#' + gridId;
		var orginalItem = new Array();
		var items = jQuery(gridId1).jqxComboBox('getSelectedItems');
		items.forEach(function(i){
			orginalItem.push(i.originalItem);
		});
		var result = Dataframe.processValues(pk, orginalItem);
		return result;
	}

	/**
	 * This method is for login dataframe
	 */

	Dataframe.authAjax = function(dataFrameName){
		var elementId = '#' + dataFrameName;
        var errorSelector = elementId + "-errorContainer";
		var dataUrl = "/elintegrostartapp/login/authenticate"; //todo: move to some where else
		var formdata= {
            username:$(elementId+ "-user-username").val(),
            password:$(elementId+ "-user-password").val(),
            remember_me:$(elementId+ "-rememberMe").val()
		};
		jQuery.ajax({
			url: dataUrl,
			type: 'POST',
			data: formdata,
			success: function(response, textStatus, jqXHR) {
				if(response.success){
					location.reload();
				}else if(response.error){
					jQuery(errorSelector).css("color","red").html(response.error);
				}else{
                    jQuery(errorSelector).html(jqXHR.responseText);
                }
			},
			error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status == 401 && jqXHR.getResponseHeader("Location")) {
                    $(errorSelector).text('Sorry, there was a problem with the login request.');
                }
                else {
                    var responseText = jqXHR.responseText;
                    if (responseText) {
                        var json = jQuery.parseJSON(responseText);
                        if (json.error) {
                            jQuery(errorSelector).html(json.error);
                            return;
                        }
                    }
                    else {
                        responseText = "Sorry, an error occurred (status: " + textStatus + ", error: " + errorThrown + ")";
                    }
                    jQuery(errorSelector).html(responseText);
                }
			}
		});
	}

    Dataframe.customDataframeDivShowHide  = function(dataframeName, isDivshow) {
        var dataframeDiv = '#'+dataframeName+'-div';
        if (isDivshow){
            jQuery(dataframeDiv).show();
        }else {
            jQuery(dataframeDiv).hide();
        }
    };

    Dataframe.showDialogBox = function( id ){
        jQuery("#"+id).css('display','block');
        jQuery("#"+id).jqxWindow({ width: 300, height: 100, theme: 'summer' ,cancelButton: $("#"+id+"-cancel")});
        jQuery("#"+id+"-cancel").jqxButton({ theme: 'summer' });
    }

    Dataframe.showSummary = function(data){

    	var outerDiv = $("<div id='summaryDialogBox' style='width: 500px;'></div>");
    	outerDiv.append("<div>Summary Dialog Box</div>");
    	var ulWrapperTag = $("<div id='summaryContents'></div>");
    	var contentDiv=$("<div></div>");
    	var ulTag = $("<ul></ul>");
        constructLi(ulTag, data.newData, data.fieldListFromHql);
        ulWrapperTag.append(ulTag);
        contentDiv.append(ulWrapperTag);
        contentDiv.append('<div id="summaryButtons" style="float: right"><input id="print" type="button" onclick="printDivWithId(\'summaryButtons\');" value="print"/>&nbsp;&nbsp;&nbsp;<input id="ok" type="button" value="ok"/></div>');
        outerDiv.append(contentDiv);
        outerDiv.appendTo('body');
        jQuery("#summaryDialogBox").css('display','block');
        jQuery("#summaryDialogBox").jqxWindow({ width: 'auto', height: 'auto', theme: 'summer' ,okButton: $("#ok")});
        jQuery("#ok").jqxButton({ theme: 'summer' });
        jQuery("#ok").on('click',function (e) {
            e.preventDefault();
            $('#summaryDialogBox').remove();
        });
        jQuery("#print").jqxButton({ theme: 'summer' });
	}

	 Dataframe.printDivWithId = function(id){
        var printContents = jQuery('#'+id+'').html();
        w=window.open();
        w.document.write(printContents);
        w.print();
        w.close();
	}
    constructLi = function(ulTag, data, fieldListFromHql){
        jQuery.each(data, function(key, value){
            if(isPrimitive(value)){
                var hqlFields = [];
                var keyPhrase = "";
                jQuery.each(fieldListFromHql, function(i,v){
                    hqlFields = v.split(" as ");
                    var field = hqlFields[0].trim();
                    var fieldAlias = hqlFields[1].trim();
                    if(field == key){
                        keyPhrase = fieldAlias
                    }
                });
                keyPhrase = keyPhrase?keyPhrase:formatFieldName(key);
                if(value!=null && value!=undefined && value!="" && !keyPhrase.toLowerCase().includes("id")){
                    ulTag.append("<li><div class='summaryKey summaryDataframe' style='display: inline-block; width: 180px;'>"+keyPhrase+"</div><div class='summaryDataframe' style='display: inline'>:</div><div class='summaryValue summaryDataframe' style='display: inline-block; margin: 2px 3px 4px 6px;'>"+value+"</div></li>");
                }
            }else {
                constructLi(ulTag, value,fieldListFromHql);
            }
        });
    }

    function formatFieldName(fldNm){
        var formattedString = fldNm.replace(/([a-z](?=[A-Z]))/g, '$1 ');
        return capitalize(formattedString)
	}
    function capitalize(s){
        return s.toLowerCase().replace( /\b./g, function(a){ return a.toUpperCase(); } );
    };
    function isPrimitive(val) {
        return (val !== Object(val));
    };

    Dataframe.hideDfrsDetails = function (gridDataframeNames, dataframeNamesToShow) {
    	if (gridDataframeNames) {
            jQuery.each(gridDataframeNames, function (index, value) {
                if (!dataframeNamesToShow.includes(value)){
                    Dataframe.customDataframeDivShowHide(value, false);
                }
            });
		}
    };

/*Showing Notification*/

	Dataframe.notification = function(value, type){
		type = type || 'info';
		var template = "success";
		if(type.includes("info")){template = "success"}
        if(type.includes("success")){template = "success"}
        if(type.includes("error")){template = "error"}
        jQuery("#notificationDiv").show();
        jQuery("#notificationDiv").text(value);
        jQuery("#notificationDiv").jqxNotification({
            width: "auto",
            position: "top-right",
            opacity: 0.6,
            autoOpen: true,
            autoClose: true,
            template: template
        });

    }

    Dataframe.getUrl = function(){
        var url_string = window.location.href;
        var url = new URL(url_string);
        return url;
	}

	Dataframe.sendDataByHttpRequest = function(url, param, callback, methd){
		var method = (methd == "POST")?"POST":"GET";
		var params;
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
            	if(callback){
                    callback(xmlHttp.responseText);
                }else{
            		console.log(xmlHttp.responseText);
				}
            }
        }
        params = formatParamsToSend(param);
        if(method == "POST"){
            http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.open(method, url, true);
            xmlHttp.send(params);
		}else {
            xmlHttp.open(method, url+params, true); // true for asynchronous
            xmlHttp.send(null);
		}
	}

	function formatParamsToSend(params){
        if(params != null && params!= undefined && params != ""){
            return "?" + Object
                .keys(params)
                .map(function(key){
                    return key+"="+encodeURIComponent(params[key])
                })
                .join("&")
		}
		return ""
	}
});
