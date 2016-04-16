import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Get the club's data from the website.
 */
public class GetMyClubClass {
	public static void main() {
		// Connect to the database
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), MainFrame.prop.getProperty("mysql.username"), MainFrame.prop.getProperty("mysql.password"));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}