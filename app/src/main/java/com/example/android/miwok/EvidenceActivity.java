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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class EvidenceActivity extends AppCompatActivity {

    private TextView textViewResult;
    private static final String TAG = "MyActivity";
    private JsonApiInterFace jsonApiInterFace;


    ArrayList<Post> mposts = new ArrayList<Post>();
    ArrayList<Comment> mcomments = new ArrayList<Comment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        //textViewResult = (TextView) findViewById(R.id.tvHome);

        // Create a list of padaky
//        ArrayList<Transl> padaky = new ArrayList<Transl>();
//       padaky.add(new Transl("black", "kululli", R.drawable.color_black));
//        padaky.add(new Transl("white", "kelelli", R.drawable.color_white));


        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/josse7cz/demo/")//("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build();
        jsonApiInterFace = retrofit.create(JsonApiInterFace.class);
       getPosts(null ,null);
       // getComments();
       // createPost();
       //updatePost();
       // deletePost();


    }


    public void getPosts(Integer x, Integer y) {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("userId", "2");
//        parameters.put("_sort", "id");
//        parameters.put("_order", "desc");
//        Call<List<Post>> call = jsonApiInterFace.getPosts(parameters);

        Call<List<Post>> call = jsonApiInterFace.getPosts(new Integer[]{x, y}, "id", "asc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code" + response.code());
                    return;
                }
                List<Post> posts = response.body();


                for (Post post : posts) {
//                    String content = "";
//                    content += "" + post.getId() + "\n";
//                    content += "userID: " + post.getUserId() + "\n";
//                    content += "title" + post.getTitle() + "\n";
//                    content += "text" + post.getText() + "\n\n";

                    // textViewResult.append(content);

                    mposts.add(post);

                }

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

    private void getComments() {
        Call<List<Comment>> call = jsonApiInterFace
                .getComments("https://jsonplaceholder.typicode.com/posts/1/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";
                    //textViewResult.append(content);
                    mcomments.add(comment);
                }

                //Create the adapter to convert the array to views
                CommentAdapter c = new CommentAdapter(EvidenceActivity.this, mcomments);
                // Attach the adapter to a ListView
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(c);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "5");
        parameters.put("title", "Something to eat");
        parameters.put("body", "Nothing");


       // Post post = new Post(11, "How to make", "It is easy");
        Call<Post> call = jsonApiInterFace.createPost(parameters);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code" + response.code());
                    Toast.makeText(EvidenceActivity.this, "Error Code: "+response.code(), LENGTH_SHORT).show();
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "userID: " + postResponse.getUserId() + "\n";
                content += "title: " + postResponse.getTitle() + "\n";
                content += "text: " + postResponse.getText() + "\n\n";
                //textViewResult.setText(content);
                Toast.makeText(EvidenceActivity.this,"Created:"+content,Toast.LENGTH_LONG).show();
                getPosts(null,null);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void updatePost() {
        Post post = new Post(1, null, "New Text nejaky brutální text");
        Call<Post> call = jsonApiInterFace.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(EvidenceActivity.this, "Update, Code: "+response.code(), LENGTH_SHORT).show();
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                //textViewResult.setText(content);
                getPosts(null,null);
                Toast.makeText(EvidenceActivity.this, "updated: "+response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                Toast.makeText(EvidenceActivity.this, "Fail: "+t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }
    private void deletePost() {
        Call<Void> call = jsonApiInterFace.deletePost(1);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               // textViewResult.setText("Code: " + response.code());
                Toast.makeText(EvidenceActivity.this, "Code: "+response.code(), LENGTH_SHORT).show();
                getPosts(null,null);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Toast.makeText(EvidenceActivity.this, "Code: "+t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

}

//                Log.v(TAG, "ZDAAAR" + mposts.get(0).getText());
//                Log.v(TAG, "ZDAAAR" + mposts.get(25).getTitle());
//                Log.v(TAG, "ZDAAAR" + mposts.size());
// Log.v(TAG, "ZDAAAR" + mmposts.get(2));
