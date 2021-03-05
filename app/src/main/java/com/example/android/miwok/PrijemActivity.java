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

import java.util.ArrayList;

public class PrijemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of padaky
        ArrayList<Padak> padaky = new ArrayList<Padak>();
        padaky.add(new Padak("one", "jedna", R.drawable.number_one));
        padaky.add(new Padak("two", "otiiko", R.drawable.number_two));
        padaky.add(new Padak("three", "tolookosu", R.drawable.number_three));
        padaky.add(new Padak("four", "oyyisa", R.drawable.number_four));
        padaky.add(new Padak("five", "massokka", R.drawable.number_five));
        padaky.add(new Padak("six", "temmokka", R.drawable.number_six));
        padaky.add(new Padak("seven", "kenekaku", R.drawable.number_seven));
        padaky.add(new Padak("eight", "kawinta", R.drawable.number_eight));
        padaky.add(new Padak("nine", "wo’e", R.drawable.number_nine));
        padaky.add(new Padak("ten", "na’aacha", R.drawable.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Padak}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, padaky, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Padak} in the list.
        listView.setAdapter(adapter);
    }
}
