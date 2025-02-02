package com.dtp.example;

import com.dtp.core.DtpExecutor;
import com.dtp.core.support.DtpCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DtpConfig related
 *
 * @author yanhom
 */
@Configuration
public class DtpConfig {

    @Bean
    public DtpExecutor dtpExecutor() {
        return DtpCreator.createDynamicFast("dynamic-tp-test");
    }
}
