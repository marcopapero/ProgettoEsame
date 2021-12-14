package com.currencylayer.project.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.json.simple.*;

/** Classe implementazione di CurrencyLayerService che va ad implementare i metodi
 * responsabili delle chiamate all'API e della lettura dei JSONObject restituiti
 * da quest'ultima per la rielaborazione dei dati.
 * 
 * @author Marco Di Vita
 * @author Sara Bruschi */

@Service
public class CurrencyLayerServiceImpl implements CurrencyLayerService {

	private String url = "http://api.currencylayer.com/";
	private String key = "74a39b5b1ae2f4bac3f38eaa28bec030";
	private String source = "USD";
	private String acronym = ""; //DA CONTROLLARE
	private HashMap<String,String> currencies = new HashMap<String,String>();
	private HashMap<String,Double> rates = new HashMap<String,Double>();

	/** Riempie l'hashmap "rates" con la coppia di acronimi di cui si vuole conoscere
	 * l'exchange rate e relativo exchange rate letto da chiamata all'API.
	 * @param acronym indica l'acronimo della currency della quale si vuole
	 * conoscere l'exchange rate relativo al @value source sempre uguale a "USD".
	 * 
	 * */
	public HashMap<String, Double> createHashMapLive (String acronym) {
		//live
		JSONObject live = getLive();
		JSONObject data;
		data =  (JSONObject) live.get("quotes");
		rates.put(source+acronym, (Double) data.get(source+acronym));
		return rates;
	}
	
	/** Riempie l'hashmap "currencies" con la coppia acronimo e nome letti da chiamata 
	 * all'API.
	 * @param acronym indica l'acronimo della currency della quale si vuole
	 * prendere il nome.
	 * 
	 * */
	public HashMap<String, String> createHashMapList (String acronym) {
		//list
		JSONObject list = getList();
		JSONObject data;
		data =  (JSONObject) list.get("currencies");
		currencies.put(acronym, (String) data.get(acronym));
		return currencies;
	}
	
	public String getAcroym() {
		return acronym;
	}
	
	/**Metodo per la chiamata all'API che restituisce la lista di exchange rates
	 * relativa a coppie di valute, di cui la source è sempre "USD".  */
	@Override
    public JSONObject getLive() {
		JSONObject liveExchangeRate = new JSONObject();
		try {
			URLConnection openConnection = new URL(url+"live"+"?access_key="+key).openConnection();
			InputStream input = openConnection.getInputStream();
			String data = "";
			String inline = "";
			try {
				InputStreamReader inR = new InputStreamReader(input);
				BufferedReader buf = new BufferedReader(inR);
				//Write all the JSON data into a string using a scanner
				while ((inline = buf.readLine()) != null) {
					data += inline;
				}
			}
			finally {
				//Close the scanner
				input.close();
			}
			liveExchangeRate = (JSONObject) JSONValue.parseWithException(data);
		}
		catch(IOException e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		return (JSONObject) liveExchangeRate;
	}
	
	/**Metodo per la chiamata all'API che restituisce la lista di valute
	 * con relativo acronimo e nome.  */
    @Override
	public JSONObject getList() {
    	JSONObject listCurrencies = new JSONObject();
		try {
			URLConnection openConnection = new URL(url+"list"+"?access_key="+key).openConnection();
			InputStream input = openConnection.getInputStream();
			String data = "";
			String inline = "";
			try {
				InputStreamReader inR = new InputStreamReader(input);
				BufferedReader buf = new BufferedReader(inR);
				//Write all the JSON data into a string using a scanner
				while ((inline = buf.readLine()) != null) {
					data += inline;
				}
			}
			finally {
				//Close the scanner
				input.close();
			}
			listCurrencies = (JSONObject) JSONValue.parseWithException(data);
		}
		catch(IOException e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		return (JSONObject) listCurrencies;
	}

    /**Metodo per la chiamata all'API che restituisce la lista di exchange rates
	 * relativa a coppie di valute, di cui la source è sempre "USD", di una
	 * precisa data che deve essere specificata.
	 * @param date di cui si vuole conoscere l'exchange rate  */
    @Override
	public JSONObject getHistoricalQuotation(String date) {
		JSONObject historicalExchangeRate = new JSONObject();
		try {
			//TODO
			//FileWriter file ;
			//file = new FileWriter("2021-12-01");
			//BufferedWriter writer = new BufferedWriter(file);
			URLConnection openConnection = new URL(url+"historical"+"?access_key="+key+"&date="+date).openConnection();
			InputStream input = openConnection.getInputStream();
			String data = "";
			String inline = "";
			try {
				InputStreamReader inR = new InputStreamReader(input);
				BufferedReader buf = new BufferedReader(inR);
				//Write all the JSON data into a string using a scanner
				while ((inline = buf.readLine()) != null) {
					data += inline;
					//writer.write(data);
				}
			}
			finally {
				//Close the scanner
				input.close();
				//writer.flush();
				//writer.close();
			}
			historicalExchangeRate = (JSONObject) JSONValue.parseWithException(data);
		}
		catch(IOException e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		return historicalExchangeRate;
	}

	@Override
	public JSONObject toJSON(Object object) {
		JSONObject obj = null;
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getCurrency(String acronym) {
		this.acronym = acronym;
		JSONObject obj = new JSONObject();
		JSONObject curr = new JSONObject();
		String currency = "";
		try {
		JSONObject json = getList();
	    JSONObject currencies = (JSONObject) json.get("currencies");
	    currency = (String) currencies.get(acronym);
		}
		catch(Exception e) {
			System.out.println("Errore...");
			System.out.println(e);
		}
		curr.put("currency", obj);
		obj.put(acronym, currency);
		return curr;
	}

	@SuppressWarnings("unchecked")
	public Double getCouple(String acronym) {
		this.acronym = acronym;
		String currency = "";
		JSONObject curr = new JSONObject();
		JSONObject obj = new JSONObject();
		Double value = new Double(0);
		String couple = source+acronym;
		try {
			JSONObject json = getLive();
			JSONObject currencies = (JSONObject) json.get("quotes");
			value = (Double) currencies.get(couple);
		}
		catch(Exception e) {
			System.out.println("Errore...");
		}
		//TODO capire perché non stampa
		//createHashMapList(source);
		//String nameBase = currencies.get(source);
		//createHashMapList(acronym);
		//String nameQuote = currencies.get(acronym);
		//curr.put("currencies", obj);
		//obj.put(source, nameBase);
		//obj.put(acronym, nameQuote);
		//obj.put("rate", value);
		return value;
	}
}


