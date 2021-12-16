package com.currencylayer.project.filters;

import org.json.simple.JSONObject;

import com.currencylayer.project.exceptions.CurrencyNotFoundException;

/**
 * Interfaccia relativa ai filtri
 * @author Marco Di Vita
 * @author Sara Bruschi
 */
public interface FiltersService {
	
	public JSONObject currencyFilter(String acronym) throws CurrencyNotFoundException;
	public JSONObject historicalFilter(String date, String acronym1, String acronym2) throws CurrencyNotFoundException;

}
