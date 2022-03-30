package com.cnooc.platform.page;


/**
* @ClassName: DictionaryReplace 
* @Description: TODO 替换字典内容Bean
* @author LZ.T
* @date 2016-11-27 上午12:12:07 
* @version V2.0
 */
public class DictionaryReplace {
	public DictionaryReplace() {
	}

	public DictionaryReplace(String column, String fromColumn, String dicType) {
		this.column = column;
		this.fromColumn = fromColumn;
		this.dicType = dicType;
	}

	private String column;
	private String fromColumn = column;
	private String dicType;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getFromColumn() {
		return fromColumn;
	}

	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

}
