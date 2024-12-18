package com.tsspringexperience.rest.errorhandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.tsspringexperience.utils.Credentials;

public class CallsErrorHandler extends DefaultResponseErrorHandler {
	private HandleError handleError;
	public CallsErrorHandler(HandleError handleError) {
		this.handleError = handleError;
	}

	@Override
    public void handleError(ClientHttpResponse response) throws IOException {
		String error = logError(response.getBody());
		handleError.handle(error);
    }
	
	@SuppressWarnings("resource")
	private String logError(InputStream is) {
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\Z");
        String data = "";
        while (scanner.hasNext())
            data += scanner.next();
        if(Credentials.isDebugMode())
        	System.out.println(data);
        return data;
	}
	
}
