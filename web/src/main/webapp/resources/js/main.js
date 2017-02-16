$(function() {

    var  _$output = $("#msg").hide();
    var _$cartInfo = $("#cart_info");
    var _$size = _$cartInfo.find('span[data-out="size"]');
    var _$subtotal = _$cartInfo.find('span[data-out="subtotal"]');
    var _$form_groups = $(".form-group");

    console.log(" size: " + _$size.html() + "subtotal: " + _$subtotal.html());


    $('form[name=doAddToCartForm]').submit(function (evt) {
        console.log("ajax called outForm");
        _$output.hide();
        _$form_groups.removeClass("has-error");

        evt.preventDefault();

        var phoneId = $(this).find('input[name=phoneId]').val();
        var _$quantityInput = $(this).find('input[name=quantity]');
        var quantity = _$quantityInput.val();


        var modelQualifier = 'span[data-save="' + phoneId + '"]';
        var model = $(modelQualifier).html();
        console.log("Model: " + model);

        phoneId = jQuery.trim(phoneId);
        quantity = jQuery.trim(quantity);

        var url = 'add_to_cart';
        var attr =  $(this).attr('action');
        if (typeof attr !== typeof undefined && attr !== false) {
           url = attr;
        }
        sendAjaxToCart(phoneId, quantity, _$quantityInput, model, url);
    });

    // private String msg;
    // private String code;
    // private ShortCart result;
    function sendAjaxToCart(phoneId, quantity, _$input, model, url) {
        removeErrorClass(_$input); 
        $.ajax({
            url: url,
            dataType: "json",
            type: "GET",
            data: (
            {phoneId :phoneId,
             quantity: quantity}),
            success: function(data) {
                 console.log("msg : " + data.msg);
                 changeCartCurriculum(data.result.totalPhonesCount,
                                      data.result.subtotal,
                                      data.msg);
                _$input.val('1');
            },
            500 : function (data) {
                _$output.html(data.msg);
            },
            error: function (jqXHR) {
                console.log(jqXHR.responseText);

                var msg = jqXHR.responseText + '';
                var outMsg = msg.split('{form}').join(model);
                _$output.html(outMsg);
                _$output.show();
                addErrorClassClass(_$input);

            },
            timeout: function () {
                _$output.html("Sorry, your request can't be executed");
                _$input.val('1');
            }
        });
    }

    function addErrorClassClass(_$input) {
        _$input.parent(".form-group").addClass("has-error");
        _$input.parent(".form-group-sm").addClass("has-error");
    }

    function removeErrorClass(_$input) {
        _$input.parent(".form-group").removeClass("has-error");
        _$input.parent(".form-group-sm").removeClass("has-error");
    }

   

    function changeCartCurriculum(size, subtotal, msg){
        _$size.html(size);
        _$subtotal.html(subtotal);
        _$output.html(msg).show(); 
    }
});

