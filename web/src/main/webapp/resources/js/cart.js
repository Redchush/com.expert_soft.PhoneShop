$(function() {

	var restoreMsg = $("#restore_msg").html();
	var deleteMsg = $("#delete_msg").html(); 

   console.log(restoreMsg + " " + deleteMsg);


   	$("tr td label[data-action='delete'").on("click", function(evt){
		var _$this = $(this);
		var _$tr= _$this.parents("tr");
		var _$msgHolder = _$this.children("span"); 

		var _$input = _$this.children("input[type='checkbox']"); 

        // _$this.parent().parent().addClass("deleted");
        // _$tr.addClass("_deleted");
         
		if (_$input.prop("checked") ) {
				console.log("checked");
			_$input.prop("checked", false);
			_$msgHolder.html(restoreMsg)
				_$tr.addClass("_deleted"); 

		} else {
			 console.log("not");
     		_$input.prop("checked", true);
			_$msgHolder.html(deleteMsg)
			_$tr.removeClass("_deleted"); 
		}
	

   	});

	// $("td label>input[type=checkbox]").on("change", function(evt)

	// {
	// 	var _$this = $(this);
	// 	var _$tr= _$this.parent("tr");
	// 	var _$label = _$this.parent("label");

	// 	if (_$tr.prop( "checked" )) {
	// 		_$tr.addClass("deleted"); 
	// 		_$label.html(restoreMsg)
	// 	} else {
	// 		_$tr.removeClass("deleted"); 
	// 		_$label.html(deleteMsg)
	// 	}
		
	// });

});
