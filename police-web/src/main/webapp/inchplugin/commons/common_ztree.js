var setting = {
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid"
		}
	},
	callback : {
		onCheck : zTreeOnClick
	}
};

var usersetting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid"
			}
		},
		callback : {
			onCheck : zTreeOnClick22
		}
	};

function initSchool(url){
	
	$.getJSON(url, {}, function(data) {
		$.fn.zTree.init($("#classTree"), setting, data);
	});
}

//-----------------
	var setting2 = {
		check : {
			enable : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid"
			}
		},
		callback : {
			onClick : onClick
		}
	};
	
	var setting3 = {
			check : {
				enable : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid"
				}
			},
			callback : {
				onClick : onClickClass
			}
		};

var setting33 = {
    check : {
        enable : false
    },
    data : {
        simpleData : {
            enable : true,
            idKey : "id",
            pIdKey : "pid"
        }
    },
    callback : {
        onClick : onClickClassnot
    }
};
	
	var setting4 = {
			check : {
				enable : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid"
				}
			},
			callback : {
				onClick : onClickPid
			}
		};

function initSchool2(url){
	$.getJSON(url, {}, function(data) {
		$.fn.zTree.init($("#classTree"), usersetting, data);
		$.fn.zTree.init($("#headerTree"), setting2, data);
	});
}
//只能选择根节点
function initSingerSchool(url){
	$.getJSON(url, {}, function(data) {
		$.fn.zTree.init($("#headerTree"), setting3, data);
	});
}
//可以不选择根节点
function initSingerSchool2(url){
    $.getJSON(url, {}, function(data) {
        $.fn.zTree.init($("#headerTree"), setting33, data);
    });
}

function initSingerSchoolTree(url){
	$.getJSON(url, {}, function(data) {
		$.fn.zTree.init($("#headerTree"), setting4, data);
	});
}

function onClick(event, treeId, treeNode, clickFlag) {
	
	if(treeNode.isParent){
		return 
	}
	
	$("#headteacher").val(treeNode.id);
	$("#headName").val(treeNode.name);

	hideMenu();  
}

function onClickClass(event, treeId, treeNode, clickFlag) {
	
	if(treeNode.isParent){
		return 
	}
	
	$("#classid").val(treeNode.id);
	$("#headName").val(treeNode.name);
	
	hideMenu();  
}

function onClickClassnot(event, treeId, treeNode, clickFlag) {
    $.getJSON('/window/opList.do', {'orgid':getCurrentRoot(treeNode)}, function(data) {
        console.log(treeNode,data)
        $.fn.zTree.init($("#windowTree"), getSetting("guid","pguid",true,selwindow), data);
    });


    $("#classid").val(treeNode.id);
    $("#headName").val(treeNode.name);

    hideMenu();
}

function getCurrentRoot(treeNode){
    if(treeNode.getParentNode()!=null){
        var parentNode = treeNode.getParentNode();
        return getCurrentRoot(parentNode);
    }else{
        return treeNode.id;
    }
}

function onClickPid(event, treeId, treeNode, clickFlag) {
	
	if(treeNode.isParent){
		return 
	}
	
	
	$("#pid").val(treeNode.id);
	$("#headName").val(treeNode.name);
	
	hideMenu();  
}

