$(function() {

// public class AjaxResponseCart implements Serializable {
//     private String msg;
//     private String code;
//     private CartCurriculum result;
// public class CartCurriculum implements Serializable{
//     private Integer cartSize;
//     private BigDecimal cartSubtotal;



    var  _$output = $("#msg").hide();
    var _$cartInfo = $("#cart_info");
    var _$size = _$cartInfo.find('span[data-out="size"]');
    var _$subtotal = _$cartInfo.find('span[data-out="subtotal"]');

    console.log(" size: " + _$size.html() + "subtotal: " + _$subtotal.html());


    $('form[name=doAddToCartForm]').submit(function (evt) {
        console.log("ajax called outForm");
        evt.preventDefault();
        var _$quantityInput = $(this).find('input[name=phoneId]');
        var phoneId = _$quantityInput.val();
        var quantity = $(this).find('input[name=quantity]').val();

        var model = $("span[data-save=" + phoneId + "]").html();
        console.log("Model" + model);

        phoneId = jQuery.trim(phoneId);
        quantity = jQuery.trim(quantity);

        sendAjaxToCart(phoneId, quantity, _$quantityInput, model);
    });

    function sendAjaxToCart(phoneId, quantity, _$input, model) {
        $.ajax({
            url: 'add_to_cart',
            dataType: "json",
            type: "GET",
            data: (
            {phoneId :phoneId,
             quantity: quantity}),
            success: function(data) {
                 console.log("data : " + data);
                 changeCartCurriculum(data.result.cartSize,
                                      data.result.cartSubtotal,
                                      data.msg);
                 _$input.val(1);

            },
            error: function (data) {
                _$output.html("The phone " + model + "can't be saved in cart\n." + data);
                _$output.show();
                _$input.val(1);

            },
            timeout: function () {
                _$output.html("Sorry, your request can't be executed");
                _$input.val(1);
            }
        });

        // private String msg;
        // private String code;
        // private CartCurriculum result;
    }

   

    function changeCartCurriculum(size, subtotal, msg){
        _$size.html(size);
        _$subtotal.html(subtotal);
        _$output.html(msg).show(); 
    }

     function test(){
         changeCartCurriculum(10, 10, "msg");  
     }

});



// $("button[name = doAddToCartOutForm]").on("submit", function (evt) {
//     console.log("ajax called");
//     evt.stopPropagation();
//
//     var phoneId = $(this).val();
//     var formId = $(this).attr('form');
//     var inputQualifier = "input[form=" + formId;
//     var quantity = $(inputQualifier).val();
//
//     console.log("Receive in doAjaxAddToCart: phoneId "
//         + phoneId + ", quantity : " + quantity);
//     sendAjaxToCart(phoneId, quantity);
// });
//
//
// $("button[name = doAddToCartInForm]").on("submit", function (evt) {
//     console.log("ajax called inForm");
//     evt.stopPropagation();
//
//     var phoneId = $(this).val();
//     var _$parent = $(this).parent('form');
//     var quantity = _$parent.find('input[name=quantity]').val();
//
//     console.log("Receive in doAjaxAddToCart: phoneId "
//         + phoneId + ", quantity : " + quantity);
//     sendAjaxToCart(phoneId, quantity);
// });