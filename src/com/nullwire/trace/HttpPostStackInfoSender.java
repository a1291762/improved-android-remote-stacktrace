package com.nullwire.trace;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;

/**
 * A StackInfoSender that performs an individual http POST to a URL for each
 * stack info provided. The http requests will be performed inside of a single
 * <code>AsyncTask</code>, so submitStackInfos must be called from the main thread.
 *  
 * The data sent is identical to the data sent in
 * the original android-remote-stacktrace:
 * <ul>
 * <li>package_name
 * <li>package_version
 * <li>phone_model
 * <li>android_version
 * <li>stacktrace
 * </ul>
 * 
 * @author pretz
 *
 */
public class HttpPostStackInfoSender implements StackInfoSender {
	
	private static final String TAG = "HttpPostStackInfoSender";
	
	private final String mPostUrl;
	
	/**
	 * Construct a new HttpPostStackInfoSender that will submit
	 * stack traces by POSTing them to postUrl.
	 */
	public HttpPostStackInfoSender(String postUrl) {
		mPostUrl = postUrl;
	}
	
	public void submitStackInfos(Collection<StackInfo> stackInfos, final String packageName) {
		new AsyncTask<StackInfo, Void, Void>() {
			
			@Override
			protected Void doInBackground(StackInfo... infos) {

				for (final StackInfo info : infos) {
					try {
						MultipartUtility multipart = new MultipartUtility(mPostUrl, "utf-8");
						multipart.addFormField("package_name", packageName);
						multipart.addFormField("package_version", info.getPackageVersion());
						multipart.addFormField("phone_model", info.getPhoneModel());
						multipart.addFormField("android_version", info.getAndroidVersion());
						multipart.addFormField("stacktrace", TextUtils.join("\n", info.getStacktrace()));
						multipart.finish();
					} catch (IOException e) {
						Log.e(TAG, "Error sending stack traces", e);
					}
				}

				return null;
			}
		}.execute(stackInfos.toArray(new StackInfo[0]));
	}

}
