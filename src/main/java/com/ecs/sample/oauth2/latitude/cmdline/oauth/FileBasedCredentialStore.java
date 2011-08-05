package com.ecs.sample.oauth2.latitude.cmdline.oauth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;

public class FileBasedCredentialStore implements CredentialStore {

	private static final String fileLocation = "c:/temp/latitude-accesstoken";

	@Override
	public AccessTokenResponse read() {
		return readTokenFromFile(fileLocation);
	}

	@Override
	public void write(AccessTokenResponse response) {
		writeFile(fileLocation, response);
	}

	private AccessTokenResponse readTokenFromFile(String fileLocation) {
		AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
	    try {
//		    FileInputStream fin = new FileInputStream(fileLocation);
//		    ObjectInputStream ois = new ObjectInputStream(fin);
//		    accessTokenResponse = (AccessTokenResponse) ois.readObject();
//		    ois.close();
		
	    	FileInputStream fin = new FileInputStream(fileLocation);
		DataInputStream in = new DataInputStream(fin);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  accessTokenResponse.accessToken = br.readLine();
		  accessTokenResponse.expiresIn = Long.parseLong(br.readLine());
		  accessTokenResponse.refreshToken = br.readLine();
		  accessTokenResponse.scope = br.readLine();
		  in.close();
	    }
	    catch (Exception ex){
	      ex.printStackTrace();
	      return null;
	    }
		return accessTokenResponse;
	  }

	private void writeFile(String fileLocation,
			AccessTokenResponse accessTokenResponse) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileLocation));
	            
	            //Start writing to the output stream
	            bufferedWriter.write(accessTokenResponse.accessToken);
	            bufferedWriter.newLine();
	            bufferedWriter.write(String.valueOf(accessTokenResponse.expiresIn));
	            bufferedWriter.newLine();
	            bufferedWriter.write(accessTokenResponse.refreshToken);
	            bufferedWriter.newLine();
	            if (accessTokenResponse.scope!=null) {
	            	bufferedWriter.write(accessTokenResponse.scope);
	            	bufferedWriter.newLine();
	            }

//			FileOutputStream fout = new FileOutputStream(fileLocation);
//			
//			ObjectOutputStream oos = new ObjectOutputStream(fout);
//			oos.writeObject(accessTokenResponse);
//			oos.close();
	            bufferedWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
