package com.ingestion.management.business;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import io.swagger.v3.core.util.Json;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BBCNews {
    public ArrayList<String> urlList = new ArrayList<>();

    public JSONObject scrapPageContent(String link) throws IOException {

        JSONObject newsDetails = new JSONObject();
        String url = "https://www.bbc.com" + link;
        String newsTitle;
        String newsAuthor = "Unknown";
        String newsDate = "Unknown";
        String newsThumbnail = "Unknown";
        StringBuilder newsBody = new StringBuilder();

        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        newsTitle = doc.title();

        Elements content = doc.getElementsByTag("article");

        for (Element divs : content) {
            Elements author = divs.getElementsByTag("strong");
            if (!author.isEmpty()) {
                newsAuthor = author.text();
            }

            newsDate = divs.select("time").attr("datetime");

            Elements div = divs.getElementsByTag("div");
            for (Element elem : div) {

                if (elem.attr("data-component").equals("image-block")) {
                    if (newsThumbnail.equals("Unknown")) {
                        newsThumbnail = elem.select("img").attr("src");
                    }
                }

                if (elem.attr("data-component").equals("crosshead-block")) {
                    Elements paragraphs = elem.getElementsByTag("h2");
                    newsBody.append(paragraphs.text());
                }

                if (elem.attr("data-component").equals("text-block")) {
                    Elements paragraphs = elem.getElementsByTag("p");
                    newsBody.append(paragraphs.text());
                }
            }
        }

        return getJsonObject(url, newsDetails, newsTitle, newsAuthor, newsBody, newsDate, newsThumbnail);
    }

    private JSONObject getJsonObject(String url, JSONObject newsDetails, String newsTitle, String newsAuthor,
                                     StringBuilder newsBody, String newsDate, String newsThumbnail) {
        if (newsBody.length() != 0 && !urlList.contains(url)) {
            newsDetails.put("title", newsTitle);
            newsDetails.put("author", newsAuthor);
            newsDetails.put("url", url);
            newsDetails.put("description", JSONValue.escape(newsBody.toString()));
            newsDetails.put("postDate", newsDate);
            newsDetails.put("thumbnail", newsThumbnail);

            urlList.add(url);
            return newsDetails;
        } else
            return null;
    }

    public String scrapMainPage() throws IOException {
        JSONArray newsList = new JSONArray();
        JSONObject tempNews;

        String[] newsCategories = {"", "coronavirus", "world", "uk", "business", "technology",
                "science_and_environment", "entertainment_and_arts"};

        for (String category : newsCategories) {

            Document doc = Jsoup.connect("https://www.bbc.com/news/" + category).userAgent("Mozilla").get();

            Element content = doc.getElementById("orb-modules");
            Elements links = content.getElementsByTag("a");

            for (Element link : links) {
                String linkHref = link.attr("href");

                if (linkHref.matches("(.*)/(.*)[(0-9)]+")) {
                    if (!linkHref.contains("http")) {
                        tempNews = scrapPageContent(linkHref);
                        if (tempNews != null)
                            newsList.add(tempNews);
                    }
                }

            }
        }
        return newsList.toJSONString();
    }
}