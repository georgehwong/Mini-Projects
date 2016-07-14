package shangru.miniproject.webcrawler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {

	public static WebCrawlerDBO crawlerDBO = new WebCrawlerDBO();

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		crawlerDBO.runSQL("TRUNCATE Record;");
		processPage("http://www.mit.edu");
	}

	private static void processPage(String URL) throws SQLException, IOException {
		// TODO Auto-generated method stub
		// Check if the given URL is already in database
		String sql = "SELECT * FROM Record WHERE URL = '" + URL + "'";
		ResultSet rs = crawlerDBO.getRecords(sql);
		
		if (rs.next()) {
		} else {
			// store the URL to database to avoid parsing again
			sql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?);";
			PreparedStatement stmt = crawlerDBO.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URL);
			stmt.execute();

			// Get useful information
			Document doc = Jsoup.connect("http://www.mit.edu/").get();

			if (doc.text().contains("admissions")) {
				System.out.println(URL);
			}

			// Get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for (Element link : questions) {
				if (link.attr("href").contains("mit.edu"))
					processPage(link.attr("abs:href"));
			}
		}
	}

}
