package com.example.android.chuckchuck;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class ApiInfoFragment extends Fragment {
    private static final String URL = "http://www.icndb.com/api/";
    private Context context;
    private WebView webView;
    private ProgressBar progressBar;

    public ApiInfoFragment() {
        // Required empty public constructor
    }

    public static ApiInfoFragment newInstance(String param1, String param2) {
        ApiInfoFragment fragment = new ApiInfoFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_api_info, container, false);

        webView = view.findViewById(R.id.web_view);
        progressBar = view.findViewById(R.id.progress_bar);

        updateWebView();

        return view;
    }

    private void updateWebView () {
        webView.setWebViewClient(new FragmentWebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);

        NetworkConnection network = new NetworkConnection(context);

        if (network.checkNetworkConnection()) {
            webView.loadUrl(URL);
        }
        else {
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    public class FragmentWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

}


