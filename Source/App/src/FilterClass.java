import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FilterClass {
	
	private static String pseudo;
	private static String motDePasse;
	private static String[] attr = {"N", "Joueur", "Pos", "Age", "NPA", "NPV", "Tet", "Fra", "Ctr", "Dri", "Rec", "Pas", "Gar", "End", "Psy"};
	private static JSONArray playersList = new JSONArray();
	
	public FilterClass(String pseudo, String motDePasse) {
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}
	
	boolean getPlayers(String url) {
		 try {
			WebClient webClient = login(new WebClient());
			HtmlPage currentPage = webClient.getPage(url);
			String pageSource = currentPage.asXml();
			Document document = Jsoup.parse(pageSource);
			Elements players = document.getElementsByClass("tableau_listing");
			ExtractFundamentalPlayers(players.get(1).getElementsByClass("ligne_joueurs_highlight"));
			ExtractReservePlayers(players.get(1).getElementsByClass("ligne_joueurs_highlight"));
			if(players.size() == 4) { // Young players exists
				ExtractFundamentalPlayers(players.get(2).getElementsByClass("ligne_joueurs_highlight"));
				ExtractReservePlayers(players.get(2).getElementsByClass("ligne_joueurs_highlight"));
			}
			System.out.println(playersList.toJSONString());
			System.out.println(playersList.size());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static void ExtractFundamentalPlayers(Elements list) { // Get fundamental players
		for(int i = 0; i < list.size() ; i++) {
			Element item = list.get(i);
			Elements subItems = item.getElementsByTag("td");
			JSONObject player = new JSONObject();
			for(int j = 0; j < subItems.size() - 1; j++) {
				Elements children = subItems.get(j).getElementsByTag("span");
				switch(j) {
					case 0:
						player.put(attr[0], subItems.get(0).html());
						break;
					case 1:
						Elements child = children.get(0).getElementsByTag("a");
						String[] parts = child.attr("title").split(" ");
						player.put("ID", parts[0].substring(0, parts[0].length() - 1));
						player.put(attr[1], child.html());
						player.put("Prenom", parts[1]);
						player.put("Nom", parts[2]);
						player.put("Lien", child.attr("href"));
						break;
					default:
						player.put(attr[j], children.get(children.size() - 1).html());
						break;
				}
			}
			playersList.add(player);
		}
	}
	
	private static void ExtractReservePlayers(Elements list) { // Get reserve players
		for(int i = 0; i < list.size() ; i++) {
			Element item = list.get(i);
			Elements subItems = item.getElementsByTag("td");
			JSONObject player = new JSONObject();
			for(int j = 0; j < subItems.size() - 1; j++) {
				switch(j) {
					case 1:
						Elements child = subItems.get(1).getElementsByTag("a");
						String[] parts = child.attr("title").split(" ");
						player.put("ID", parts[0].substring(0, parts[0].length() - 1));
						player.put(attr[1], child.html());
						player.put("Prenom", parts[1]);
						player.put("Nom", parts[2]);
						player.put("Lien", child.attr("href"));
						break;
					default:
						Elements children = subItems.get(j).getElementsByTag("span");
						if(children.size() != 0) {
							player.put(attr[j], children.html());
						} else {
							player.put(attr[j], subItems.get(j).html());
						}
						break;
				}
			}
			playersList.add(player);
		}
	}
	
	private static WebClient login(WebClient webClient) throws Exception {
	    webClient.getOptions().setJavaScriptEnabled(false);
	    HtmlPage currentPage = webClient.getPage("http://www.defifoot.com/");
	    HtmlInput username = currentPage.getElementByName("pseudo");
	    username.setValueAttribute(pseudo);
	    HtmlInput password = currentPage.getElementByName("password");
	    password.setValueAttribute(motDePasse);
	    DomElement form = currentPage.getElementByName("defifoot_jeu_connexion");
	    DomElement submitBtn = form.getFirstElementChild().getNextElementSibling().getNextElementSibling().getNextElementSibling();
	    currentPage = submitBtn.click();
	    return webClient;
	}
}