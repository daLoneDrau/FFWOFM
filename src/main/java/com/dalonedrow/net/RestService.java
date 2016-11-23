package com.dalonedrow.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

public class RestService {
	private static final String	CHARSET	= StandardCharsets.UTF_8.name();
	/**
	 * the one and only instance of the <code>RESTServiceController</code>
	 * class.
	 */
	private static RestService	instance;
	/**
	 * Gives access to the singleton instance of {@link RestService}.
	 * @return {@link RestService}
	 */
	public static RestService getInstance() {
		if (RestService.instance == null) {
			RestService.instance = new RestService();
		}
		return RestService.instance;
	}
	private String responseString;
	/**
	 * Builds an endpoint URL with parameters.
	 * @param targetURL the target URL
	 * @param params the list of any parameters
	 * @return {@link String}
	 * @throws PooledException should never occur
	 * @throws UnsupportedEncodingException if there is an error encoding the
	 * URL
	 */
	private String buildUrl(final String targetURL, final String... params)
			throws PooledException, UnsupportedEncodingException {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(targetURL.replaceAll(" ", "%20"));
		if (params != null
				&& params.length > 0) {
			if (!targetURL.endsWith("?")) {
				sb.append('?');
			}
			for (int i = 0, len = params.length; i < len - 1;) {
				sb.append(params[i]);
				sb.append('=');
				sb.append(URLEncoder.encode(params[i + 1], CHARSET));
				i += 2;
				if (i < len - 1) {
					sb.append('&');
				}
			}
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	public void callService(final String targetURL, final String... params) {
		RESTResponseListener httpResponseListener =
				new RESTResponseListener();
		if (Gdx.net == null) {
			try {
				URL url = new URL(buildUrl(targetURL, params));
				HttpURLConnection connection =
						(HttpURLConnection) url.openConnection();
				connection.setRequestMethod(HttpMethods.GET);
				connection.setRequestProperty("Accept-Charset", CHARSET);
				InputStream response = connection.getInputStream();
				httpResponseListener.handleHttpResponse(
						new RESTResponse(response, connection.getResponseCode(),
								connection.getHeaderFields()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PooledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
				HttpRequest httpRequest = requestBuilder.newRequest().method(
						HttpMethods.GET).url(buildUrl(targetURL, params))
						.build();
				Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PooledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Gets the value for the responseString.
	 * @return {@link String}
	 */
	public String getResponseString() {
		return responseString;
	}
	/**
	 * Sets the value of the responseString.
	 * @param responseString the new value to set
	 */
	public void setResponseString(final String responseString) {
		this.responseString = responseString;
	}
}
