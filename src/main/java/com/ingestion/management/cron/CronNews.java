package com.ingestion.management.cron;

import com.google.gson.Gson;
import com.ingestion.management.business.*;
import com.ingestion.management.model.News;
import com.ingestion.management.repository.NewsRepository;
import com.ingestion.management.service.INewsService;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class CronNews {

    // private static final Logger LOGGER = (Logger)
    // LoggerFactory.getLogger(CronNewsTask.class);

    private NewsRepository newsRepository;

    public CronNews(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;

    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callBBCNews() throws IOException {
        // call BBC news
        BBCNews bbcNews = new BBCNews();
        String bbcnews = bbcNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsBBC = gson.fromJson(bbcnews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsBBC));
        System.out.println("BBCNews---:" + new Date());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callHuffingtonPost() throws IOException {
        HuffingtonPost huffingtonNews = new HuffingtonPost();
        String huffintonnews = huffingtonNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsHuffinton = gson.fromJson(huffintonnews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsHuffinton));
        System.out.println("Huffington:" + new Date());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callNY() throws IOException {
        NewYorkPost nyNews = new NewYorkPost();
        String nynews = nyNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsNY = gson.fromJson(nynews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsNY));
        System.out.println("NewYork---:" + new Date());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callNBC() throws IOException, InterruptedException {
        NBCNews nbcNews = new NBCNews();
        String nbcnews = nbcNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsNBC = gson.fromJson(nbcnews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsNBC));
        System.out.println("NBCNews---:" + new Date());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callBuzzFeed() throws IOException {
        BuzzFeed bfNews = new BuzzFeed();
        String bfnews = bfNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsBF = gson.fromJson(bfnews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsBF));
        System.out.println("BuzzFeed--:" + new Date());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void callDailyMail() throws IOException {
        DailyMail dmNews = new DailyMail();
        String dmnews = dmNews.scrapMainPage();

        Gson gson = new Gson();
        News[] newsDM = gson.fromJson(dmnews, News[].class);

        this.newsRepository.saveAll(Arrays.asList(newsDM));
        System.out.println("DailyMail--:" + new Date());
    }
}
