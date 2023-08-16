package com.satyam.interview;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleAuthorizeUtil {
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES =
		      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String APPLICATION_NAME  = "Rupicard Google Sheets";
    
    public static Credential authorize() throws IOException, GeneralSecurityException {
        
        // build GoogleClientSecrets from JSON file
		InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
    	        GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, scopes)
    	        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
    	        .setAccessType("offline")
    	        .build();

        // build Credential object
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
        		.authorize("user");

        return credential;
    }

	public static Sheets getSheetService()throws IOException, GeneralSecurityException  {
		Credential credential = authorize();
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), 
				JSON_FACTORY, 
				credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}
	public static void execute() throws IOException, GeneralSecurityException {
	    // Build a new authorized API client service.
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    final String spreadsheetId = "1ByQhdUyTAd5DoRK__n0h2-P-HSLTTl2WlPsLcxD_f8A";
	    final String range = "userdata!A1:B2";
	    Sheets service =
	        new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize())
	            .setApplicationName(APPLICATION_NAME)
	            .build();
	    ValueRange response = service.spreadsheets().values()
	        .get(spreadsheetId, range)
	        .execute();
	    List<List<Object>> values = response.getValues();
	    if (values == null || values.isEmpty()) {
	      System.out.println("No data found.");
	    } else {
	      System.out.println("Name, Major");
	      for (List row : values) {
	        // Print columns A and E, which correspond to indices 0 and 4.
	        System.out.printf("%s, %s\n", row.get(0), row.get(1));
	      }
	    }
	  }
}
