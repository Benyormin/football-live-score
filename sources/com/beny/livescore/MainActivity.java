package com.beny.livescore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    boolean isClicked = false;
    MatchAdapter matchAdapter = new MatchAdapter(this);
    RecyclerView recyclerView;
    Button refreshBtn;
    String url = "https://api.football-data.org/v2/matches";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.refreshBtn = (Button) findViewById(R.id.refresh);
        this.recyclerView = (RecyclerView) findViewById(R.id.match_recycler);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        this.refreshBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.this.isClicked) {
                    MainActivity.this.clearData();
                }
                MainActivity.this.recyclerView.clearOnChildAttachStateChangeListeners();
                requestQueue.add(new JsonObjectRequest(0, MainActivity.this.url, (JSONObject) null, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Log.d("response is :", response.toString());
                        try {
                            JSONArray Jarray = response.getJSONArray("matches");
                            for (int i = 0; i < Jarray.length(); i++) {
                                JSONObject match_index = Jarray.getJSONObject(i);
                                MatchDetails matchDetails = new MatchDetails();
                                JSONObject homeTeam = match_index.getJSONObject("homeTeam");
                                JSONObject awayTeam = match_index.getJSONObject("awayTeam");
                                JSONObject fullTimeScore = match_index.getJSONObject("score").getJSONObject("fullTime");
                                matchDetails.setUtcDate(match_index.getString("utcDate"));
                                matchDetails.setStatus(match_index.getString(NotificationCompat.CATEGORY_STATUS));
                                matchDetails.setId(match_index.getInt("id"));
                                matchDetails.setHomeTeamName(homeTeam.getString("name"));
                                matchDetails.setAwayTeamName(awayTeam.getString("name"));
                                if (matchDetails.getStatus().equalsIgnoreCase("IN_PLAY") || matchDetails.getStatus().equalsIgnoreCase("FINISHED")) {
                                    matchDetails.setHome_team_score(fullTimeScore.getInt("homeTeam"));
                                    matchDetails.setAway_team_score(fullTimeScore.getInt("awayTeam"));
                                }
                                Log.d("", matchDetails.toString());
                                MatchDetails.matchDetails_List.add(matchDetails);
                                MainActivity.this.recyclerView.setAdapter(MainActivity.this.matchAdapter);
                                MainActivity.this.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this.getApplicationContext()));
                                MainActivity.this.isClicked = true;
                                MainActivity.this.refreshBtn.setText("Refresh");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error!", 0).show();
                    }
                }) {
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-Auth-Token", "6ea4aeaefc644a4fb548a7a464b5465d");
                        return headers;
                    }
                });
            }
        });
    }

    public void clearData() {
        MatchDetails.matchDetails_List.clear();
        this.matchAdapter.notifyDataSetChanged();
    }
}
