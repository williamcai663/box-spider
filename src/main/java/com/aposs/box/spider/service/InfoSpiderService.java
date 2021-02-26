package com.aposs.box.spider.service;

import com.aposs.box.spider.domain.info.InfoPipeline;
import com.aposs.box.spider.domain.news.processor.DajiajixieInfoProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoSpiderService {

    @Autowired
    private DajiajixieInfoProcessor dajiajixieInfoProcessor;

    @Autowired
    private InfoPipeline infoPipeline;


    public void runInfoSpider() {
        List<Request> requests = createRequest();
        Spider.create(dajiajixieInfoProcessor).addRequest(requests.toArray(new Request[requests.size()])).addPipeline(infoPipeline).run();
    }

    private List<Request> createRequest() {
        int requestNum = 5;
        List<Request> requestList = new ArrayList<>();
        for (int index=1; index <= requestNum;) {
            Request request = new Request("https://www.dajiajixie.com/cms/content/pageList");
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.json("{\"pageNum\":1,\"pageSize\":100,\"filters\":{\"channelId\":" + index + "}}", "utf-8"));
            requestList.add(request);
            index=index+1;
        }
        return requestList;
    }

}
