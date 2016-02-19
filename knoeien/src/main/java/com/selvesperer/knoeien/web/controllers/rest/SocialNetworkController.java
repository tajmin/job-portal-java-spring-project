package com.selvesperer.knoeien.web.controllers.rest;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.scribe.builder.ServiceBuilder;
//import org.scribe.builder.api.FacebookApi;
//import org.scribe.model.OAuthRequest;
//import org.scribe.model.Response;
//import org.scribe.model.Token;
//import org.scribe.model.Verb;
//import org.scribe.model.Verifier;
//import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping(value = "/api/v1/socialNetwork")
public class SocialNetworkController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -5529379980137718693L;

	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.5/me";

	// https://graph.facebook.com/v2.5/oauth/access_token
	private static final Logger log = (Logger) LoggerFactory.getLogger(SocialNetworkController.class);
	private static final Token EMPTY_TOKEN = null;
	private final String apiKey = "196906993999156";
	private final String apiSecret = "01f33e69a387ae68e16edb89016b1540";
	private final String secretState = "secret" + new Random().nextInt(999_999);
	private final String applicationHost = "http://localhost:8080/knoeien/api/v1/socialNetwork";
	private final String STATE = "state";
	private ObjectMapper objectMapper;

	private final OAuth20Service service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).state(secretState)
			.callback(applicationHost + "/auth/facebook/callback").build(FacebookApi.instance());
	private final String authorizationUrl = service.getAuthorizationUrl();

	@RequestMapping(value = "/auth/facebook", method = RequestMethod.GET)
	public ResponseEntity<Object> facebookLogin(HttpServletResponse response)
			throws IOException, Exception {
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			//String state = UUID.randomUUID().toString();
			//session.setAttribute(STATE, state);

			URI fbUri = new URI(authorizationUrl);
			httpHeaders.setLocation(fbUri);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		httpHeaders.setLocation(new URI("/"));
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@RequestMapping(value = "/auth/facebook/callback", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> callback(@RequestParam("code") String code, HttpServletResponse servletResponse) throws Exception{
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			
			Verifier verifier = new Verifier(code);

			final Token accessToken = service.getAccessToken(verifier);
			System.out.println("Access Token");
			System.out.println(accessToken);
			System.out.println();

			// WORKING
			System.out.println("Now we're going to access a protected resource...");
			// OAuthRequest request = new OAuthRequest(Verb.GET,
			// PROTECTED_RESOURCE_URL);
			final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
			service.signRequest(accessToken, request);
			final Response response = request.send();

			System.out.println("Got it...!");
			System.out.println();
			System.out.println(response.getCode());
			System.out.println(response.getBody());
			String responseBody = response.getBody();
			JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);

			String facebookName = json.getString("name");
			String facebookId = json.getString("id");

			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			User fbUser = userService.facebookLogin(facebookName, facebookId);

			HashMap<String, String> uData = new HashMap<>();
			uData.put(Constants.CURRENT_USER_ID, fbUser.getEmail());
			uData.put(Constants.CURRENT_USER_NAME, fbUser.getFullName());
			
			URI appUri = new URI("/knoeien/home.xhtml?login=fb");
			httpHeaders.setLocation(appUri);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//			return new ResponseEntity<RestResponse>(
//					convertToRestGoodResponse(uData, LocalizationUtil.findLocalizedString("signupsuccess.text")),
//					HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		//return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null), HttpStatus.BAD_REQUEST);
		httpHeaders.setLocation(new URI("/"));
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

}