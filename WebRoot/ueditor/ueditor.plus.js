(function () {
	var actionData;
	
	/*图片*/
	var ueditor_image;
    var InsertImage = function(id, cfgCat, callback){
    	ueditor_image = UE.getEditor(id, {
    	    isShow: false,
    	    focus: false,
    	    enableAutoSave: false,
    	    autoSyncData: false,
    	    autoFloatEnabled:false,
    	    wordCount: false,
    	    sourceEditor: null,
    	    scaleEnabled:true,
    	    cfgCat: cfgCat,
    	    toolbars: [["insertimage"]]
    	});
    	ueditor_image.ready(function () {
    		ueditor_image.addListener("beforeInsertImage", function(t, result){
    			eval(callback(t, result, actionData));
    		});
    	});
    };
    
    InsertImage.prototype = {
        insertImage: function (data) {
        	actionData = data;
        	var dialog = ueditor_image.getDialog("insertimage");
            dialog.title = '更换图片';
            dialog.render();
            dialog.open();
        }
    };
    
    window.InsertImage = InsertImage;
    
    /*文件*/
    var ueditor_file;
    var UploadFile = function(id, cfgCat, callback){
    	ueditor_file = UE.getEditor(id, {
    	    isShow: false,
    	    focus: false,
    	    enableAutoSave: false,
    	    autoSyncData: false,
    	    autoFloatEnabled:false,
    	    wordCount: false,
    	    sourceEditor: null,
    	    scaleEnabled:true,
    	    cfgCat: cfgCat,
    	    toolbars: [["attachment"]]
    	});
    	ueditor_file.ready(function () {
    		ueditor_file.addListener("afterUpfile", function(t, result){
    			eval(callback(t, result, actionData));
    		});
    	});
    };
    
    UploadFile.prototype = {
        uploadFile: function (data) {
        	actionData = data;
        	var dialog = ueditor_file.getDialog("attachment");
            dialog.title = '附件上传';
            dialog.render();
            dialog.open();
        }
    };
    
    window.UploadFile = UploadFile;
    
})();