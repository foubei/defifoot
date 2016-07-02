import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * The training' data.
 */
public class Training {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static JSONObject get(String link) { // Get the training's data
		JSONObject result = new JSONObject();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String[] str = link.split("/");
		result.put("joueur_id", Integer.parseInt(str[str.length - 1]));
        try {
        	HtmlPage currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.training") + "?joueur=" + str[str.length - 1]);
			String pageSource = currentPage.asXml();
			Document document = Jsoup.parse(pageSource);
			Element content = document.getElementsByClass("tableau_listing").first();
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), MainFrame.prop.getProperty("mysql.username"), MainFrame.prop.getProperty("mysql.password"));
			Statement statement = connection.createStatement();
			for(int i = content.getElementsByTag("tr").size() - 3; i > 2; i--) {
				int j = 0;
				str = content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j).html().split("/");
				result.put("date", new Date(Integer.parseInt(str[2]) - 1900, Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
				ResultSet resultSet = statement.executeQuery("SELECT id FROM joueur WHERE vrai_id = " + 
						result.get("joueur_id") + 
				"");
				resultSet.afterLast();
		        GETLASTINSERTED:
		        while(resultSet.previous()){
		        	result.put("joueur_id", Integer.parseInt(resultSet.getString("id")));
		            break GETLASTINSERTED;
		        }
				
				resultSet = statement.executeQuery("SELECT id FROM entrainement WHERE joueur_id = " +
						result.get("joueur_id").toString() + 
						" AND date = '" +
						result.get("date") + "'");
				if(resultSet.next()) {
					continue;
				} else {
					result.put("passe", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+1).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+1).html()));
					result.put("dribble", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+2).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+2).html()));
					if(!content.getElementsByTag("tr").get(0).getElementsByTag("td").get(0).html().contains("GB")) {
						result.put("tir", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+3).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+3).html()));
						result.put("tete", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+4).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+4).html()));
						result.put("reflexe", 0);
						result.put("prise_balle", 0);
						result.put("detente", 0);
						j = j + 2;
					}
					result.put("tacle", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+3).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+3).html()));
					result.put("controle", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+4).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+4).html()));
					result.put("vitesse", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+5).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+5).html()));
					result.put("endurance", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+6).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+6).html()));
					if(content.getElementsByTag("tr").get(0).getElementsByTag("td").get(0).html().contains("GB")) {
						result.put("reflexe", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+7).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+7).html()));
						result.put("prise_balle", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+8).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+8).html()));
						result.put("detente", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+9).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+9).html()));
						result.put("tir", 0);
						result.put("tete", 0);
						j = j + 3;
					}
					result.put("physique", content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+7).html().contains("-") ? 0 : Float.parseFloat(content.getElementsByTag("tr").get(i).getElementsByTag("td").get(j+7).html()));
					String sql = "INSERT INTO entrainement "
			                   + "(joueur_id, reflexe, prise_balle, detente, tete, tacle, passe, tir, dribble, controle, vitesse, endurance, physique, date)" 
			                   + "VALUES (" + result.get("joueur_id") + ", " + result.get("reflexe").toString() + ", " + result.get("prise_balle").toString() + ", " + result.get("detente") + ", " + result.get("tete") + ", " + result.get("tacle") + ", " + result.get("passe") + ", " + result.get("tir") + ", " + result.get("dribble") + ", " + result.get("controle") + ", " + result.get("vitesse") + ", " + result.get("endurance") + ", " + result.get("physique") + ", '" + result.get("date") + "')";
					statement.executeUpdate(sql);
				}
			}
			result.put("Error", false);
	        result.put("ErrorMsg", "");
	        connection.close();
		} catch (IOException e) {
			result.put("Error", true);
	        result.put("ErrorMsg", "Echec de connexion");
		} catch (SQLException e) {
			result.put("Error", true);
	        result.put("ErrorMsg", "Echec du MySQL");
		} finally {
			MainFrame.webClient.close();
		}
        return result;
	}
}