package com.premiumminds.billy.core.test.fixtures;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Context;

public class MockContextEntity extends MockBaseEntity implements ContextEntity {
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String description;
	public ContextEntity parentContext;
	
	public MockContextEntity() {
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Context getParentContext() {
		return parentContext;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public <T extends ContextEntity> void setParentContext(T parent) {
		this.parentContext = parent;
	}

}
