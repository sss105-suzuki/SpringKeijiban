package jp.co.soramasu.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResponseList {
	private ArrayList<SingleResponse> list; 
	
	public ResponseList () {
		list = new ArrayList<SingleResponse>();
	}
	
	//クイックソート
	private void quickSort (String mode, int left, int right){
		if (mode.equals("DisNum") || mode.equals("ResNum")) {
			if (left <= right) {
				int pNum = 0;
				if (mode.equals("DisNum")) {
					pNum = list.get((left+right) / 2).getDisNum();
				} else if (mode.equals("ResNum")) {
					pNum = list.get((left+right) / 2).getResNum();
				}
				int lNum = left;
				int rNum = right;
				
				while(lNum <= rNum) {
					if (mode.equals("DisNum")) {
						while(list.get(lNum).getDisNum() < pNum){ lNum++; }
						while(list.get(rNum).getDisNum() > pNum){ rNum--; }
					} else if (mode.equals("ResNum")) {
						while(list.get(lNum).getResNum() < pNum){ lNum++; }
						while(list.get(rNum).getResNum() > pNum){ rNum--; }
					}
					
					if (lNum <= rNum) {
						SingleResponse tmp = list.get(lNum);
						list.set(lNum, list.get(rNum));
						list.set(rNum, tmp);
						lNum++; 
						rNum--;
					}
				}
				quickSort(mode, left, rNum);
				quickSort(mode, lNum, right);
			}
		}
	}
	
	//返信宛先番号>返信番号の順に昇順ソート
	private void ResponseSort () {
		quickSort("DisNum", 0, list.size()-1);
		
		int nowNum = list.get(0).getDisNum();
		int leftNum = 0;
		for (int i=0; i<list.size(); i++) {
			if (nowNum != list.get(i).getDisNum()) {
				quickSort("ResNum", leftNum, i-1);
				nowNum = list.get(i).getDisNum();
				leftNum = i;
			} else 	if (i == list.size()-1) {
				quickSort("ResNum", leftNum, i);
			}
		}
	}
	
	public void setAllResponse (ResultSet newone) {
		
		try {
			while (newone.next()) {
				SingleResponse setResponse = new SingleResponse();
				setResponse.setDisNum(newone.getInt("distinationnum"));
				setResponse.setResNum(newone.getInt("responsenum"));
				setResponse.setDayNum(newone.getString("daynum"));
				setResponse.setEditPass(newone.getString("edit_pass"));
				setResponse.setMessage(newone.getString("message"));
				setResponse.setUserName(newone.getString("user_name"));
				list.add(setResponse);
				setResponse = null;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	//特定の書き込み1つに対する全レスの抜出
	public ResponseList getDozenResponse (int distinationNum) {
		ResponseList resultRes = new ResponseList ();
		for (int i=1;i<=list.size();i++) {
			SingleResponse listSercher = list.get(i);
			if (listSercher.getDisNum() == distinationNum) {
				
			}
			
		}
		
		if (resultRes.size() != 0) {
			return resultRes;
		} else {
			return null;
		}
		
	}
	
	//特定のレス1つの抜出
	public SingleResponse getSingleResponse (int distinationNum,int responseNum) {
		for (int i=1;i<=list.size();i++) {
			SingleResponse listSercher = list.get(i);
			if (listSercher.getDisNum() == distinationNum && listSercher.getResNum() == responseNum) {
				return listSercher;
			}
		}
		return null;
	}
	
	public int size (){
		return list.size();
	}

}
