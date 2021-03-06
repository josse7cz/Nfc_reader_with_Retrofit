/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvidenceActivity extends AppCompatActivity {

    private TextView textViewResult;
    private static final String TAG = "MyActivity";
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    ArrayList<Post> mposts = new ArrayList<Post>();
    ArrayList<String> mmposts = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        textViewResult = (TextView) findViewById(R.id.tvHome);

        // Create a list of padaky
//        ArrayList<Padak> padaky = new ArrayList<Padak>();
//       padaky.add(new Padak("black", "kululli", R.drawable.color_black));
//        padaky.add(new Padak("white", "kelelli", R.drawable.color_white));

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code" + response.code());
                    return;
                }
                List<Post> posts = response.body();


                for (Post post : posts) {
                    String content = "";
                    content += "" + post.getId() + "\n";
                    content += "userID: " + post.getUserId() + "\n";
                    content += "title" + post.getTitle() + "\n";
                    content += "text" + post.getText() + "\n\n";

                    // textViewResult.append(content);
                    
                    mposts.add(post);

                }
//                Log.v(TAG, "ZDAAAR" + mposts.get(0).getText());
//                Log.v(TAG, "ZDAAAR" + mposts.get(25).getTitle());
//                Log.v(TAG, "ZDAAAR" + mposts.size());
               // Log.v(TAG, "ZDAAAR" + mmposts.get(2));

                //Create the adapter to convert the array to views
                PostAdapter adapter = new PostAdapter(EvidenceActivity.this, mposts);
// Attach the adapter to a ListView
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}
