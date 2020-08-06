package com.github.wxpay.sdk;

import java.io.InputStream;
/*
    飞狐在微信平台的相关配置

 */
public class FeiConfig extends WXPayConfig {
    @Override
    String getAppID() {
        return "wxa1e44e130a9a8eee";
    }

    @Override
    String getMchID() {
        return "1507758211";
    }

    @Override
    String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}
