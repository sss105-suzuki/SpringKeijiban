/**
 * �^�u��؂�ւ���
 *
 * @param tabname	�ؑ֐�̃^�uID
 * @param boxname	�ؑ֐�̃{�b�N�XID
 */
function ChangeTab(tabname, boxname) {
	// �^�u����
	document.getElementById('tab1').className = "nonacttab";
	document.getElementById('tab2').className = "nonacttab";
	document.getElementById('tab3').className = "nonacttab";
	document.getElementById(tabname).className = "acttab";
	// �{�b�N�X����
	// --�S������
	document.getElementById('box1').style.display = 'none';
	document.getElementById('box2').style.display = 'none';
	document.getElementById('box3').style.display = 'none';
	// --�w��ӏ��̂ݕ\��
	document.getElementById(boxname).style.display = 'block';
}
/**
 * �I�������ԍ��̏������݂�ҏW�e�L�X�g�G���A�ɔ��f������
 *
 * @param number	�I�������ԍ�
 */
function getSelectMessage(number) {
	document.getElementById('editmsg').value = document.getElementById('msg'+ number).innerText;
}