//-------------
var rolesetting = {
		check: {
			enable: true
		},
		callback:{
            onCheck:onCheckRole
        },
		data: {
			simpleData: {
				enable: true,
				idKey: "id",    
                pIdKey: "pid"
			}
		}
	};

	function initRole(url){
		$.getJSON(url, {}, function(data) {
			$.fn.zTree.init($("#roleTree"), rolesetting, data);
		});
	}
	
	function onCheckRole(e, treeId, treeNode) {
		
		
		var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		nodes = treeObj.getCheckedNodes(true);
		var ids = "",names="";
		for (var i = 0; i < nodes.length; i++) {
			ids += nodes[i].id + ",";
			names += nodes[i].name + ",";
		}
		
		if (ids.length > 0)  
	    {
			ids = ids.substring(0, ids.length - 1);
			names = names.substring(0, names.length - 1);
	    }  
		$("#roleName").val(names);
		$("#selRoles").val(ids);
	}


	function zTreeBeforeClick(treeId, treeNode, clickFlag) {  
        var check = (treeNode && !treeNode.isParent);  
        if (!check){  
        	// alert("只能选中根节点");
        }  
        return check;  
    }  

	function zTreeOnClick(event, treeId, treeNode) {  
	    var zTree = $.fn.zTree.getZTreeObj("classTree"), 
	    nodes = zTree.getCheckedNodes(true), 
	    v="",
	    name="";
	    for (var i = 0, l = nodes.length; i < l; i++) { 
	    	
	      if(!nodes[i].isParent){
	    	  v += nodes[i].id + ",";  
	    	  name += nodes[i].name + ","; 
	      }
	    }
	    if (v.length > 0)  
	    {
	    	v = v.substring(0, v.length - 1);
	    	name = name.substring(0, name.length - 1);
	    }
	    
	    var catObj = $("#className"),catId = $("#classid");  
	    catObj.attr("value", name);  
	    catId.attr("value", v);  
	} 
	
	function zTreeOnClick22(event, treeId, treeNode) {  
	    var zTree = $.fn.zTree.getZTreeObj("classTree"), 
	    nodes = zTree.getCheckedNodes(true), 
	    v="",
	    name="";
	    for (var i = 0, l = nodes.length; i < l; i++) { 
	    	
	      //if(!nodes[i].isParent){
	    	  v += nodes[i].id + ",";  
	    	  name += nodes[i].name + ","; 
	      //}
	    }
	    if (v.length > 0)  
	    {
	    	v = v.substring(0, v.length - 1);
	    	name = name.substring(0, name.length - 1);
	    }    
	    var catObj = $("#className"),catId = $("#classid");  
	    catObj.attr("value", name);  
	    catId.attr("value", v);  
	} 

	var mObj;
    function showMenu(nameobj,menuObj) { 
    	
        var cityObj =nameobj; //$("#className");  
        var cityOffset =nameobj.offset();  // $("#className").offset();  
        menuObj.css({  
            left : cityOffset.left + "px",  
            top : cityOffset.top + cityObj.outerHeight() + "px"  
        }).slideDown("fast");  
        
        mObj=menuObj;
        $("body").bind("mousedown", onBodyDown);
    }  
    
    function hideMenu() { 
    	mObj.fadeOut("fast");  
        $("body").unbind("mousedown", onBodyDown);  
    }  
    
    function onBodyDown(event) {  
    	if (!(event.target.id == "menuContent"  
            || event.target.id == "menuContent2" 
            || event.target.id == "menuContent3" 
        	|| $(event.target).parents("#menuContent").length > 0
        	|| $(event.target).parents("#menuContent2").length > 0
        	|| $(event.target).parents("#menuContent3").length > 0)
           ) { 
            hideMenu();  
        }  
    }
    
    //清空tree选中
    function clearZTree() {
    	$(".ztree").each(function(){
    		var obj=$.fn.zTree.getZTreeObj($(this).attr("id"));
    		
    		if(obj!=null){
    			obj.cancelSelectedNode();
    			obj.checkAllNodes(false);
    		}
    	})
	}
   
    //设置tree选中节点checkbox
    function initCheckState(data,treename,nameObj,idObj){
    	
    	 var zTree2 = $.fn.zTree.getZTreeObj(treename);
		 var node ; 
		 var cname="";
		 var cid="";
		 $.each(data, function(i, id) {
			 
			 node = zTree2.getNodeByParam("id",id);
			 if(node){
				 
				 if(!node.isParent){
					 zTree2.checkNode(node, true, true);
					 
					 if(cname.length>0){
						 cname=cname+","+node.name;
						 cid=cid+","+node.id;
					 }else{
						 cname=node.name;
						 cid=node.id;
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
    
    function initCheckUserState(data,treename,nameObj,idObj){
    	
    	 var zTree2 = $.fn.zTree.getZTreeObj(treename);
		 var node ; 
		 var cname="";
		 var cid="";
		 $.each(data, function(i, id) {
			 
			 node = zTree2.getNodeByParam("id",id);
			 if(node){
				 
				 //if(!node.isParent){
					 zTree2.checkNode(node, true, false);
					 
					 if(cname.length>0){
						 cname=cname+","+node.name;
						 cid=cid+","+node.id;
					 }else{
						 cname=node.name;
						 cid=node.id;
					 }
				 //}
				 
				  var parent = node.getParentNode();
		          if(parent&&!parent.open){
		        	  zTree2.expandNode(parent,true,true);
		          }
			 }
		 })
		 
		 nameObj.val(cname);
		 idObj.val(cid);
   }
    
    //设置tree选中节点checkbox(单选)
    function initSelectState(id,treename,showobj){
    	
    	 var zTree2 = $.fn.zTree.getZTreeObj(treename);
			 
		 var node = zTree2.getNodeByParam("id",id);
		 
		 if(node==null){
			 return ;
		 }
		 
		 zTree2.selectNode(node);
		 showobj.val(node.name);
    }


