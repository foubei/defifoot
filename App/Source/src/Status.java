import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * The status' data.
 */
public class Status {
	@SuppressWarnings("unchecked")
	public static JSONObject get() { // Get the status' data
		JSONObject result = new JSONObject();
		for(int i = 0; i < 1; i++) {
			JSONObject data = new JSONObject();
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			// Get the players status' data from http://defifoot.com/modules/equipe/effectif.php
	        try {
	        	HtmlPage currentPage = (HtmlPage) MainFrame.webClient.getPage(MainFrame.prop.getProperty("url.players"));
				String pageSource = currentPage.asXml();
				Document document = Jsoup.parse(pageSource);
				Element content = document.getElementById("jeu_contenu_container");
				Element status = content.getElementById("tabs-1").getElementsByTag("tbody").first();
				Elements status_1 = status.getElementsByAttributeValue("style", "background-color:#FED798");
				for(int j = 0; j < status_1.size(); j++) {
					data.put("link", status_1.get(j).getElementsByAttribute("href").first().attr("href").toString());
					data.put("normal", true);
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(1).html().length()) {
						case 1:
							data.put("vestiaire", 1);
							break;
						case 148:
							data.put("vestiaire", 1);
							break;
						default:
							String[] value = status_1.get(j).getElementsByAttributeValue("align", "center").get(1).getElementsByAttribute("title").attr("title").replace(" jours", "").split("/");
							data.put("vestiaire", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(2).html().length()) {
						case 1:
							data.put("gloire", 1);
							break;
						case 148:
							data.put("gloire", 1);
							break;
						default:
							String[] value = status_1.get(j).getElementsByAttributeValue("align", "center").get(2).getElementsByAttribute("title").attr("title").replace(" titres", "").split("/");
							data.put("gloire", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(3).html().length()) {
						case 1:
							data.put("ame", 1);
							break;
						case 148:
							data.put("ame", 1);
							break;
							case 284:
								data.put("ame", 0);
								break;
						default:
							String[] value = status_1.get(j).getElementsByAttributeValue("align", "center").get(3).getElementsByAttribute("title").attr("title").replace(" jours", "").split("/");
							data.put("ame", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(4).html().length()) {
						case 148:
							data.put("vedette", true);
							break;
						case 246:
							data.put("vedette", false);
							break;
						default:
							data.put("vedette", false);
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(5).html().length()) {
						case 1:
							data.put("experience", 0);
							break;
						default:
							data.put("experience", df.format(Float.parseFloat(status_1.get(j).getElementsByAttributeValue("align", "center").get(5).html().replace("+", ""))));
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(6).html().length()) {
						case 1:
							data.put("pression", 0);
							break;
						default:
							data.put("pression", df.format(Float.parseFloat(status_1.get(j).getElementsByAttributeValue("align", "center").get(6).html().replace("+", ""))));
							break;
					}
					switch(status_1.get(j).getElementsByAttributeValue("align", "center").get(7).html().length()) {
						case 1:
							data.put("flair", 0);
							break;
						default:
							data.put("flair", df.format(Float.parseFloat(status_1.get(j).getElementsByAttributeValue("align", "center").get(7).html().replace("+", ""))));
							break;
					}
				}
				Elements status_2 = status.getElementsByAttributeValue("style", "background-color:#FECD7F");
				for(int j = 0; j < status_2.size(); j++) {
					data.put("link", status_2.get(j).getElementsByAttribute("href").first().attr("href").toString());
					data.put("normal", true);
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(1).html().length()) {
						case 1:
							data.put("vestiaire", 1);
							break;
						case 148:
							data.put("vestiaire", 1);
							break;
						default:
							String[] value = status_2.get(j).getElementsByAttributeValue("align", "center").get(1).getElementsByAttribute("title").attr("title").replace(" jours", "").split("/");
							data.put("vestiaire", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(2).html().length()) {
						case 1:
							data.put("gloire", 1);
							break;
						case 148:
							data.put("gloire", 1);
							break;
						default:
							String[] value = status_2.get(j).getElementsByAttributeValue("align", "center").get(2).getElementsByAttribute("title").attr("title").replace(" titres", "").split("/");
							data.put("gloire", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(3).html().length()) {
						case 1:
							data.put("ame", 1);
							break;
						case 148:
							data.put("ame", 1);
							break;
							case 284:
								data.put("ame", 0);
								break;
						default:
							String[] value = status_2.get(j).getElementsByAttributeValue("align", "center").get(3).getElementsByAttribute("title").attr("title").replace(" jours", "").split("/");
							data.put("ame", df.format(Float.parseFloat(value[0]) / Float.parseFloat(value[1])));
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(4).html().length()) {
						case 148:
							data.put("vedette", true);
							break;
						case 246:
							data.put("vedette", false);
							break;
						default:
							data.put("vedette", false);
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(5).html().length()) {
						case 1:
							data.put("experience", 0);
							break;
						default:
							data.put("experience", df.format(Float.parseFloat(status_2.get(j).getElementsByAttributeValue("align", "center").get(5).html().replace("+", ""))));
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(6).html().length()) {
						case 1:
							data.put("pression", 0);
							break;
						default:
							data.put("pression", df.format(Float.parseFloat(status_2.get(j).getElementsByAttributeValue("align", "center").get(6).html().replace("+", ""))));
							break;
					}
					switch(status_2.get(j).getElementsByAttributeValue("align", "center").get(7).html().length()) {
						case 1:
							data.put("flair", 0);
							break;
						default:
							data.put("flair", df.format(Float.parseFloat(status_2.get(j).getElementsByAttributeValue("align", "center").get(7).html().replace("+", ""))));
							break;
					}
				}
				result.put("Error", false);
			} catch (FailingHttpStatusCodeException | IOException e) {
				result.put("Error", true);
		        result.put("ErrorMsg", "Echec de connexion, veuillez réessayer plus tard.");
		        return result;
			}
		}
		return result;
	}
}
