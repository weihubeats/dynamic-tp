package com.dtp.starter.nacos.refresh;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.dtp.common.config.DtpProperties;
import com.dtp.common.em.ConfigFileTypeEnum;
import com.dtp.common.util.NacosUtil;
import com.dtp.core.refresh.AbstractRefresher;
import com.dtp.core.support.DtpCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * NacosRefresher related
 *
 * @author: yanhom
 * @since 1.0.0
 **/
@Slf4j
public class NacosRefresher extends AbstractRefresher implements InitializingBean, Listener {

    private static final ThreadPoolExecutor EXECUTOR = DtpCreator.createCommonFast("nacos-listener");

    private ConfigFileTypeEnum configFileType;

    @NacosInjected
    private ConfigService configService;

    @Resource
    private DtpProperties dtpProperties;

    @Resource
    private Environment environment;

    @Override
    public void afterPropertiesSet() {

        DtpProperties.Nacos nacos = dtpProperties.getNacos();
        configFileType = NacosUtil.getConfigType(dtpProperties, ConfigFileTypeEnum.PROPERTIES);
        String dataId = NacosUtil.deduceDataId(nacos, environment, configFileType);
        String group = NacosUtil.getGroup(nacos, "DEFAULT_GROUP");

        try {
            configService.addListener(dataId, group, this);
        } catch (NacosException e) {
            log.error("DynamicTp refresher, {} addListener error.", getClass().getSimpleName());
        }
    }

    @Override
    public Executor getExecutor() {
        return EXECUTOR;
    }

    @Override
    public void receiveConfigInfo(String content) {
        refresh(content, configFileType);
    }

}
