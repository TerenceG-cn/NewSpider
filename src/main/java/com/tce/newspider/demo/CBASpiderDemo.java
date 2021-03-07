package com.tce.newspider.demo;

import com.tce.newspider.downloader.DownloadException;
import com.tce.newspider.downloader.HttpClientDownloader;
import com.tce.newspider.http.HttpGetRequest;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import com.tce.newspider.scheduler.UniqueScheduler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;


public class CBASpiderDemo {
    public static void main(String[] args) {
        UniqueScheduler urls = new UniqueScheduler();

        for (int i = 1; i <= 18; i++)
            urls.in(new HttpGetRequest("http://cbadata.sports.sohu.com/ranking/players/2019/0/2/0/0/1/" + i));
        //csv 准备
        File writeFile = new File("C:\\Users\\10352\\IdeaProjects\\NewSpider\\src\\main\\java\\com\\tce\\newspider\\demo\\cba-2019.csv");

        HttpClientDownloader downloader = new HttpClientDownloader();
        HttpRequest request = null;
        try {
            //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
            while (!urls.getRequests().isEmpty()) {
                request = urls.out();

                HttpResponse response = downloader.download(request, 1000);
                String html = response.getContent();
                System.out.println("获取数据成功！");
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("cutM");
                Elements trs = elements.select("table").select("tr");

                for (Element element : trs) {
                    String str = element.text().replace(" ", ",");
                    writeText.write(str + "\n");

                }
            }
            writeText.flush();//冲刷
            writeText.close();//关闭
        } catch (DownloadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件读写出错");
        }

    }
}
