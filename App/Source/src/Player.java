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
 * The player's data.
 */
public class Player {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static JSONObject get(String link) { // Get the player's data
		JSONObject result = new JSONObject();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		result.put("lien", link);
		String[] str = link.split("/");
		result.put("vrai_id", Integer.parseInt(str[str.length - 1]));
		
		// 1. Get the player's data from his link
        try {
			HtmlPage currentPage = (HtmlPage) MainFrame.webClient.getPage(link);
			String pageSource = currentPage.asXml();
			Document document = Jsoup.parse(pageSource);
			String fullName = document.getElementById("jeu_contenu_container").siblingElements().first().child(1).getElementsByTag("tr").first().getElementsByTag("td").first().html().replaceAll("Joueur ", "").substring(0, document.getElementById("jeu_contenu_container").siblingElements().first().child(1).getElementsByTag("tr").first().getElementsByTag("td").first().html().replaceAll("Joueur ", "").indexOf("&nbsp;&nbsp;"));
			String lastName = document.getElementsByAttributeValue("href", "#tabs-0").html().toString().replaceAll("Identité de ", "").substring(document.getElementsByAttributeValue("href", "#tabs-0").html().toString().replaceAll("Identité de ", "").indexOf(".") + 2).toLowerCase();
			result.put("prenom", fullName.substring(0, fullName.toLowerCase().indexOf(lastName) - 1));
			result.put("nom", fullName.substring(fullName.toLowerCase().indexOf(lastName)));
			Element content = document.getElementById("jeu_contenu_container");
			Element playerIdentity = content.getElementById("tabs-0");
			result.put("numero", Integer.parseInt(playerIdentity.getElementsByClass("avatar_joueur").first().getElementsByAttributeValue("alt", "Numéro de maillot").first().attr("src").replace("http://www-defifoot-com.so-static.net/images/pictos/joueur/numeros/", "").replace(".png", "")));
			result.put("avatar", playerIdentity.getElementsByClass("avatar_joueur").first().getElementsByTag("img").last().attr("src"));
			str = playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(1).getElementsByClass("linfo_joueur").first().html().replace(" ans et", "").replace(" jours", "").split(" ");
			result.put("age", df.format(Float.parseFloat(str[0]) + Float.parseFloat(str[1])/365));
			result.put("position_actuelle", playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(2).getElementsByClass("titre_info").first().html().replace("Niveau en ", "").replace("&nbsp;:&nbsp;", ""));
			str = playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(2).getElementsByTag("script").html().replace("//<![CDATA[", "").replace("//]]>", "").replaceAll("\\n", "").replaceAll("\\r", "").split("ajouterProgressBar");
			result.put("npa", Float.parseFloat(str[1].replace("('pb_npa',", "").replace(",'NPA',20,200);", "")));
			result.put("npv", Float.parseFloat(str[2].replace("('pb_npv',", "").replace(",'NPV',20,200);", "")));
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://" + MainFrame.prop.getProperty("mysql.host") + ":" + MainFrame.prop.getProperty("mysql.port") + "/" + MainFrame.prop.getProperty("mysql.database"), MainFrame.prop.getProperty("mysql.username"), MainFrame.prop.getProperty("mysql.password"));
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet = statement.executeQuery("SELECT id FROM equipe WHERE nom = '" + 
						playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(3).getElementsByClass("linfo_joueur").first().getElementsByTag("a").last().html().toString() + 
				"'");
				resultSet.afterLast();
		        GETLASTINSERTED:
		        while(resultSet.previous()){
		        	result.put("equipe_id", Integer.parseInt(resultSet.getString("id")));
		            break GETLASTINSERTED;
		        }
				connection.close();
			} catch (SQLException e) {
				result.put("Error", true);
		        result.put("ErrorMsg", "Echec du MySQL");
		        return result;
			}
			result.put("valeur", playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(5).getElementsByClass("linfo_joueur").first().html());
			if(playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(6).getElementsByClass("linfo_joueur").first().html().contains("Ce joueur n'intéresse personne actuellement")) {
				result.put("cote", 0);
			} else {
				str = playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(6).getElementsByClass("linfo_joueur").first().getElementsByAttributeValueContaining("style", "background-color: #dcd598").html().split("/");
				result.put("cote", df.format(Float.parseFloat(str[0]) / Float.parseFloat(str[1])));
			}
			result.put("salaire", playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(7).getElementsByClass("linfo_joueur").first().html());
			if(!playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(8).getElementsByAttributeValue("href", "http://defifoot.com/modules/equipe/effectif.php?dataview=statuts").first().html().equalsIgnoreCase("Pas de statut")) {
				result.put("statut", playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(8).getElementsByAttributeValue("href", "http://defifoot.com/modules/equipe/effectif.php?dataview=statuts").first().html());
			}
			result.put("capitaine", df.format(Float.parseFloat(playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").get(8).getElementsByTag("script").html().replace("//<![CDATA[", "").replace("//]]>", "").replaceAll("\\n", "").replaceAll("\\r", "").replace("ajouterProgressBar('pb_capitaine',", "").replace(",'Capitaine',20,200);", "")) / 100));
			if(playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").size() == 10) {
				result.put("position_origine", playerIdentity.getElementById("infos_joueurs").getElementsByTag("tr").last().getElementsByTag("td").html().replace("Tu as positionné ce joueur comme " + result.get("position_actuelle") + ". N'oublie pas qu'il a plutôt un potentiel pour devenir bien meilleur en tant que ", "").replace(". <br> <a href=\"http://defifoot.com/modules/equipe/effectif.php?dataview=npa\"> Comparer le niveau de mes joueurs pour chacune des positions </a>", ""));
			} else {
				result.put("position_origine", result.get("position_actuelle"));
			}
			Element playerProgress = content.getElementsByClass("soustitre").first();
			result.put("progression_potentiel", playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(0).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/etoile-or.gif").size() + (float) (playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(0).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/demi-etoile-or.gif").size()) / 2);
			result.put("progression_vitesse", playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(1).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/etoile-or.gif").size() + (float) (playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(1).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/demi-etoile-or.gif").size()) / 2);
			result.put("progression_rigueur", playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(2).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/etoile-or.gif").size() + (float) (playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(2).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/demi-etoile-or.gif").size()) / 2);
			result.put("progression_rigularite", playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(3).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/etoile-or.gif").size() + (float) (playerProgress.getElementsByAttributeValue("style", "position:relative;width:200px;top:-16px;left:80px").get(3).getElementsByAttributeValue("src", "http://www-defifoot-com.so-static.net/images/pictos/progression/demi-etoile-or.gif").size()) / 2);
			str = content.getElementsByTag("script").get(9).toString().substring(content.getElementsByTag("script").get(9).toString().lastIndexOf("data: ["), content.getElementsByTag("script").get(9).toString().lastIndexOf("},]")).replace("data: [", "").replace("]", "").replaceAll("\\n", "").replaceAll("\\r", "").replaceAll(" ", "").split(",");
			int i = 0;
			if(result.get("position_origine").toString().equals("GB")) {
				result.put("reflexe", Integer.parseInt(str[0]));
				result.put("prise_balle", Integer.parseInt(str[1]));
				result.put("detente", Integer.parseInt(str[2]));
				i = 3;
			} else {
				result.put("tete", Integer.parseInt(str[0]));
				result.put("tacle", Integer.parseInt(str[1]));
				i = 2;
			}
			result.put("passe", Integer.parseInt(str[i]));
			result.put("tir", Integer.parseInt(str[i+1]));
			result.put("dribble", Integer.parseInt(str[i+2]));
			result.put("controle", Integer.parseInt(str[i+3]));
			result.put("vitesse", Integer.parseInt(str[i+4]));
			result.put("endurance", Integer.parseInt(str[i+5]));
			result.put("physique", Integer.parseInt(str[i+6]));
			result.put("forme", Integer.parseInt(str[i+7]));
			result.put("flair", Integer.parseInt(str[i+8]));
			result.put("pression", Integer.parseInt(str[i+9]));
			result.put("mental", Integer.parseInt(str[i+10]));
			result.put("experience", Integer.parseInt(str[i+11]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(0).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(0).toString().indexOf("'pb_progres_0',") + 15).split(",");
			result.put("niveau_tete", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(1).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(1).toString().indexOf("'pb_progres_1',") + 15).split(",");
			result.put("niveau_frappe", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(2).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(2).toString().indexOf("'pb_progres_2',") + 15).split(",");
			result.put("niveau_controle", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(3).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(3).toString().indexOf("'pb_progres_3',") + 15).split(",");
			result.put("niveau_dribble", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(4).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(4).toString().indexOf("'pb_progres_4',") + 15).split(",");
			result.put("niveau_recuperation", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(5).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(5).toString().indexOf("'pb_progres_5',") + 15).split(",");
			result.put("niveau_passe", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(6).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(6).toString().indexOf("'pb_progres_6',") + 15).split(",");
			result.put("niveau_endurance", Integer.parseInt(str[0]));
			str = content.getElementsByClass("soustitre").last().getElementsByTag("script").get(7).toString().substring(content.getElementsByClass("soustitre").last().getElementsByTag("script").get(7).toString().indexOf("'pb_progres_7',") + 15).split(",");
			result.put("niveau_gardien", Integer.parseInt(str[0]));
			str = link.split("/");
			
			// 2. Get the player's club history
			currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.player_clubs") + "?joueur=" + str[str.length - 1]);
			pageSource = currentPage.asXml();
			document = Jsoup.parse(pageSource);
			for(i = 0; i < document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").size(); i++) {
				if(document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(MainFrame.prop.getProperty("url.club") + MainFrame.prop.getProperty("id.club") + "/") && document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains("Achat")) {
					result.put("condition_arrivee", document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(1).html().replace(" <br> ", ", "));
					str = document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(2).html().split("/");
					result.put("date_arrivee", new Date(Integer.parseInt(str[2]) - 1900, Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
					break;
				}
				if(document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(MainFrame.prop.getProperty("url.club") + MainFrame.prop.getProperty("id.club") + "/") && document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains("<td valign=\"top\"> Prêt </td>")) {
					result.put("condition_arrivee", document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(1).html());
					str = document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(2).html().split("/");
					result.put("date_arrivee", new Date(Integer.parseInt(str[2]) - 1900, Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
					break;
				}
				if(document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(MainFrame.prop.getProperty("url.club") + MainFrame.prop.getProperty("id.club") + "/") && document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains("Centre de formation")) {
					result.put("condition_arrivee", document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(1).html());
					str = document.getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(2).html().split("/");
					result.put("date_arrivee", new Date(Integer.parseInt(str[2]) - 1900, Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
					break;
				}
			}
			
			// 3. Get the player's npa for each position
			currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players") + "?dataview=" + MainFrame.prop.getProperty("dataView.npa"));
			pageSource = currentPage.asXml();
			document = Jsoup.parse(pageSource);
			if(Float.parseFloat(result.get("age").toString()) < 21) {
				for(i = 0; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").size(); i++) {
					if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
						result.put("npa_gb", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(7).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_df", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(8).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_lb", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(9).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_md", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(10).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_mt", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(11).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_mo", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(12).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_at", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(13).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_bc", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(14).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						break;
					}
				}
			} else {
				for(i = 0; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").size(); i++) {
					if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
						result.put("npa_gb", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(7).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_df", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(8).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_lb", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(9).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_md", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(10).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_mt", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(11).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_mo", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(12).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_at", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(13).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						result.put("npa_bc", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(14).html().replace("<span style=\"font-weight: bold; color: #020598;\">", "").replace("</span>", "")));
						break;
					}
				}
			}
			
			// 4. Get the player's psychological quality
			currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players") + "?dataview=" + MainFrame.prop.getProperty("dataView.niv"));
			pageSource = currentPage.asXml();
			document = Jsoup.parse(pageSource);
			if(Float.parseFloat(result.get("age").toString()) < 21) {
				for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").size(); i++) {
					if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
						result.put("qualite_psychologique", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(15).html().replace("<span>", "").replace("</span>", "")));
						break;
					}
				}
			} else {
				for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").size(); i++) {
					if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
						result.put("qualite_psychologique", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(15).html().replace("<span>", "").replace("</span>", "")));
						break;
					}
				}
			}
			
			// 5. Get the missing data for the player
			if(!result.get("position_origine").toString().equals("GB")) {
				currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players") + "?dataview=" + MainFrame.prop.getProperty("dataView.gb"));
				pageSource = currentPage.asXml();
				document = Jsoup.parse(pageSource);
				if(Float.parseFloat(result.get("age").toString()) < 21) {
					for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").size(); i++) {
						if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
							result.put("reflexe", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(8).html().replace("<span>", "").replace("</span>", "")));
							result.put("prise_balle", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(9).html().replace("<span>", "").replace("</span>", "")));
							result.put("detente", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(10).html().replace("<span>", "").replace("</span>", "")));
							break;
						}
					}
				} else {
					for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").size(); i++) {
						if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
							result.put("reflexe", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(8).html().replace("<span>", "").replace("</span>", "")));
							result.put("prise_balle", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(9).html().replace("<span>", "").replace("</span>", "")));
							result.put("detente", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(10).html().replace("<span>", "").replace("</span>", "")));
							break;
						}
					}
				}
			} else {
				currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players") + "?dataview=" + MainFrame.prop.getProperty("dataView.df"));
				pageSource = currentPage.asXml();
				document = Jsoup.parse(pageSource);
				if(Float.parseFloat(result.get("age").toString()) < 21) {
					for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").size(); i++) {
						if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
							result.put("tete", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(9).html().replace("<span>", "").replace("</span>", "")));
							result.put("tacle", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").last().getElementsByTag("tr").get(i).child(10).html().replace("<span>", "").replace("</span>", "")));
							break;
						}
					}
				} else {
					for(i = 1; i < document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").size(); i++) {
						if(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).html().contains(result.get("lien").toString())) {
							result.put("tete", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(9).html().replace("<span>", "").replace("</span>", "")));
							result.put("tacle", Float.parseFloat(document.getElementById("tabs-0").getElementsByClass("tableau_listing").first().getElementsByTag("tr").get(i).child(10).html().replace("<span>", "").replace("</span>", "")));
							break;
						}
					}
				}
			}
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