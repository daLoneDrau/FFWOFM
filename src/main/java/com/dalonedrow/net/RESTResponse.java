package com.dalonedrow.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.net.HttpStatus;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

public class RESTResponse implements HttpResponse {
	private final Map<String, List<String>>	headers;
	private final InputStream				resultStream;
	private final int						statusCode;
	public RESTResponse(final InputStream resultStream, final int statusCode,
			final Map<String, List<String>> headers) {
		super();
		this.resultStream = resultStream;
		this.statusCode = statusCode;
		this.headers = headers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(final String name) {
		int size = headers.get(name).size();
		return headers.get(name).get(size - 1);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getHeaders()
	 */
	@Override
	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getResult()
	 */
	@Override
	public byte[] getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getResultAsStream()
	 */
	@Override
	public InputStream getResultAsStream() {
		return resultStream;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getResultAsString()
	 */
	@Override
	public String getResultAsString() {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				resultStream));
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		String inputLine;
		try {
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponse#getStatus()
	 */
	@Override
	public HttpStatus getStatus() {
		return new HttpStatus(statusCode);
	}

}
