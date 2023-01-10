 function serializeObject(obj) {
    var o = {};
    var a = obj.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}
 function serializeStr(obj) {
	    var str="";
	    var a = obj.serializeArray();
	    $.each(a, function() {
	    	str+=this.name+'='+this.value + '&';
	    });
	    return str;
	}
