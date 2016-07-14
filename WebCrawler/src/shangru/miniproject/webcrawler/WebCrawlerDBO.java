package shangru.miniproject.webcrawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WebCrawlerDBO {

	public Connection conn = null;

	public WebCrawlerDBO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/Crawler?useSSL=false";
			conn = DriverManager.getConnection(url, "root", "910719Huangsr");
			System.out.println("Connection Established!");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet getRecords(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeQuery(sql);
	}

	public boolean runSQL(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.execute(sql);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}
