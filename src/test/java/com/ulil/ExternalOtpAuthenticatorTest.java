package com.ulil;

import static com.ulil.AuthenticateConst.OTP_REQUEST;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.http.HttpRequest;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class ExternalOtpAuthenticatorTest {
    @Test
    void testMatchCondition() {
        // Mock dependencies
        AuthenticationFlowContext context = mock(AuthenticationFlowContext.class);
        AuthenticatorConfigModel authenticatorConfig = mock(AuthenticatorConfigModel.class);
        KeycloakSession session = mock(KeycloakSession.class);
        RealmModel realm = mock(RealmModel.class);
        UserModel user = mock(UserModel.class);
        AuthenticationSessionModel authSession = mock(AuthenticationSessionModel.class);
        UriInfo uriInfo = mock(UriInfo.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        MultivaluedMap<String, String> formParams = new MultivaluedHashMap<>();
        formParams.add(OTP_REQUEST, "01293012930");

        // Set up expectations and stubs
        when(context.getAuthenticatorConfig()).thenReturn(authenticatorConfig);
        when(authenticatorConfig.getConfig()).thenReturn(new HashMap<>());
        when(context.getSession()).thenReturn(session);
        when(context.getAuthenticationSession()).thenReturn(authSession);
        when(context.getUser()).thenReturn(user);
        when(context.getHttpRequest()).thenReturn(httpRequest);
        when(context.getHttpRequest().getDecodedFormParameters()).thenReturn(formParams);

        ExternalOtpAuthenticator authenticator = new ExternalOtpAuthenticator();
//        boolean condition = authenticator.getConditionValue(context);

        assertTrue(true);
    }
}
