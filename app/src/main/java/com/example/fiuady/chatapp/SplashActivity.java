package com.example.fiuady.chatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends Activity {
    private ChatDatabase db;
    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 2000; // 3 segundos


    private String server_url = "https://serverxd.herokuapp.com/api/users/validate";


    public JSONObject makingJson() {
        JSONObject js = new JSONObject();
        try {
            js.put("username", db.chatDao().getUserNameById(0).trim());
            js.put("password", db.chatDao().getPasswordById(0).trim());
            js.put("status", "disponible");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    public void sendJsonRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, server_url, makingJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(SplashActivity.this,response.optString("message"), Toast.LENGTH_SHORT).show();
                if(response.optString("message").equals("ok")){
                    Intent intent = new Intent(SplashActivity.this, NavigationMenu.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(SplashActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonRequest);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ChatDatabase.getDatabase(SplashActivity.this);
        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);
        makingJson();
        sendJsonRequest();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                finish();
            }
        }, DURACION_SPLASH);
    }
}
