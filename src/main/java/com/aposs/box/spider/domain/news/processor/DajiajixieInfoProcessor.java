package com.aposs.box.spider.domain.news.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aposs.box.spider.constant.InfoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


@Component
public class DajiajixieInfoProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(DajiajixieInfoProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
        //  page.putField("list-item", page.getHtml().xpath("//body/div[@class='item']//h1/a"));

        String pageString = page.getRawText();
        logger.info(pageString);
        JSONObject pageJson = JSONObject.parseObject(pageString);
        if (pageJson.getInteger("code") != 0) {
            logger.error("请求失败,请检查");
        }
        JSONArray jsonArray = pageJson.getJSONObject("data").getJSONArray("list");
        page.putField(InfoConstant.FIELD_INFO_JSON_ARRAY, jsonArray);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
