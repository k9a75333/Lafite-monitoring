package com.eooker.lafite.modules.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.eooker.lafite.common.utils.PwdEncryptionUtils;

public class CybHashedCredentialsMatcher extends SimpleCredentialsMatcher {
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		PwdEncryptionUtils utils=new PwdEncryptionUtils();

		String tokenCredentials=utils.encryp(String.valueOf(token.getPassword()));
		System.err.println("token"+tokenCredentials);
		String accountCredentials=String.valueOf(getCredentials(info));
		System.err.println("account"+accountCredentials);
		if (tokenCredentials.equals(accountCredentials)) {
			return true;
		}
		return false;
	}
	
}