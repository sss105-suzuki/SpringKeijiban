/**
 * ���O�C���ׂ̈ɓ��͂��ꂽ�l���`�F�b�N����B
 *
 * @param id	ID
 * @param pass	�p�X���[�h
 * @return �`�F�b�N���ʂɖ�肪�Ȃ���΁Atrue��Ԃ�
 */
function chkLogin(id, pass){
	var msg = "";
	if (id == "") {
		msg += "ID�����͂���Ă��܂���\n";
	}
	if (!(/^\d{5}$/.test(id))) {
		msg += "ID�ɂ�5���̐�������͂��Ă�������\n";
	}
	
	msg += chkPass(pass);
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		return true;
	}
}

/**
 * ���b�Z�[�W�̕ҏW�E�폜�ׂ̈ɓ��͂��ꂽ�l���`�F�b�N����B
 *
 * @param number	�ԍ�
 * @param pass		�p�X���[�h
 * @return �`�F�b�N���ʂɖ�肪�Ȃ���΁Atrue��Ԃ�
 */
function chkSelectMessage(number, pass){
	var msg = "";
	if (number == "") {
		msg += "�ԍ������͂���Ă��܂���\n";
	}
	if (!(/^\d+$/.test(number))) {
		if (!(/^\-\d+$/.test(number))) {
			msg += "�ԍ��ɂ͐��̐�������͂��Ă�������\n";
		} else {
			msg += "�ԍ��ɂ͐�������͂��Ă�������\n";
		}
	}
	
	msg += chkPass(pass);
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		return true;
	}
}

/**
 * �V�K�������ݓ��e���`�F�b�N����B
 *
 * @param name		���O
 * @param title		�^�C�g��
 * @param pass		�p�X���[�h
 * @param message	��������
 * @return �`�F�b�N���ʂɖ�肪�Ȃ���΁Atrue��Ԃ�
 */
function chkNewInput(name, title, pass, message) {
	var msg = "";
	if (name == "" || title == "" || pass == "" || message == "") {
		msg += "���͂���Ă��Ȃ����ڂ�����܂�\n";
	}
	if (message.length > 500) {
		msg += "�������ݎ�����500���܂łł�\n";
	}
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		return true;
	}
}

/**
 * �������ݕҏW���e���`�F�b�N����B
 *
 * @param message	��������
 * @return �`�F�b�N���ʂɖ�肪�Ȃ���΁Atrue��Ԃ�
 */
function chkEditInput(message) {
	var msg = "";
	if (message == "") {
		msg += "���b�Z�[�W����͂��Ă�������\n";
	}
	if (message.length > 500) {
		msg += "�������ݎ�����500���܂łł�\n";
	}
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		return true;
	}
}

/**
 * ���͂��ꂽ�p�X���[�h���`�F�b�N����B
 *
 * @param pass	�p�X���[�h
 * @return �`�F�b�N���ʂɉ������������Ԃ�
 */
function chkPass(pass) {
	var msg = "";
	if (pass == "") {
		msg += "�p�X���[�h�����͂���Ă��܂���\n";
	}
	if (!(/^[a-zA-Z0-9]+$/.test(pass))) {
		msg += "�p�X���[�h�ɂ͔��p�p�����̕��������͂��Ă�������\n";
	}
	if (!(/^.{1,20}$/.test(pass))) {
		msg += "�p�X���[�h�̎���������20���ł�\n";
	}
	return msg;
}

/**
 * �`�F�b�N���ʂɖ�肪����΁A���b�Z�[�W��\������B
 *
 * @return name		�`�F�b�N���ʂɖ�肪����΁Atrue��Ԃ�
 */
function isError(msg) {
	if (msg != "") {
		alert(msg);
		return true;
	}
	return false;
}