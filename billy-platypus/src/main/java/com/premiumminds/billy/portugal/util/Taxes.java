package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class Taxes { //TODO
	
	public class Continent {
		
		public PTTax normal() {
			//TODO
			return null;
		}
		
		public PTTax intermediate() {
			//TODO
			return null;
		}
		
		public PTTax reduced() {
			//TODO
			return null;
		}
		
	}
	
	public class Madeira {
		
		public PTTax normal() {
			//TODO
			return null;
		}
		
		public PTTax intermediate() {
			//TODO
			return null;
		}
		
		public PTTax reduced() {
			//TODO
			return null;
		}
		
	}
	
	public class Azores {
		
		public PTTax normal() {
			//TODO
			return null;
		}
		
		public PTTax intermediate() {
			//TODO
			return null;
		}
		
		public PTTax reduced() {
			//TODO
			return null;
		}
		
	}

	public PTTax exempt() {
		//TODO
		return null;
	}
	
	private final Continent continent;
	private final Madeira madeira;
	private final Azores azores;
	private final Injector injector;
	
	public Taxes(Injector injector) {
		continent = new Continent();
		madeira = new Madeira();
		azores = new Azores();
		this.injector = injector;
	}
	
	public Continent continent() {
		return continent;
	}
	
	public Madeira madeira() {
		return madeira;
	}

	public Azores azores() {
		return azores;
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
	
}