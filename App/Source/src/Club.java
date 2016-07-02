import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * The club' data.
 */
public class Club {
	public static String[] playersLinks = new String[35]; // Take the players' links to get the players' data.
	
	/**
	 * Get my own club's data
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject getMine() {
		JSONObject result = new JSONObject();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		result.put("vrai_id", Integer.parseInt(MainFrame.prop.getProperty("id.club")));
		// 1. Get the club's data from http://defifoot.com/equipe/football/club_id
		try {
			HtmlPage currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.club") + MainFrame.prop.getProperty("id.club") + "/");
			String pageSource = currentPage.asXml();
			Document document = Jsoup.parse(pageSource);
			Element content = document.getElementById("jeu_contenu_container");
			Element clubName = content.getElementsByAttributeValue("href", "#tabs-0").first();
			result.put("nom", clubName.html());
			Filter.getMyClubDataProgressBar.setValue(9);
			Element otherData = content.getElementById("tabs-0").getElementsByTag("tbody").first();
			Element managerName = otherData.child(6).child(2).child(0);
			result.put("manager", managerName.html());
			Filter.getMyClubDataProgressBar.setValue(18);
		} catch (IOException e) {
			result.put("Error", true);
	        result.put("ErrorMsg", "Echec de connexion");
	        return result;
		} finally {
			MainFrame.webClient.close();
		}
		
		// 2. Get the remaining data from http://defifoot.com/modules/equipe/effectif.php
        try {
			HtmlPage currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players"));
			String pageSource = currentPage.asXml();
			Document document = Jsoup.parse(pageSource);
			Element content = document.getElementById("jeu_contenu_container");
			String[] playersNumber = content.getElementsByAttributeValue("href", "#tabs-0").first().html().split(" ");
			result.put("joueurs", Integer.parseInt(playersNumber[0]));
			Filter.getMyClubDataProgressBar.setValue(27);
			playersLinks = new String[Integer.parseInt(result.get("joueurs").toString())];
			Element players = content.getElementById("tabs-0");
			Element playersPro = players.getElementsByClass("tableau_listing").first().getElementsByTag("tbody").first();
			Element playersAcademy = null;
			if(players.getElementsByClass("tableau_listing").size() == 2) { // Club has also players from academy
				playersAcademy = players.getElementsByClass("tableau_listing").last().getElementsByTag("tbody").first();
				result.put("joueurs_centre", playersAcademy.getElementsByClass("ligne_joueurs_highlight").size() + playersAcademy.getElementsByClass("ligne_joueurs_downlight").size());
			} else { // Club has no players from academy
				result.put("joueurs_centre", 0);
			}
			Filter.getMyClubDataProgressBar.setValue(36);
			result.put("joueurs_pro", playersPro.getElementsByClass("ligne_joueurs_highlight").size() + playersPro.getElementsByClass("ligne_joueurs_downlight").size());
			Filter.getMyClubDataProgressBar.setValue(45);
			for(int i = 0; i < Integer.parseInt(result.get("joueurs_pro").toString()); i++) {
				playersLinks[i] = playersPro.child(i + 1).getElementsByAttribute("href").first().attr("href").toString();
			}
			if(Integer.parseInt(result.get("joueurs_pro").toString()) != 0) {  // Club has players pro
				result.put("age", df.format(Float.parseFloat(playersPro.getElementsByTag("tr").last().getElementsByTag("strong").get(1).html()) * Integer.parseInt(result.get("joueurs_pro").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(54);
				result.put("npa", df.format(Float.parseFloat(playersPro.getElementsByTag("tr").last().getElementsByTag("strong").get(2).html()) * Integer.parseInt(result.get("joueurs_pro").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(63);
				result.put("npv", df.format(Float.parseFloat(playersPro.getElementsByTag("tr").last().getElementsByTag("strong").get(3).html()) * Integer.parseInt(result.get("joueurs_pro").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(72);
				if(Integer.parseInt(result.get("joueurs_centre").toString()) != 0) { // Club has also players from academy
					result.put("age", df.format(Float.parseFloat(result.get("age").toString()) + (Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(1).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString()))));
					result.put("npa", df.format(Float.parseFloat(result.get("npa").toString()) + (Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(2).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString()))));
					result.put("npv", df.format(Float.parseFloat(result.get("npv").toString()) + (Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(3).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString()))));
					for(int i = 0; i < Integer.parseInt(result.get("joueurs_centre").toString()); i++) {
						playersLinks[i + Integer.parseInt(result.get("joueurs_pro").toString())] = playersAcademy.child(i + 1).getElementsByAttribute("href").first().attr("href").toString();
					}
				}
			} else { // Club has only players from academy
				result.put("age", df.format(Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(1).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(54);
				result.put("npa", df.format(Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(2).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(63);
				result.put("npv", df.format(Float.parseFloat(playersAcademy.getElementsByTag("tr").last().getElementsByTag("strong").get(3).html()) * Integer.parseInt(result.get("joueurs_centre").toString()) / Integer.parseInt(result.get("joueurs").toString())));
				Filter.getMyClubDataProgressBar.setValue(72);
				for(int i = 0; i < Integer.parseInt(result.get("joueurs_centre").toString()); i++) {
					playersLinks[i] = playersAcademy.child(i + 1).getElementsByAttribute("href").first().attr("href").toString();
				}
			}
			Element status = content.getElementById("tabs-1");
			result.put("experience", Float.parseFloat(status.getElementsByClass("tableau_listing").first().getElementsByTag("tr").last().getElementsByTag("strong").get(6).html()));
			Filter.getMyClubDataProgressBar.setValue(81);
			result.put("pression", Float.parseFloat(status.getElementsByClass("tableau_listing").first().getElementsByTag("tr").last().getElementsByTag("strong").get(7).html()));
			Filter.getMyClubDataProgressBar.setValue(90);
			result.put("flair", Float.parseFloat(status.getElementsByClass("tableau_listing").first().getElementsByTag("tr").last().getElementsByTag("strong").get(8).html()));
			Filter.getMyClubDataProgressBar.setValue(99);
			result.put("Error", false);
	        result.put("ErrorMsg", "");
		} catch (IOException e) {
			result.put("Error", true);
	        result.put("ErrorMsg", "Echec de connexion");
		} finally {
			MainFrame.webClient.close();
		}
        return result;
	}
}