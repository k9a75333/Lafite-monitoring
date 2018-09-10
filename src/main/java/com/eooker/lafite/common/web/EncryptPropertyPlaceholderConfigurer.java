package com.eooker.lafite.common.web;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.eooker.lafite.common.utils.DESUtils;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer  
{  
	public EncryptPropertyPlaceholderConfigurer(){
		
	}
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};  
    private String username;
    private String password;
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override  
    protected String convertProperty(String propertyName, String propertyValue)  
    {  
          
        //如果在加密属性名单中发现该属性  
        if (isEncryptProp(propertyName))  
        {   //System.out.println("-------------"+propertyName);
            String decryptValue = DESUtils.getDecryptString(propertyValue);  
            if(propertyName.equals("jdbc.username")) username = decryptValue;
            if(propertyName.equals("jdbc.password")) password = decryptValue;
            return decryptValue;  
        }else {  
            return propertyValue;  
        }  
          
    }  
      
    private boolean isEncryptProp(String propertyName)  
    {  
        for (String encryptName : encryptPropNames)  
        {  
            if (encryptName.equals(propertyName))  
            {  
                return true;  
            }  
        }  
        return false;  
    }  
}  