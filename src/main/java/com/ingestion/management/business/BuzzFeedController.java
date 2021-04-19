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

public class BuzzFeedController {
    public ArrayList<String> urlList = new ArrayList<>();

    public JSONObject scrapPageContent(String link) throws IOException {
        JSONObject newsDetails = new JSONObject();
        String newsTitle;
        String newsAuthor = "Unknown";
        StringBuilder newsBody = new StringBuilder();

        Document doc = Jsoup.connect(link).get();
        newsTitle = doc.title();

        Elements mainBlock = doc.getElementsByTag("main").attr("id", "news-content");
        for (Element e : mainBlock) {
            Elements author_elem = e.getElementsByTag("header");
            for (Element author : author_elem) {
                Elements authorLinks = author.getElementsByTag("span").attr("class",
                        "news-byline-full__name xs-block link-initial--text-black");
                if (authorLinks.size() > 0) {
                    Element a = authorLinks.get(0);
                    newsAuthor = a.getElementsByTag("span").get(1).text();
                }
            }

            Elements divs = e.getElementsByTag("div").attr("id", "mod-article-wrapper-1");
            for (Element elem : divs) {
                if (elem.attr("data-module").equals("subbuzz-text")) {
                    Elements paragraphs = elem.getElementsByTag("p");
                    newsBody.append(paragraphs.text());
                }
            }
        }

        return getJsonObject(link, newsDetails, newsTitle, newsAuthor, newsBody, urlList);

    }

    public static JSONObject getJsonObject(String link, JSONObject newsDetails, String newsTitle, String newsAuthor,
            StringBuilder newsBody, ArrayList<String> urlList) {
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

    public void scrapMainPage() throws IOException {
        JSONArray newsList = new JSONArray();
        JSONObject tempNews;

        String[] newsCategories = { "", "section/arts-entertainment", "section/books", "section/culture",
                "section/inequality", "section/jpg", "section/lgbtq", "collection/opinion", "section/politics",
                "section/reader", "section/science", "section/tech", "section/world" };
        for (String category : newsCategories) {
            Document doc = Jsoup.connect("https://www.buzzfeednews.com/" + category).get();

            Elements mainBlock = (Elements) doc.getElementsByTag("main").attr("id", "news-content");

            for (Element e : mainBlock) {
                Elements articles = e.getElementsByTag("article");
                for (Element article : articles) {
                    Elements links = article.getElementsByTag("a");

                    for (Element link : links) {
                        String linkHref = link.attr("href");
                        if (linkHref.contains("https")) {
                            tempNews = scrapPageContent(linkHref);
                            if (tempNews != null) {
                                newsList.add(tempNews);
                            }
                        }
                    }
                }
            }
        }

        try (FileWriter file = new FileWriter("BuzzFeed_News2.json")) {
            file.write(newsList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
