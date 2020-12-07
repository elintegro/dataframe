jQuery(document).ready(function () {
    if (window['DfEditor'] == undefined){
        DfEditor = {};
    }

    DfEditor.showDfEditorTree = function (treeData) {
        var data = DfEditor.populateTreeNodeData(treeData);
        // prepare the data
        var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'id' },
                    { name: 'parentid' },
                    { name: 'text' },
                    { name: 'value' }
                ],
                id: 'id',
                localdata: data
            };
        // create data adapter.
        var dataAdapter = new jQuery.jqx.dataAdapter(source);
        // perform Data Binding.
        dataAdapter.dataBind();
        var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
        jQuery('#gcPages').jqxTree({ source: records, width: '250px'});

        jQuery('#gcPages').on('itemClick', function (event) {
            var parentPageNode;
            var parentElement = event.args.element.parentElement.parentElement;
            var parent = $('#gcPages').jqxTree('getItem', parentElement);
            if (parent) {
                parentPageNode = parent.label
            };
            var args = event.args;
            var item = jQuery('#gcPages').jqxTree('getItem', args.element);
            var dataframe = item.label;
            if (!(parentPageNode && dataframe)){
                alert("Select Dataframe Node");
                return false;
            }
            var urlDefault = "/elintegrostartapp/dfEditor/editorValues";
            var params = {'page':parentPageNode,'dataframe':dataframe};
            jQuery.ajax({
                url: urlDefault,
                data: params,
                type: 'POST',
                success: function(returnedRefreshData){
                    try {
                        var dataframe = returnedRefreshData.dataframe;
                        var pageName = returnedRefreshData.pageName;
                        DfEditor.showDataframeDetail(pageName, dataframe);
                        DfEditor.initValues(returnedRefreshData.data, dataframe, pageName);
                    }catch (e){
                        alert(" FE: Error while refreshing data, error is: " + e);
                    }
                }
            });
        });

    };

    DfEditor.populateTreeNodeData = function (treeData) {
        var dataList = [];
        var i = 1;
        var j = 1;
        jQuery.each(treeData, function(page, dfPageList){
            var pId = "a"+i;
            dataList.push(getFormatedData(pId,-1,page));
            dfPageList.forEach(function(dfName){
                dataList.push(getFormatedData(j, pId, dfName));
                j++;
            });
            i++;
        });
        return dataList
    };

    function getFormatedData(id, parentId, text){
        return {
            "id" : id,
            "parentid"  : parentId,
            "text"       : text
        }
    }

    DfEditor.showDataframeDetail = function (pageName, dataframe) {
        var selector = "#"+pageName+"-"+dataframe+"-Div";
        var prntDfDiv = "#pagedf-detail-div";
        jQuery('.hql-result').children().remove();
        jQuery('.named-params').children().remove();
        jQuery(prntDfDiv).children().hide();
        jQuery(selector).show();
    };

    DfEditor.initValues = function (json, dataframe, pageName) {
        if(json === undefined, dataframe == undefined, pageName == undefined){
            alert("default data has not been formed, please ask developers to fix it!");
        }
        var selector = "#"+pageName+"-"+dataframe;
        jQuery.each(json, function(key, value){
            $(selector+"-"+key+"-id").val(value)
        });
    };

    DfEditor.buildWidget = function (data) {
        var namedParameter = data.namedParameter;
        var scriptOpen = '<script type="text/javascript">';
        var scriptClose = '</script>';
        var html = "";
        if (namedParameter){
            jQuery.each(namedParameter, function(name, type){
                var label = '<label for="'+name+'">'+name+':&nbsp;&nbsp;&nbsp;</label>';
                var inputText = '<input type="text" id="'+name+'" name="'+name+'"/>';
                var div = '<div id="'+name+'" name="'+name+'"></div>';
                switch (type) {
                    case 'STRING':
                        html += label + inputText;
                        html += scriptOpen+'$("#'+name+'").jqxInput({placeHolder: "please enter '+name+'", height: 25, width: 200, minLength: 1})'+scriptClose;
                        break;
                    case 'DATE':
                        html += label + div;
                        html += scriptOpen+'$("#'+name+'").jqxDateTimeInput({ width: 300, height: 25 })'+scriptClose;
                        break;
                    case 'INT':
                    case 'LONG':
                        html += label + div;
                        html += scriptOpen+'$("#'+name+'").jqxNumberInput({ width: 250, height: 25, decimal: 0, decimalDigits: 0, inputMode: "simple", spinButtons: true })'+scriptClose;
                        break;
                    case 'BOOLEAN':
                        html += label + div;
                        html += scriptOpen+'$("#'+name+'").jqxCheckBox({ width: 120, height: 25 })'+scriptClose;
                        break;
                    default:
                        html += label + inputText;
                        html += scriptOpen+'$("#'+name+'").jqxInput({placeHolder: "please enter '+name+'", height: 25, width: 200, minLength: 1})'+scriptClose;
                        break;

                }
            });
        }
        return html
    };


});