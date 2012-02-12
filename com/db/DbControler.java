package com.db;
import java.sql.*;  

public class DbControler {
	public DbControler()throws Exception  {
		// TODO Auto-generated constructor stub
		Class.forName("org.sqlite.JDBC");
	    Connection conn =
	      DriverManager.getConnection("jdbc:sqlite:asserts/databases/database.db");
	    Statement stat = conn.createStatement();
//	    stat.executeUpdate("drop table if exists people;");
//	    stat.executeUpdate("create table people (name, occupation);");
//	    PreparedStatement prep = conn.prepareStatement(
//	      "insert into people values (?, ?);");
//
//	    prep.setString(1, "Gandhi");
//	    prep.setString(2, "politics");
//	    prep.addBatch();
//	    prep.setString(1, "Turing");
//	    prep.setString(2, "computers");
//	    prep.addBatch();
//	    prep.setString(1, "Wittgenstein");
//	    prep.setString(2, "smartypants");
//	    prep.addBatch();
//
//	    conn.setAutoCommit(false);
//	    prep.executeBatch();
//	    conn.setAutoCommit(true);

	    ResultSet rs = stat.executeQuery("select * from T_FactoryPosition;");
	    while (rs.next()) {
	      System.out.println("Video = " + rs.getString("Video"));
	      System.out.println("Cover = " + rs.getString("Cover"));
	    }
	    rs.close();
	    conn.close();
	}
}
