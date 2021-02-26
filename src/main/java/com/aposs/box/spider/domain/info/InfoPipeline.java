package com.aposs.box.spider.domain.info;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aposs.box.spider.constant.InfoConstant;
import com.aposs.box.spider.domain.info.dao.InfoMapper;
import com.aposs.box.spider.domain.info.entity.SysInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InfoPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(InfoPipeline.class);


    @Autowired
    private InfoMapper infoMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        if (resultItems.isSkip()) {
            return;
        }
        JSONArray jsonArray = resultItems.get(InfoConstant.FIELD_INFO_JSON_ARRAY);
        List<SysInformation> dataList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonArray)) {
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject iterm = jsonArray.getJSONObject(i);
                SysInformation sysInformation = new SysInformation();
                sysInformation.setId(iterm.getLong("id"));
                sysInformation.setTitle(iterm.getString("title"));
                sysInformation.setTitleImgUrl(iterm.getString("titleImgUrl"));
                sysInformation.setSpiderId(iterm.getString("spiderId"));
                sysInformation.setSpiderUrl(iterm.getString("spiderUrl"));
                sysInformation.setSourceName(iterm.getString("sourceName"));
                sysInformation.setInfoType(getCreateTypeByChannelName(iterm.getString("channelName")));
                sysInformation.setSummary(iterm.getString("summary"));
                sysInformation.setContent(iterm.getString("content"));
                sysInformation.setReleaseTime(iterm.getTimestamp("releaseTime"));
                Optional<SysInformation> optionalSysInformation = selectOne(iterm.getLong("id"));
                if (!optionalSysInformation.isPresent()) {
                    dataList.add(sysInformation);
                }
            }

        }
        if (!CollectionUtils.isEmpty(dataList)) {
            infoMapper.insertList(dataList);
        }
    }

    private Optional<SysInformation> selectOne(Long id) {
        return Optional.ofNullable(infoMapper.selectById(id));
    }

    private int getCreateTypeByChannelName(String channelName) {
        if ("行业".equals(channelName)) {
            return 1;
        }
        if ("企业".equals(channelName)) {
            return 2;
        }
        if ("新品".equals(channelName)) {
            return 3;
        }
        if ("评测".equals(channelName)) {
            return 4;
        }
        if ("导购".equals(channelName)) {
            return 5;
        }
        return 1;
    }
}
