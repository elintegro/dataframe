/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.frontendlib

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeView
import com.elintegro.erf.dataframe.OrderedMap
import org.apache.commons.lang.StringUtils
import org.springframework.context.i18n.LocaleContextHolder

/**
 * All this class does is generating the javascript functions, based on dataframe object, based on JQuery Jqx library
 *
 * If we need to use different technology, we need to implement DataframeView interface and build accordingly ...
 *
 *
 * @author ELipkov1
 *
 */
public class DataframeViewJqx implements DataframeView {

	Dataframe dataframe;
	OrderedMap fields;
	String dataframeName;

	public DataframeViewJqx(Dataframe dataframe){
		this.dataframe = dataframe;
		fields = dataframe.getFields();
		dataframeName = dataframe.dataframeName;

	}

	//Consider to move this one to JqxTreeWidget, since it is related to Tree Widget!
	public String getAjaxButtonScript(com.elintegro.erf.dataframe.DFButton dfButton, Dataframe refDataframe){
		
		String parentNamedParam = dataframe?.getNamedParameters()?dataframe?.getNamedParameters()?.toArray()[0].key:""; //TODO check if it is a right way to gewt parentNamed param!!!
		if(StringUtils.isEmpty(parentNamedParam)){
			parentNamedParam = 'id';
		}
		StringBuilder script = new StringBuilder()
		String dialogBoxId = ""
		if(dfButton.dialogBoxActionParams){
		   dialogBoxId = "confirmationDialog-$dataframe.dataframeName-$dfButton.dialogBoxActionParams.buttonFor"
		}
		String hoverMessage = dfButton.hoverMessage?dataframe.messageSource.getMessage(dfButton.hoverMessage, null, org.apache.commons.lang.WordUtils.capitalizeFully(dfButton.name), LocaleContextHolder.getLocale()):""
		if(dfButton.script.trim().length()>0){
			String buttonUrl = dfButton.url?:""
			script.append("""
                        Dataframe.${dataframeName}_${dfButton.name} = function(){
                           var allParams = {'dataframe':'$dataframeName'};
                                   ${dfButton.script}
                          if (${!buttonUrl.isEmpty()}){
                            ${ getClickedButtonScript(dfButton) }
                          }
                        }
                         if(${!dfButton?.image && !dfButton?.image?.isEmpty()}){
                             jQuery("#${dataframeName}-${dfButton.name}").jqxButton({ theme: 'summer', height: '60%'});
                         }
		                 if(${!hoverMessage.isEmpty()}){
                          jQuery("#${dataframeName}-${dfButton.name}").jqxTooltip({ content: '$hoverMessage', position: 'top', name: 'movieTooltip'});
                         }
					jQuery('body').on('click','#${dataframeName}-${dfButton.name}', function(){
                          if(${!dialogBoxId.isEmpty()}){
						      Dataframe.showDialogBox("$dialogBoxId");
                          }else{
                              Dataframe.${dataframeName}_${dfButton.name}();
                          }
					});""")

		} else {
			if(refDataframe){
				def paramList = Dataframe.getParamOfDataframeFields(refDataframe)

				//TODO: check if it is a relevant way to get the name of the referenced field!
//				def domainAlias = refDataframe.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0)
				def refreshFunction =  refDataframe?.getRefreshFunctionName()
				def refDataframeName = refDataframe?.dataframeName

				String refreshAction = "";
				if(dfButton.refreshField){
					def refreshField_ = Dataframe.buildKeyFieldParamForMetaData_(dataframe.dataframeName, dfButton.refreshField);
					refreshAction = """\n
                               	Dataframe.refreshDataForGrid(data, '$refreshField_', '$dataframe.dataframeName', '$refDataframeName');
							\n""";
				}

				refDataframe.doAfterSave += refreshAction;											  
				refDataframe.doAfterDelete += refreshAction;
				
				script.append("""

                         Dataframe.${dataframeName}_${dfButton.name} = function(){
                                   var formParams = jQuery('#${refDataframeName}-form');
                                   var parentFormParams = jQuery('#${dataframeName}-form');
                                   var formParamsReady = Dataframe.getFormparams(formParams);
                                   var parentFormParamsReady = Dataframe.getFormparams(parentFormParams);
                                   var parentFieldName = formParamsReady['${refDataframeName}-parentFieldName'];
                                   var isPopup= ${refDataframe.displayType.equals("popup") ? true : false}
                            
                                   if(Dataframe.${refDataframeName}.dataFrameParamsToRefresh == undefined || isPopup){
                                    	Dataframe.${refDataframeName}.dataFrameParamsToRefresh = {};
                                    	Dataframe.${refDataframeName}.defaultData = Dataframe.defaultDataframeInit("${refDataframeName}");
                                    	Dataframe.showDataframe2('${dataframeName}', '${refDataframeName}', '$refDataframe.displayType');
                                        Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentFieldName = parentFieldName;
                                   }else{
                                    	Dataframe.${refDataframeName}.initValues(Dataframe.${refDataframeName}.defaultData);
                                    	Dataframe.showDataframe2('${dataframeName}', '${refDataframeName}', '$refDataframe.displayType');
                                    	Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentFieldName = parentFieldName;
                                   }
                            
                                   //TODO: check if it works! if not - remove!!!
                                   Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentFieldName = '${parentNamedParam}';
                            
                                   Dataframe.${refDataframeName}.dataFrameParamsToRefresh.level = parentFormParamsReady['${dataframeName}-level'];
                                   Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentNode =  parentFormParamsReady['${dataframeName}-level']+'-'+parentFormParamsReady['${parentNamedParam}'];
                                   Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentNodeId = parentFormParamsReady['${parentNamedParam}'];
                                   Dataframe.${refDataframeName}.dataFrameParamsToRefresh.parentNodeLevel =  parentFormParamsReady['${dataframeName}-level'];
                            
                                   Dataframe.initHiddenValuesForInsert(Dataframe.${refDataframeName}.dataFrameParamsToRefresh,  '${refDataframeName}', formParamsReady);
                        }
                        if(${dfButton?.image && dfButton?.image?.isEmpty()}){
                             jQuery("#${dataframeName}-${dfButton.name}").jqxButton({ theme: 'summer', height: '60%'});
                         }
                        if(${!hoverMessage.isEmpty()}){
                          jQuery("#${dataframeName}-${dfButton.name}").jqxTooltip({ content: '$hoverMessage', position: 'top', name: 'movieTooltip'});
                         }
						jQuery('body').on('click','#${dataframeName}-${dfButton.name}', function(){
                                   if(${!dialogBoxId.isEmpty()}){
                                       Dataframe.showDialogBox("$dialogBoxId");
                                   }else{
                                       Dataframe.${dataframeName}_${dfButton.name}();
                                   }
		                
						});
				""")
			}else {
				String buttonUrl = dfButton.url?:""
				script.append("""
                        Dataframe.${dataframeName}_${dfButton.name} = function(){
                           var allParams = {'dataframe':'$dataframeName'};
                          if (${!buttonUrl.isEmpty()}){
                            ${ getClickedButtonScript(dfButton) }
                          }
                        }
                         if(${!dfButton?.image && !dfButton?.image?.isEmpty()}){
                             jQuery("#${dataframeName}-${dfButton.name}").jqxButton({ theme: 'summer', height: '60%'});
                         }
		                 if(${!hoverMessage.isEmpty()}){
                          jQuery("#${dataframeName}-${dfButton.name}").jqxTooltip({ content: '$hoverMessage', position: 'top', name: 'movieTooltip'});
                         }
					jQuery('body').on('click','#${dataframeName}-${dfButton.name}', function(){
                          if(${!dialogBoxId.isEmpty()}){
						      Dataframe.showDialogBox("$dialogBoxId");
                          }else{
                              Dataframe.${dataframeName}_${dfButton.name}();
                          }
					});""")
			}

		}
		return script.toString();
	}

