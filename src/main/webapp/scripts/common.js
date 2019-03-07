function patternPhone(phone, messageConfirm) {
	var checkPattern = /^((\+\d{1,3})(\(\d{1,3}\))?)?\d{4,}$/m.test(phone);
	var res;
	console.log(phone);
	if (checkPattern) {
		res = true;
	} else {
		res = window.confirm(messageConfirm);
	}
	return res;
}
