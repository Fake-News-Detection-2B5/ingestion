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

public class HuffingtonPost {
    public ArrayList<String> urlList = new ArrayList<>();

    public JSONObject scrapPageContent(String link) throws IOException {
        StringBuilder newsBody = new StringBuilder();
        JSONObject newsDetails = new JSONObject();
        Document doc = Jsoup.connect(link).userAgent("Mozilla").get();
        String newsTitle = doc.title();
        String newsAuthor = "Unknown";
        String newsDate = "Unknown";
        String newsThumbnail = "Unknown";

        Elements newsDateSpan = doc.select("span").attr("class", "timestamp__date--published");

        for (Element e : newsDateSpan) {
            if (e.attr("aria-label").contains("Published on")) {
                newsDate = e.text();
            }
        }

        Elements newsAuthorSpan = doc.select("a");
        for (Element e : newsAuthorSpan) {
            if (e.attr("aria-label").contains("By")) {
                newsAuthor = e.attr("aria-label");
            }
        }

        Elements newsThumbnailImg = doc.getElementsByTag("figure").attr("class", "content-list-component image yr-content-list-image");
        if (newsThumbnailImg.size() > 0) {
            newsThumbnail = newsThumbnailImg.get(0).getElementsByTag("img").attr("src");
        }

        Elements newsSubtitle = doc.getElementsByClass("headline__subtitle");
        newsBody.append(newsSubtitle.text());

        Elements newsArticleBody = doc.getElementsByClass("content-list-component");
        newsBody.append(newsArticleBody.text());


        if (newsBody.length() != 0) {
            newsDetails.put("title", newsTitle);
            newsDetails.put("author", newsAuthor);
            newsDetails.put("url", link);
            newsDetails.put("description", JSONValue.escape(newsBody.toString()));
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a 'ET'");
            SimpleDateFormat formatted = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date convertedDate;
            try {
                convertedDate = format.parse(newsDate);
                newsDate = formatted.format(convertedDate);
            } catch (ParseException e) {
                newsDate = "Unknown";
            }
            newsDetails.put("postDate", newsDate);
            newsDetails.put("thumbnail", newsThumbnail);
            return newsDetails;
        } else
            return null;
    }

    public String scrapMainPage() throws IOException {
        JSONArray newsList = new JSONArray();
        JSONObject tempNews;
        String[] newsCategories = {"", "news/", "feature/coronavirus", "news/politics", "entertainment/", "section/huffpost-personal"};

        for (String category : newsCategories) {
            Document doc = Jsoup.connect("https://www.huffpost.com/" + category).userAgent("Mozilla").get();

            Elements links = doc.select("a");

            for (Element link : links) {
                String newsURL = link.attr("href");
                if (newsURL.matches("(https:\\/\\/www.huffpost.com\\/)(entry)(\\/.*)")) {
                    if (!urlList.contains(newsURL)) {
                        tempNews = scrapPageContent(newsURL);
                        if (tempNews != null) {
                            newsList.add(tempNews);
                        }
                        urlList.add(newsURL);
                    }
                }
            }
        }
        return newsList.toJSONString();
    }
}
