package com.dalonedrow.net;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class RESTResponseListener implements HttpResponseListener {

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Net.HttpResponseListener#cancelled()
	 */
	@Override
	public void cancelled() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.badlogic.gdx.Net.HttpResponseListener#failed(java.lang.Throwable)
	 */
	@Override
	public void failed(final Throwable t) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.badlogic.gdx.Net.HttpResponseListener#handleHttpResponse(com.badlogic
	 * .gdx.Net.HttpResponse)
	 */
	@Override
	public void handleHttpResponse(final HttpResponse httpResponse) {
		RestService.getInstance()
				.setResponseString(httpResponse.getResultAsString());
	}

}
