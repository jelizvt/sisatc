function validaInputCbo(comp) {
	var idInputCbo = comp.id;
	var idSplit = idInputCbo.split('comboboxField');
	var idCbo = new String(idSplit[0]);
	var valueInput = document.getElementById(idInputCbo).value;
	var compCbo = document.getElementById(idCbo).component;
	var lista = compCbo.comboList.itemsText; // #{rich:component('cboMotivoDecla')}.comboList.itemsText;
	var correcto = false;
	for (var i = 0; i < lista.length; i++) {
		if (jQuery.trim(lista[i]) == valueInput) {
			correcto = true;
			break;
		}
	}
	if (!correcto) {
		document.getElementById(idInputCbo).value = '';
	}
}

function showListCbo(comp, e) {
	var tecla = (document.all) ? e.keyCode : e.which;

	var idDivCbo = comp.id;
	var idSplit = idDivCbo.split('combobox');
	var idCbo = new String(idSplit[0]);

	if (tecla == 32) {
		document.getElementById(idCbo).component.showList();
		return false;
	}
	return true;
}

function validaEnteroPositivo(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == 8 || tecla == 0) {
		return true;
	}
	patron = /\d/;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}

function validaEnteroPositivo2(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == 8 || tecla == 0) {
		return true;
	}
	patron = /\d|\./;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}

function validarCarateres(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == 37)
		return false;
	if (tecla == 45)
		return true;
	if (tecla == 46)
		return true;
	if (tecla == 47)
		return true;
	if (tecla == 241)
		return true;
	if (tecla == 209)
		return true;
	if (tecla == 8)
		return true;
	patron = /[A-Za-z\s\&\%]/;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}

function validarLetrasNroValor(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == 8 || tecla == 0) {
		return true;
	}
	patron = /[0-9\-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}

// function convertToUpper(comp) {
// var temp = comp.value.toUpperCase();
// comp.value = temp;
// }
//
// function uppercase(comp) {
// var temp = comp.value.toUpperCase();
// comp.value = temp;
// }

function isNumeric(str, e) {
	var key = (document.all) ? e.keyCode : e.which;
	var values = str + String.fromCharCode(key);
	if (key == 46)
		values = str + String.fromCharCode(key) + '0';
	str = alltrim(values);
	var result = /^[-+]?[0-9]{1,10}(\.[0-9]{1,2})?$/.test(str);
	return result;
}

function alltrim(str) {
	return str.replace(/^\s+|\s+$/g, '');
}

function numbersonly(strString, e) {
	var unicode = (document.all) ? e.keyCode : e.which;
	var unicodevalido = false;
	if (unicode == 8 || unicode == 0) {
		return true;
	} else {
		if (unicode == 46) {
			unicodevalido = true;
		} else if (unicode < 48 || unicode > 57) {
			unicodevalido = false;
		} else {
			unicodevalido = true;
		}
	}
	if (unicodevalido) {
		return isNumeric(strString, e);
	} else {
		return false;
	}
}

function numberCurrency(comp) {
	var number = comp.value;
	number = number.replace(/,/g, '');
	var lastChar = number.charAt(number.length - 1);
	if (isNaN(lastChar) && lastChar != '.') {
		number = number.replace(lastChar, '');
	}
	var splitPoint = number.split('.');
	var integer = splitPoint[0];
	var decimals = splitPoint[1];

	var numComas = 0;
	var length = integer.length;
	var div = length / 3;
	var splitDiv = String(div).split('.');
	var intDiv = splitDiv[0];
	var decDiv = splitDiv[1];

	if (decDiv == undefined || decDiv == '') {
		numComas = Number(intDiv) - 1;
	} else {
		numComas = Number(intDiv);
	}

	var res = "";
	if (numComas > 0) {
		var count = 0;
		var numComasAgreg = 0;
		for (var i = length - 1; i >= 0; i--) {
			count++;
			if (count % 3 == 0 && numComasAgreg < numComas) {
				res = ',' + integer.charAt(i) + res;
				numComasAgreg++;
			} else {
				res = integer.charAt(i) + res;
			}
		}
	} else {
		res = integer;
	}

	if (decimals != undefined) {
		res += '.' + decimals;
	}

	$(comp.id).value = res;
}

