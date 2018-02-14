package com.example.user.songlinkfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> slinks;
    ArrayList<String> downloadlinks;
    EditText song_name;
    Button searchbtn;
    ListView linklist;
    ArrayAdapter<String> adapter;
    String cSpell;
    ProgressDialog dialog;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slinks = new ArrayList<>();
        cSpell = new String();
        message = (TextView) findViewById(R.id.msgText);
        downloadlinks = new ArrayList<>();
        song_name = (EditText) findViewById(R.id.song_name);
        searchbtn = (Button) findViewById(R.id.search_btn);
        linklist = (ListView) findViewById(R.id.searchlinks);
        //slinks.add("akshay");
        dialog = new ProgressDialog(getApplicationContext());
        dialog.setMessage("Searching for links online\nPlease wait.....");
        adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.url_text,R.id.songname,downloadlinks);
        linklist.setAdapter(adapter);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songname = song_name.getText().toString();
                new link().execute(songname);
                message.setText("Searching for links....");
            }
        });

        linklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = downloadlinks.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }


    class link extends AsyncTask<String,Void,ArrayList<String>>{
        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            song_name.setText(cSpell);
            slinks.addAll(strings);
            if(downloadlinks.isEmpty()){
                message.setText("No Links Found....Sorry!!!");
            }else {
                message.setText("Links For Your Song:");
            }
            adapter.notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute() {
            downloadlinks.clear();
            adapter.notifyDataSetChanged();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            adapter.notifyDataSetChanged();
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

           /* String url = "http://www.google.com/search?q=mkyong";

            URL obj = null;
            try {
                obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            */

            ArrayList<String> result = new ArrayList<>();
            String request = "https://www.google.com/search?q=" + strings[0] +" mp3 download";
            Log.i("REQUEST","Sending request..." + request);

            try {

                // need http protocol, set this as a Google bot agent :)
                Document doc = Jsoup
                        .connect(request)
                        .userAgent("Mozilla/5.0")
                        .timeout(5000).get();

                Elements ele = doc.select(".spell");
                if(ele.size()>1) {
                    String checkedspell = ele.get(1).text();
                    cSpell = checkedspell;
                }
                else{
                    cSpell = song_name.getText().toString();
                }
                // get all links
                Elements links = doc.select("a[href]");
                Elements linktopage2 = doc.select("a[href]:containsOwn(2)");
                Log.d("page 2 link",linktopage2.get(0).absUrl("href"));
                Document doc1 = Jsoup.connect(linktopage2.get(0).absUrl("href")).userAgent("Mozilla/5.0").timeout(5000).get();
                Elements links1 = doc1.select("a[href]");
                links.addAll(links1);
                Log.i("LINK COUNT",links.size()+"");

                int m=0;
                while(m<links.size()){
                    Log.d("LINKS",links.get(m).attr("href"));
                    m++;
                }
                int l=0;
                while (l<links.size()) {
                    Element link = links.get(l);
                    String temp = link.attr("href");

                    if(temp.startsWith("/url?q=")){
                        //use regex to get domain name
                        result.add(temp.replace("/url?q=",""));
                        Log.i("SEARCH URL",temp.replace("/url?q=",""));
                        String t1 = temp.replace("/url?q=","");

                        String findStr = "http";
                        int lastIndex = 0;
                        int count = 0;

                        while(lastIndex != -1){

                            lastIndex = t1.indexOf(findStr,lastIndex);

                            if(lastIndex != -1){
                                count ++;
                                lastIndex += findStr.length();
                            }
                        }

                        //System.out.println(count);
                        if(count==1&&!t1.contains("chillingeffects")) {
                            String url = t1.substring(0, t1.indexOf("&", 0));
                            Document docu=null;
                            try {
                                docu = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(5000).get();

                            }catch (Exception e){
                                e.printStackTrace();
                                l++;
                                continue;
                            }
                            //Elements dele = docu.select("a:contains(download)");
                            Elements dele = docu.select("a:contains(Download)");

                            Log.i("DLINK NUMBER",dele.size() + "");
                            /*if(!dele.isEmpty()) {
                                int i=0;
                                while(i<2&&i<dele.size()) {
                                    String durl = dele.get(i).attr("abs:href");
                                    if(durl.endsWith(".html")){
                                        try {
                                            Document d = Jsoup.connect(durl).userAgent("Mozilla/5.0").timeout(3000).get();
                                            Elements e = d.select("a:contains(download)");
                                            if(e.size()>0){
                                                durl = e.get(0).attr("abs:href");
                                            }

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    if (durl.endsWith(".mp3")) {
                                        Log.w("Main", durl);
                                        downloadlinks.add(durl);
                                        publishProgress();
                                    }
                                    i++;
                                }
                            }*/
                            ArrayList<String> idlink = new ArrayList<>();
                            if(!dele.isEmpty()) {
                                int i=0;
                                while(i<dele.size()) {
                                    String durl = dele.get(i).attr("abs:href");
                                    Log.i("Do Li ",durl);

                                    if (durl.endsWith(".mp3")) {
                                        Log.w("Main", durl);
                                        downloadlinks.add(durl);
                                        publishProgress();
                                        i++;
                                        continue;
                                    }
                                    //if(durl.endsWith(".html")){
                                        idlink.add(durl);

                                  //  }


                                    i++;
                                }
                            }
                            if(downloadlinks.size()<2&&idlink.size()>0){
                                int j=0;
                                Log.i("IDLINK COUNT",idlink.size()+"");
                                while(j<idlink.size()&&j<5){
                                    try {
                                        Log.i("Internal link",idlink.get(j));
                                        Document d = Jsoup.connect(idlink.get(j)).userAgent("Mozilla/5.0").timeout(5000).get();
                                        Elements e = d.select("a:contains(Download)");
                                        int i=0;
                                        while(i<e.size()) {
                                            String durl = e.get(i).attr("abs:href");
                                            Log.i("In Do Li ",durl);
                                            if (durl.endsWith(".mp3")) {
                                                Log.i("Main", durl);
                                                downloadlinks.add(durl);
                                                publishProgress();
                                            }
                                            i++;
                                        }

                                    }catch(UnsupportedMimeTypeException e){
                                        Log.i("MIME TYPE",e.getMimeType());
                                        //if(e.getMimeType().equals("application/force-download")){
                                            downloadlinks.add(idlink.get(j));
                                        //}
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    j++;
                                }

                            }

                        }
                    }


                if(downloadlinks.size()>=2){
                        break;
                }
                l++;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }




            return result;
        }
    }

}
