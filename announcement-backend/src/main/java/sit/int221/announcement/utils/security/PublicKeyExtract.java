package sit.int221.announcement.utils.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

//Extract Public Key Ref
//https://medium.com/@maheshkalakshan/secure-user-authentication-validating-azure-msal-sso-tokens-in-java-e900d5295544
@AllArgsConstructor @NoArgsConstructor
public class PublicKeyExtract {

    private String jwkProviderUrl;
    private String token;

    public java.security.PublicKey getX509PublicKey() throws CertificateException, IOException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        PemObject pemObject;
        try (PemReader pemReader = new PemReader(new StringReader( getKey() ))) {
            pemObject = pemReader.readPemObject();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(pemObject.getContent());
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(inputStream);

        return certificate.getPublicKey();
    }
    public String getKey() {
        return  "-----BEGIN PUBLIC KEY-----\n"+ getOriginKey()+"\n-----END PUBLIC KEY-----";
    }
    public String getOriginKey() {
        try {
            // Decode the JWT token and extract the "kid" claim
            String kid = getTokenKeyId(token);

            // Get the public key from Azure AD OpenID configuration endpoint
            URL endpointUrl = new URL(this.jwkProviderUrl);
            String publicKeyResponse = getAzureADPublicKey(endpointUrl);

            // Extract the value of x5c for the matching key ID
            return extractX5CValue(publicKeyResponse, kid);
        } catch (Exception e) {
            // Handle any exceptions here
            return null;
        }
    }
    private String getTokenKeyId(String token) throws Exception {
        // Decode the JWT token and extract the "kid" claim
        String[] parts = token.split("\\.");
        String header = new String(Base64.getUrlDecoder().decode(parts[0]));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(header);
        return jsonNode.get("kid").asText();
    }
    private String extractX5CValue(String publicKeyResponse, String kid) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(publicKeyResponse);
        JsonNode keysNode = jsonNode.get("keys");
        if (!keysNode.isArray()) throw new RuntimeException("keysNode is not array");

        for (JsonNode keyNode : keysNode) {
            String keyId = keyNode.get("kid").asText();
            if (!keyId.equals(kid)) continue;
            JsonNode x5cNode = keyNode.get("x5c");
            if (x5cNode.isArray() && x5cNode.size() == 1) return x5cNode.get(0).asText();
            else throw new RuntimeException("Invalid x5c value in the Azure AD public key response.");
        }

        throw new RuntimeException("Matching key ID not found in the Azure AD public key response.");
    }
    //GET AZURE AD FROM EndpointUrl
    private String getAzureADPublicKey(URL endpointUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
        connection.setRequestMethod(HttpMethod.GET.toString());
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) throw new RuntimeException("Failed to fetch Azure AD public key. Response code: " + responseCode);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) response.append(line);

        reader.close();
        connection.disconnect();
        return response.toString();
    }
}
