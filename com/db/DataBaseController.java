package com.db;







import java.sql.ResultSet;

import java.util.Vector;



public class DataBaseController  {
	public static final String TAG="ZSDataBaseController";
	// ִ��open���������ݿ�ʱ�����淵�ص����ݿ����
//	public static SQLiteDatabase		mSQLiteDatabase	;
	private static String m_strDatabaseName="database.db";
	private static String m_strDataPath="sdcard/zsmobile";
	private static String m_strDataPath2="data/data/qiaoqing.zsmobile/databases";
	private static boolean m_bReadFromSD=false;


	

	
	
	/**
	 * @return the m_strDatabaseName
	 */
	public static String getDatabaseName() {
		return m_strDatabaseName;
	}

	/**
	 * @param m_strDatabaseName the m_strDatabaseName to set
	 */
	public static void setDatabaseName(String m_strDatabaseName) {
		DataBaseController.m_strDatabaseName = m_strDatabaseName;
	}

	/**
	 * @return the m_strDataPath
	 */
	public static String getDataPath() {
		return m_strDataPath;
	}

	/**
	 * @param m_strDataPath the m_strDataPath to set
	 */
	public static void setDataPath(String m_strDataPath) {
		DataBaseController.m_strDataPath = m_strDataPath;
	}

	/**
	 * @return the m_bReadFromSD
	 */
	public static boolean isReadFromSD() {
		return m_bReadFromSD;
	}

	/**
	 * @param m_bReadFromSD the m_bReadFromSD to set
	 */
	public static void setReadFromSD(boolean m_bReadFromSD) {
		DataBaseController.m_bReadFromSD = m_bReadFromSD;
	}

	
	// �����ݿ⣬�������ݿ����
	public static void open() 
	{
		
	
		
	}

	// �ر����ݿ�
	public static void close()
	{
		
	}

	
	public static Vector<BaseEntity> GetAllData(BaseEntity entity){
		
		String strSql = "select * from " + entity.getTableName();
		
//		ResultSet obj = mSQLiteDatabase.rawQuery(strSql, null);
		
		return null;// entity.PackageAllData(obj);
		//return new ArrayList<BaseEntity>();
	}
	
	public static Vector<BaseEntity> GetAllData(BaseEntity entity,String strWhereValue){
		
		String strSql = "select * from " + entity.getTableName()+" where "+strWhereValue;
		
//		Cursor obj = mSQLiteDatabase.rawQuery(strSql, null);
		
		return null;//entity.PackageAllData(obj);
		//return new ArrayList<BaseEntity>();
	}
	
	public static void AddEntity(BaseEntity entity){
		
		//��ȡsql��䣬ִ��֮
		String strSql = entity.GetInsertSql();
		
		try {
//			mSQLiteDatabase.execSQL(strSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void DeleteEntity(BaseEntity entity){
		//������
		
		//��ȡsql��䣬ִ��֮
		String strSql = entity.GetDeleteSql();
		try {
//			mSQLiteDatabase.execSQL(strSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�ر�����
			
	}
	
	public static void ModifyEntity(BaseEntity entity){
		//������
			
	   //��ȡsql��䣬ִ��֮
		String strSql = entity.GetModifySql();
		try {
//			mSQLiteDatabase.execSQL(strSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�ر�����
		
	}
}
