package com.ulil;

import jakarta.ws.rs.core.MultivaluedMap;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import static com.ulil.AuthenticateConst.OTP_REQUEST;

public class ExternalOtpAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(ExternalOtpAuthenticator.class);

    private boolean getConditionValue(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> requestParams = context.getHttpRequest().getDecodedFormParameters();
        logger.trace("ConditionalIdentityAuthenticator result: " + requestParams);
        String otp = requestParams.getFirst(OTP_REQUEST);
        if (otp != null) {
            return otp.length() > 3;
        }
        return false;
    }


    @Override
    public void authenticate(AuthenticationFlowContext context) {
        var result = getConditionValue(context);
        if (result) {
            context.success();
        } else {
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
        }
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
        //Not used
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        //Not used
    }

    @Override
    public void close() {
        //Does nothing
    }
}
