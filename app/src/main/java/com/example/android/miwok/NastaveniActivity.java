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
import java.util.Collection;

public class NastaveniActivity extends AppCompatActivity {
    PrijemActivity prijem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of padaky

      ArrayList<Padak> padaky = new ArrayList<Padak>();
        padaky.add(new Padak(123));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(124,"ovp80"));
      padaky.add(new Padak(125,"ovp80"));
      padaky.add(new Padak(126,"ovp80"));
      padaky.add(new Padak(127,"ovp80"));
      padaky.add(new Padak(128,"ovp80"));
      padaky.add(new Padak(129,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));
      padaky.add(new Padak(123,"ovp80"));



   PadakAdapter adapter = new PadakAdapter(this,padaky);

        //ArrayList<Transl> padaky = new ArrayList<Transl>();
//        padaky.add(new Transl("Where are you going?", "minto wuksus"));
//        padaky.add(new Transl("Where are you going?", "minto wuksus"));
//        padaky.add(new Transl("What is your name?", "tinnә oyaase'nә"));
//        padaky.add(new Transl("My name is...", "oyaaset..."));
//        padaky.add(new Transl("How are you feeling?", "michәksәs?"));
//        padaky.add(new Transl("I’m feeling good.", "kuchi achit"));
//        padaky.add(new Transl("Are you coming?", "әәnәs'aa?"));
//        padaky.add(new Transl("Yes, I’m coming.", "hәә’ әәnәm"));
//        padaky.add(new Transl("I’m coming.", "әәnәm"));
//        padaky.add(new Transl("Let’s go.", "yoowutis"));
//        padaky.add(new Transl("Come here.", "әnni'nem"));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Transl}s. The
        // adapter knows how to create list items for each item in the list.
        //WordAdapter adapter = new WordAdapter(this, padaky, R.color.category_phrases);


        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Transl} in the list.
        listView.setAdapter(adapter);
    }
}
