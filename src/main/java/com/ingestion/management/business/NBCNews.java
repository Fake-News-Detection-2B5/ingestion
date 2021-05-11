package com.ingestion.management.business;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NBCNews {
    public ArrayList<String> urlList = new ArrayList<>();

    public JSONObject scrapPageContent(String link) throws IOException {
        StringBuilder newsBody = new StringBuilder();
        JSONObject newsDetails = new JSONObject();
        Document doc = Jsoup.connect(link).userAgent("Mozilla").get();
        String newsTitle = doc.title();
        String newsAuthor = "Unknown";
        String newsDate = "Unknown";
        String newsThumbnail = "Unknown";

        Elements mainBlock = doc.getElementsByTag("article").attr("class", "article-body");
        for (Element e : mainBlock) {
            Elements author_elem = e.getElementsByTag("section").attr("class", "mb7");
            if (author_elem.size() > 0) {
                Element a = author_elem.get(0);
                if (a.getElementsByTag("div").attr("class", "founders-cond").contains("By")) {
                    if (a.getElementsByTag("div").attr("class", "founders-cond").text().split("By")[1].length() > 0)
                        newsAuthor = a.getElementsByTag("div").attr("class", "founders-cond").text().split("By")[1];
                }
            }
        }

        for (Element e : mainBlock) {
            Elements contents = e.getElementsByTag("div").attr("class", "article-body__content");

            if (contents.size() > 0) {
                Elements paragraphs = contents.get(0).getElementsByTag("p");
                for (Element p : paragraphs) {
                    newsBody.append(p.text());
                }
            }

        }
        if (newsBody.length() != 0 && !urlList.contains(link)) {
            newsDetails.put("title", newsTitle);
            newsDetails.put("author", newsAuthor);
            newsDetails.put("url", link);
            newsDetails.put("content", JSONValue.escape(newsBody.toString()));
            urlList.add(link);
            return newsDetails;
        } else
            return null;
    }


    public String scrapMainPage() throws IOException {
        String[] newsCategories = {"", "world", "politics", "business", "health", "entertainment"};
        JSONArray newsList = new JSONArray();
        JSONObject tempNews;

        for (String category : newsCategories) {
            String url = "https://www.nbcnews.com/" + category;

            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements mainLinks = doc.select("a[href]");
            for (Element e : mainLinks) {
                String tempUrl = e.attr("href");
                if (tempUrl.matches("(.*)/(.*)[(0-9)]+")) {
                    if (!tempUrl.contains("/video/")) {
                        tempNews = scrapPageContent(tempUrl);
                        if (tempNews != null)
                            newsList.add(tempNews);
                    }
                }

            }
        }
        return newsList.toJSONString();
    }
}
