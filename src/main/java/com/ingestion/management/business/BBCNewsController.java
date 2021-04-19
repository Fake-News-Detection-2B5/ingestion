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

public class BBCNewsController {
    public ArrayList<String> urlList = new ArrayList<String>();

    public JSONObject scrapPageContent(String link) throws IOException {

        JSONObject newsDetails = new JSONObject();
        String url = "https://www.bbc.com" + link;
        String newsTitle;
        String newsAuthor = "Unknown";
        StringBuilder newsBody = new StringBuilder();

        Document doc = Jsoup.connect(url).get();
        newsTitle = doc.title();

        Elements content = doc.getElementsByTag("article");

        for (Element divs : content) {
            Elements author = divs.getElementsByTag("strong");
            if (!author.isEmpty()) {
                newsAuthor = author.text();
            }

            Elements div = divs.getElementsByTag("div");
            for (Element elem : div) {

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

        return getJsonObject(url, newsDetails, newsTitle, newsAuthor, newsBody, urlList);
    }

    private JSONObject getJsonObject(String url, JSONObject newsDetails, String newsTitle, String newsAuthor,
            StringBuilder newsBody, ArrayList<String> urlList) {
        if (newsBody.length() != 0 && !urlList.contains(url)) {
            newsDetails.put("title", newsTitle);
            newsDetails.put("author", newsAuthor);
            newsDetails.put("url", url);
            newsDetails.put("content", JSONValue.escape(newsBody.toString()));
            urlList.add(url);
            return newsDetails;
        } else
            return null;
    }

    public void scrapMainPage() throws IOException {
        JSONArray newsList = new JSONArray();
        JSONObject tempNews = new JSONObject();

        String[] newsCategories = { "", "coronavirus", "world", "uk", "business", "technology",
                "science_and_environment", "entertainment_and_arts" };

        for (String category : newsCategories) {

            Document doc = Jsoup.connect("https://www.bbc.com/news/" + category).get();

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

        try (FileWriter file = new FileWriter("BBC_News7.json")) {
            file.write(newsList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