	public String getRefreshFunctionName(){
		//return "refresh_${dataframeName}"
		return "Dataframe.${dataframeName}.refresh"
	}

	public String getClickedButtonScript(dfButton){
		String buttonUrl = dfButton.url?:""
		String doBeforeSave = dfButton.doBeforeSave?:""
		String successScript = "";
		String failureScript = ""
		boolean replaceButton = false;
		if(dfButton?.callBackParams && dfButton?.callBackParams?.successScript){
				successScript = dfButton.callBackParams.successScript
		}
		if(dfButton?.callBackParams && dfButton?.callBackParams?.failureScript){
			failureScript = dfButton.callBackParams.failureScript
		}
			return """
//				var allParams = {'dataframe':'$dataframeName'};
                if($dataframe.wrapInDiv){
                         $dataframe.saveScriptJs
                }else{
		                 jQuery.each(jQuery('#$dataframeName-form').serializeArray(), function(i, field) {
                                allParams[field.name] = field.value;
                         });
	            }
	            $doBeforeSave
	            jQuery.ajax({
                        url: '$buttonUrl',
 						data: allParams,
						type: 'POST',
						error: function(data) {
							alert("Error!");
						},
						success: function(data) {
							if(data.success) {
							    if(data.newData){
							       Dataframe.returnedRefreshData(data);
							    }
							   if(data.msg)	{
							      jQuery("#$dataframeName-errorContainer").html(data.msg);
//							      alert(data.msg);
							   }else{
								  jQuery("#$dataframeName-errorContainer").html("Success!");
                               } 
                              ${successScript}
                            }else {
                               if(data.msg){
							      jQuery("#$dataframeName-errorContainer").html(data.msg);
                                }
                              ${failureScript}
                            }
						}
				});
			"""
	}
}
