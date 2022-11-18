/**
 * 出勤時間フォーカスアウト処理
 */
function focusOutClockIn(obj) {
	// コロンを付与する
	// toColon(obj);
}
/**
 * 出勤時間フォーカスイン処理
 */
function focusInClockIn(obj) {
	// コロンを解除する
	// offColon(obj);
}
/**
 * 帰社時間フォーカスアウト処理
 */
function focusOutClockOut(obj) {
	// コロンを付与する
	// toColon(obj);
}
/**
 * 帰社時間フォーカスイン処理
 */
function focusInClockOut(obj) {
	// コロンを解除する
	// offColon(obj);
}
/**
 * コロン編集を行うFunction
 */
function toColon(obj) {
	if ((obj.value).trim().length == 4 && !isNaN(obj.value)) {
		var str = obj.value.trim();
		var h = str.substr(0, 2);
		var m = str.substr(2, 2);
		obj.value = h + ":" + m;
	}
}

/**
 * コロン編集を解除するFunction
 */
function offColon(obj) {
	var reg = new RegExp(":", "g");
	var chgVal = obj.value.replace(reg, "");
	if (!isNaN(chgVal)) {
		obj.value = chgVal;  //値セット
		obj.select();        //全選択
	}
}