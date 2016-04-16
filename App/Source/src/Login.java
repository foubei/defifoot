import org.json.simple.JSONObject;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Login to the website http://www.defifoot.com/
 */
public class Login {
	private static String pseudo, motDePasse;
	
	@SuppressWarnings("static-access")
	public Login(String pseudo, String motDePasse) {
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject main() {
		WebClient webClient = new WebClient();
		JSONObject result = new JSONObject(); // Contains status of login process
	    try {
	    	webClient.getOptions().setJavaScriptEnabled(false);
	        HtmlPage page = (HtmlPage) webClient.getPage(MainFrame.prop.getProperty("url.main_site"));
	        HtmlForm form = page.getFormByName("defifoot_jeu_connexion");
	        form.getInputByName("pseudo").setValueAttribute(pseudo); 
	        HtmlInput passWordInput = form.getInputByName("password");
	        passWordInput.removeAttribute("disabled");
	        passWordInput.setValueAttribute(motDePasse); 
	        DomElement submitBtn = form.getFirstElementChild().getNextElementSibling().getNextElementSibling().getNextElementSibling();
	        page = submitBtn.click();
	        result.put("Error", page.asText().contains("Login ou mot de passe incorrect"));
	        result.put("ErrorMsg", page.asText().contains("Login ou mot de passe incorrect") ? "Login ou mot de passe incorrect" : "");
	    } catch (Exception e) {
	    	result.put("Error", true);
	        result.put("ErrorMsg", "Echec de connexion, veuillez réessayer plus tard.");
	    } finally {
	        webClient.close();
	    }
	    return result;
	}
}