package br.com.medicao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Abre o app de medição (assets/www/index.html) dentro de um WebView.
 * Os arquivos são servidos num endereço https interno (app.medicao.local),
 * assim o app guarda os dados no aparelho e conversa com a Planilha Google normalmente.
 */
public class MainActivity extends Activity {

    private static final String HOST = "app.medicao.local";
    private static final String INICIO = "https://" + HOST + "/index.html";
    private WebView web;

    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        getWindow().setStatusBarColor(Color.parseColor("#2A2D30"));

        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(false);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest req) {
                Uri u = req.getUrl();
                if (!HOST.equals(u.getHost())) return null; // planilha, fontes etc.: rede normal
                String caminho = u.getPath();
                if (caminho == null || caminho.isEmpty() || caminho.equals("/")) caminho = "/index.html";
                try {
                    InputStream in = getAssets().open("www" + caminho);
                    HashMap<String, String> cab = new HashMap<>();
                    cab.put("Cache-Control", "no-cache");
                    return new WebResourceResponse(tipo(caminho), "UTF-8", 200, "OK", cab, in);
                } catch (IOException e) {
                    return new WebResourceResponse("text/plain", "UTF-8", 404, "Not Found",
                            new HashMap<String, String>(), null);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                Uri u = req.getUrl();
                if (HOST.equals(u.getHost())) return false;
                try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception ignored) { }
                return true;
            }
        });

        setContentView(web);
        if (estado != null) web.restoreState(estado);
        else web.loadUrl(INICIO);
    }

    private static String tipo(String caminho) {
        String c = caminho.toLowerCase();
        if (c.endsWith(".html")) return "text/html";
        if (c.endsWith(".js")) return "application/javascript";
        if (c.endsWith(".css")) return "text/css";
        if (c.endsWith(".json")) return "application/json";
        if (c.endsWith(".png")) return "image/png";
        if (c.endsWith(".svg")) return "image/svg+xml";
        return "application/octet-stream";
    }

    @Override
    protected void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        web.saveState(estado);
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
    }

    @Override
    protected void onDestroy() {
        web.destroy();
        super.onDestroy();
    }
}
