package com.ulil;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.AuthenticationFlowException;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.Map;

public class ConditionalIdentityAuthenticator implements ConditionalAuthenticator{

    static final ConditionalIdentityAuthenticator SINGLETON = new ConditionalIdentityAuthenticator();

    private static final Logger logger = Logger.getLogger(ConditionalIdentityAuthenticator.class);

    @Override
    public boolean matchCondition(AuthenticationFlowContext context) {
        if (context == null){
            throw new AuthenticationFlowException("context is null", AuthenticationFlowError.INTERNAL_ERROR);
        }
        boolean negateOutput = false;

        var authenticatorConfig = context.getAuthenticatorConfig();
        if (authenticatorConfig != null){
            Map<String, String> config = authenticatorConfig.getConfig();
            if (config != null) {
                negateOutput = Boolean.parseBoolean(config.get(ConditionalIdentityAuthenticatorFactory.CONF_NOT));
            }
        }

        var result = getConditionValue(context);

        var fullResult = negateOutput != result;

        logger.trace("ConditionalIdentityAuthenticator result: " + fullResult);
        return fullResult;
    }

    private boolean getConditionValue(AuthenticationFlowContext context){
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        UserModel user = context.getUser();
        var session = context.getSession();

        var sessionIdentities = session.users().getFederatedIdentitiesStream(authSession.getRealm(), user);

        var identitiesCount = sessionIdentities.count();
        logger.trace("Identities count: "+ identitiesCount);

        sessionIdentities.close();

        return identitiesCount > 0;
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
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        //Not used
    }

    @Override
    public void close() {
        //Does nothing
    }
}
