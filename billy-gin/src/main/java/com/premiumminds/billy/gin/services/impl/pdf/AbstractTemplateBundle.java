package com.premiumminds.billy.gin.services.impl.pdf;

import java.io.InputStream;

import com.premiumminds.billy.gin.services.export.IBillyTemplateBundle;

public class AbstractTemplateBundle implements IBillyTemplateBundle{
	protected final String logoImagePath;
	protected final InputStream xsltFileStream;
	
	public AbstractTemplateBundle(String logoImagePath, InputStream xsltFileStream){
		
		this.logoImagePath = logoImagePath;
		this.xsltFileStream = xsltFileStream;
	}
	
	public String getLogoImagePath(){
		return this.logoImagePath;
	}

	public InputStream getXSLTFileStream(){
		return this.xsltFileStream;
	}
}
