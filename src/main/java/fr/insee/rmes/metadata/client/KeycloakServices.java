package fr.insee.rmes.metadata.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import fr.insee.rmes.metadata.exceptions.ExceptionColecticaUnreachable;
//import fr.insee.crabe.utils.ProprietesCrabe;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
public class KeycloakServices {
	
	private static final Logger logger = LogManager.getLogger(KeycloakServices.class);

//    @NonNull
//    private ProprietesCrabe proprietes;

	@Value("${fr.insee.rmes.metadata.keycloak.secret}")
    String secret;
	
	@Value("${fr.insee.rmes.metadata.keycloak.resource}")
    String resource;
	
	@Value("${fr.insee.rmes.metadata.keycloak.server}")
    String server;
	
	@Value("${fr.insee.rmes.metadata.keycloak.realm}")
    String realm;

//    @PostConstruct
//    private void init() {
//        secret = proprietes.getSecretKeycloakNautile();
//        resource = proprietes.getResourceKeycloakNautile();
//        server = proprietes.getServerKeycloak();
//        realm = proprietes.getRealmKeycloakNautile();
//    }

    /**
     * Permet de récuperer un jeton keycloak
     * @return jeton
     * @throws ExceptionNautileInjoingnable
     */
    public String getKeycloakAccessToken() throws ExceptionColecticaUnreachable {

        String authString = resource + ":" + secret;

        byte[] authBytes = authString.getBytes(StandardCharsets.UTF_8);
        String encodedAuthString = Base64.getEncoder().encodeToString(authBytes);

        RestTemplate keycloakClient = new RestTemplate();
        String keycloakUrl = server + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedAuthString);

        String request = "grant_type=client_credentials";

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);
        try {
            String result = keycloakClient.postForObject(keycloakUrl, entity, String.class);

            String accessToken =
                (String) result.split(",")[0].split(":")[1]
                    .subSequence(1, result.split(",")[0].split(":")[1].length() - 1);

            logger.info("Retrieve Keycloak token");

            return accessToken;
        }
        catch (RestClientException e) {
            throw new ExceptionColecticaUnreachable("Le serveur Keycloak est injoignable");
        }

    }

    /**
     * Verifie si le jeton keycloak a expiré
     * @param token
     * @return boolean
     */
    public boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        boolean isValid = false;
        Date now = new Date();
        try {
            DecodedJWT jwt = JWT.decode(token);
            if (jwt.getExpiresAt().after(now)) {
                logger.info("Token is valid");
                isValid = true;
            }
        }
        catch (JWTDecodeException exception) {
            System.out.println("erreur" + exception.toString());

        }
        return isValid;
    }
}
