function getSetting(id,pid,isenable,myfun){

    var wsetting = {
        check: {
            enable: isenable
        },
        callback:{
            onCheck:myfun
        },
        data: {
            simpleData: {
                enable: true,
                idKey: id,
                pIdKey: pid
            }
        }
    };

    return wsetting;
}

function initWin(url){

    $.getJSON(url, {}, function(data) {
        $.fn.zTree.init($("#windowTree"), getSetting("guid","pguid",true,selwindow), data);
    });
}

function selwindow(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true),
        v="",
        name="";
    for (var i = 0, l = nodes.length; i < l; i++) {

        if(!nodes[i].isParent){
            v += nodes[i].guid + ",";
            name += nodes[i].name + ",";
        }
    }
    if (v.length > 0)
    {
        v = v.substring(0, v.length - 1);
        name = name.substring(0, name.length - 1);
    }

    var catObj = $("#winName"),catId = $("#winguid");
    catObj.attr("value", name);
    catId.attr("value", v);
}

function clearZTree2() {
    $(".ztree").each(function(){
        var obj=$.fn.zTree.getZTreeObj($(this).attr("id"));

        if(obj!=null){
            obj.cancelSelectedNode();
            obj.checkAllNodes(false);
        }
    })
}

function initCheckStateNew(data,treename,nameObj,idObj){

    var zTree2 = $.fn.zTree.getZTreeObj(treename);
    var node ;
    var cname="";
    var cid="";
    $.each(data, function(i, id) {

        node = zTree2.getNodeByParam("guid",id);
        if(node){

            if(!node.isParent){
                zTree2.checkNode(node, true, true);

                if(cname.length>0){
                    cname=cname+","+node.name;
                    cid=cid+","+node.guid;
                }else{
                    cname=node.name;
                    cid=node.guid;
                }
            }

            var parent = node.getParentNode();
            if(parent&&!parent.open){
                zTree2.expandNode(parent,true,true);
            }
        }
    })

    nameObj.val(cname);
    idObj.val(cid);
}