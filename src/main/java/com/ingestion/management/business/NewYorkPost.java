package com.ingestion.management.business;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NewYorkPost {
    public ArrayList<String> urlList = new ArrayList<>();

    public JSONObject scrapPageContent(String link) throws IOException {
        JSONObject newsDetails = new JSONObject();

        StringBuilder newsBody = new StringBuilder();
        String newsTitle;
        String newsAuthor = "Unknown";
        String newsDate = "Unknown";
        String newsThumbnail = "Unknown";

        Document doc = Jsoup.connect(link).userAgent("Mozilla").get();
        newsTitle = doc.title();

        if (!doc.select("figure").attr("class", "wp-block-image size-large").isEmpty()) {
            Element thumbnail = doc.select("figure").attr("class", "wp-block-image size-large").get(0);
            if (thumbnail.select("img").attr("data-srcset").contains("1280w")) {
                if (thumbnail.select("img").attr("data-srcset").split(",")[2].split("1280w")[0].length() > 0) {
                    newsThumbnail = thumbnail.select("img").attr("data-srcset").split(",")[2].split("1280w")[0];
                }
            }
        }

        Elements content = doc.getElementsByAttributeValue("class", "article-header");

        for (Element e : content) {
            Elements ph = e.select("p");
            for (Element elem : ph) {
                if (elem.attr("class").equals("byline")) {
                    if (elem.text().length() > 0) {
                        newsAuthor = elem.text();
                    }
                }
                if (elem.attr("class").equals("byline-date")) {
                    if (elem.text().length() > 0) {
                        newsDate = elem.text();
                    }
                }
                if (elem.attr("class").equals("")) {
                    newsBody.append(elem.text());
                }
            }
        }
        if (newsBody.length() != 0 && !urlList.contains(link)) {
            newsDetails.put("title", newsTitle);
            newsDetails.put("author", newsAuthor);
            newsDetails.put("url", link);
            newsDetails.put("description", JSONValue.escape(newsBody.toString()));

            if(newsDate.contains("| Updated"))
                newsDate = newsDate.split("\\| Updated")[0];
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy '|' hh:mma");
            SimpleDateFormat formatted = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date convertedDate;
            Date date = null;
            try {
                convertedDate = format.parse(newsDate);
                date = formatted.parse(String.valueOf(convertedDate));

            } catch (ParseException e) {
                newsDate = "Unknown";
            }

            newsDetails.put("postDate", date);
            newsDetails.put("thumbnail", newsThumbnail);
            urlList.add(link);
            return newsDetails;
        } else
            return null;
    }

    public String scrapMainPage() throws IOException {
        JSONArray newsList = new JSONArray();
        JSONObject tempNews;
        String[] newsCategories = {"", "news/", "business/", "entertainment/", "fashion/", "tech/"};

        for (String category : newsCategories) {
            Document doc = Jsoup.connect("https://nypost.com/" + category).userAgent("Mozilla").get();

            Elements links = doc.select("a");

            for (Element link : links) {
                String newsURL = link.attr("href");
                if (newsURL.matches("(https:\\/\\/nypost.com\\/)([(0-9)]+\\/)([(0-9)]+\\/)([(0-9)]+)(\\/.*)")) {
                    if (!urlList.contains(newsURL)) {
                        tempNews = scrapPageContent(newsURL);
                        if (tempNews != null) {
                            newsList.add(tempNews);
                        }
                    }
                }
            }
        }
        return newsList.toJSONString();
    }
}
