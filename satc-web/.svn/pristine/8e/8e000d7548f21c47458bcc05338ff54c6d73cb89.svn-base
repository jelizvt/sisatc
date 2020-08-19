jQuery(function() {
	jQuery("#btnMenuTrib").click(function() {
		jQuery(".cont_smenu").slideToggle("100");
		return false;
	});
});

/* Date and Time */
function loadDate() {
	var f = new Date();
	var datePrint = pad2(f.getDate()) + "/" + pad2((f.getMonth() + 1)) + "/"
			+ f.getFullYear();
	jQuery('#tdDate').html(datePrint);
}

function mueveReloj() {
	var momentoActual = new Date();
	var hora = momentoActual.getHours();
	var minuto = momentoActual.getMinutes();
	var segundo = momentoActual.getSeconds();

	var horaImprimible = hora + " : " + pad2(minuto) + " : " + pad2(segundo);

	jQuery('#tdTime').html(horaImprimible);

	setTimeout("mueveReloj()", 1000);
}

function pad2(number) {
	return (number < 10 ? '0' : '') + number;
}

function selectMenu(comp){
	jQuery('.topSpan').removeClass('top_link_selected').addClass('top_link');
	jQuery('#'+comp).addClass('top_link_selected');
}