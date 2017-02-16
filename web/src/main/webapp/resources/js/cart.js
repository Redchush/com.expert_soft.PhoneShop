$(function() {

	var restoreMsg = $("#restore_msg").html();
	var deleteMsg = $("#delete_msg").html();

	console.log(restoreMsg + " " + deleteMsg);


	$("tr td label[data-action='delete']").on("click", function(evt){
		var _$this = $(this);
		evt.preventDefault();
		var _$tr= _$this.parents("tr");
		var _$input = _$this.children("input[type='checkbox']");

		_$input.prop("checked", function( i, val ) {
			var result = !val;
			_$tr.toggleClass("_deleted");
			if(result){
				$(this).next().html(restoreMsg);
			} else{
				$(this).next().html(deleteMsg);
			}
			return result;
		});
	});
});
