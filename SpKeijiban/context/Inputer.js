/**
 * タブを切り替える
 *
 * @param tabname	切替先のタブID
 * @param boxname	切替先のボックスID
 */
function ChangeTab(tabname, boxname) {
	// タブ操作
	document.getElementById('tab1').className = "nonacttab";
	document.getElementById('tab2').className = "nonacttab";
	document.getElementById('tab3').className = "nonacttab";
	document.getElementById(tabname).className = "acttab";
	// ボックス操作
	// --全部消す
	document.getElementById('box1').style.display = 'none';
	document.getElementById('box2').style.display = 'none';
	document.getElementById('box3').style.display = 'none';
	// --指定箇所のみ表示
	document.getElementById(boxname).style.display = 'block';
}
/**
 * 選択した番号の書き込みを編集テキストエリアに反映させる
 *
 * @param number	選択した番号
 */
function getSelectMessage(number) {
	document.getElementById('editmsg').value = document.getElementById('msg'+ number).innerText;
}