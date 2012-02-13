package com.db;


import java.sql.ResultSet;
import java.util.Vector;





public class BaseEntity {	
	private  String m_strTableName;

	public String getTableName() {
		return m_strTableName;
	}
	public void setTableName(String m_strTable) {
		this.m_strTableName = m_strTable;
	}
	public final String m_colROW_ID="ROW_ID";
	public int m_iROW_ID;
	public int getM_iROW_ID() {
		return m_iROW_ID;
	}
	public void setM_iROW_ID(Integer m_iROW_ID) {
		this.m_iROW_ID = m_iROW_ID;
	}
	public String [] arrayAllCol;
	public String[] getArrayAllCol() {
		return arrayAllCol;
	}
	public void setArrayAllCol(String[] arrayAllCol) {
		this.arrayAllCol = arrayAllCol;
	}
	public  String   m_strTableCreate;
	public String getM_strTableCreate() {
		return m_strTableCreate;
	}
	public void setM_strTableCreate(String m_strTableCreate) {
		this.m_strTableCreate = m_strTableCreate;
	}

	public BaseEntity(){
		
	}
	
	public BaseEntity(ResultSet cursor){
		
		
	}
	
	public Vector<BaseEntity> PackageAllData(ResultSet cursor){
		
		return null;
	}
	

	
	public String GetInsertSql()
	{
	
		return "";
	}
	
	public String GetDeleteSql()
	{
		return "";
	}
	
	public String GetModifySql(){
		return "";
	}
	
}
