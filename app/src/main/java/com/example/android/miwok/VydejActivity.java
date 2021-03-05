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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class VydejActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of padaky
        ArrayList<Padak> padaky = new ArrayList<Padak>();
        padaky.add(new Padak("father", "әpә", R.drawable.family_father));
        padaky.add(new Padak("mother", "әṭa", R.drawable.family_mother));
        padaky.add(new Padak("son", "angsi", R.drawable.family_son));
        padaky.add(new Padak("daughter", "tune", R.drawable.family_daughter));
        padaky.add(new Padak("older brother", "taachi", R.drawable.family_older_brother));
        padaky.add(new Padak("younger brother", "chalitti", R.drawable.family_younger_brother));
        padaky.add(new Padak("older sister", "teṭe", R.drawable.family_older_sister));
        padaky.add(new Padak("younger sister", "kolliti", R.drawable.family_younger_sister));
        padaky.add(new Padak("grandmother ", "ama", R.drawable.family_grandmother));
        padaky.add(new Padak("grandfather", "paapa", R.drawable.family_grandfather));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Padak}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, padaky, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Padak} in the list.
        listView.setAdapter(adapter);
    }
}
