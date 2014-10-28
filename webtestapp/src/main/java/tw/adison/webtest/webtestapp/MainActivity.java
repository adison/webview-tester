package tw.adison.webtest.webtestapp;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    WebView myWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebview = (WebView)findViewById(R.id.aWebview);
        // 4.4以上可开启 REMOTE WEBVIEW DEBUG,
        // open a webview in devtools, chrome://inspect, and you should fine the device
        // https://developer.chrome.com/devtools/docs/remote-debugging, SECTION: BROWSER TAB
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if( 0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                myWebview.setWebContentsDebuggingEnabled(true);
            }
        }

        // copy android web setting
        WebSettings ws = myWebview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setAllowFileAccess(true);

//        myWebview.addJavascriptInterface(new IJavascriptInterface(context), "ForAndroid");
        myWebview.setWebChromeClient(new WebChromeClient());
        myWebview.setWebViewClient(new WebViewClient());

        Map<String, String> noCache = new HashMap<String, String>(2);
        noCache.put("Pragma", "no-cache");
        noCache.put("Cache-Control", "no-cache");

        // 开启的网站
        String url = "https://google.com";
        myWebview.loadUrl(url, noCache);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override the android back button event, prevent for exit app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && myWebview.canGoBack()) {
            myWebview.goBack();
            return true;
        }
        return  super.onKeyDown(keyCode, event);
    }

}