function openWindow(url) {

	var ancho = jQuery(window).width();
	var alto = jQuery(window).height();

	var opciones = "fullscreen=0,toolbar=0,location=1,status=1,menubar=0,scrollbars=1,resizable=1,width="
			+ ancho + ",height=" + alto + ",left=0,top=0";

	var ventana = window.open(url, "ventana", opciones, 1);
}

function focusNroDocAnexos(comp) {
	var idChk = comp.id;
	var idSplit = idChk.split('chkDoc');
	var idTxt = idSplit[0] + 'txtGlosa';

	if (document.getElementById(idChk).checked)
		document.getElementById(idTxt).focus();
}

function validaPlacaMotor(e) {
	tecla = (document.all) ? e.keyCode : e.which;

	if (tecla == 8 || tecla == 0) {
		return true;
	}

	var value = String.fromCharCode(tecla);

	var result = /^[A-Za-z0-9(\-)(\ñ)(\Ñ)]$/.test(value);

	if (!result) {
		return false;
	}
	return true;
}

/* operaciones con 4 decimales */
function isNumeric4dec(str, e) {
	var key = (document.all) ? e.keyCode : e.which;
	var values = str + String.fromCharCode(key);
	if (key == 46)
		values = str + String.fromCharCode(key) + '0';
	str = alltrim(values);
	var result = /^[-+]?[0-9]{1,8}(\.[0-9]{1,6})?$/.test(str);
	return result;
}

function numbersonly4dec(strString, e) {
	var unicode = (document.all) ? e.keyCode : e.which;
	var unicodevalido = false;
	if (unicode == 8 || unicode == 0) {
		return true;
	} else {
		if (unicode == 46) {
			unicodevalido = true;
		} else if (unicode < 48 || unicode > 57) {
			unicodevalido = false;
		} else {
			unicodevalido = true;
		}
	}
	if (unicodevalido) {
		return isNumeric4dec(strString, e);
	} else {
		return false;
	}
}

jQuery(function() {
	// jQuery("#btnAniosDeu").click(function() {
	// jQuery(".tooltipAnios").slideToggle("100");
	// return false;
	// });
});

// id: identificador del componente del cual se pondra el focus
function focusPressEnter(e, id) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == Event.KEY_RETURN) {
		$(id).focus();
	}
}

// id: identificador del componente del cual se hara la llamada al evento click
function clickEventButtonPressEnter(e, id) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla == Event.KEY_RETURN) {
		$(id).click();
	}
}

function desactivaDiasPasados_Calendar(day) {
	var curDt = new Date();
	curDt.setHours(0, 0, 0, 0);
	var dayInMiliseconds = 60 * 60 * 24 * 1000;
	if (Math.round((curDt.getTime() - day.date.getTime()) / dayInMiliseconds) <= 0)
		return true;
	else
		return false;
}

function desactivaDiasPasadosClass_Calendar(day) {
	var curDt = new Date();
	curDt.setHours(0, 0, 0, 0);
	var dayInMiliseconds = 60 * 60 * 24 * 1000;
	if (Math.round((curDt.getTime() - day.date.getTime()) / dayInMiliseconds) < 0)
		return '';
	else
		return 'rich-calendar-boundary-dates';
}

//var ActiveModal = new function() {
//	this.activeModalPanel = null;
//	this.setActiveModalPanel = function(a) {
//	this.activeModalPanel = a;
//	};
//
//};

var ActiveModal = new function() {
	this.visiblePanelIDs = new Array();
	this.activeModalPanel = null;
	this.removeModal = function(a) {
		if (a && (a != "wait")) {
			this.visiblePanelIDs.splice(0, 1);
			this.activeModalPanel = this.visiblePanelIDs[0];
		}
	};
	this.addModal = function(a) {
		if (a && (a != "wait")) {
			a = a.id.replace(/Container/, '');
			this.visiblePanelIDs.splice(0, 0, a);
			this.activeModalPanel = this.visiblePanelIDs[0];
		}
	};
};