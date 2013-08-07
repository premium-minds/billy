package com.premiumminds.billy.gin.services.impl.pdf;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.export.IBillyTemplateBundle;

public class AbstractExportRequest implements ExportServiceRequest{
	protected UID uid;
	private IBillyTemplateBundle bundle;
	
	public AbstractExportRequest(UID uid, IBillyTemplateBundle bundle){
		this.uid = uid;
		this.bundle = bundle;
	}
	
	public UID getDocumentUID(){
		return this.uid;
	}
	
	public IBillyTemplateBundle getBundle(){
		return this.bundle;
	}
	
}
