package com.cli.gateway.utils;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author lqd
 */
public class RequestUtils {
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     */
    public static String getIpAddress(ServerHttpRequest request) {
        //        String localIp = request.getHeaders().getFirst("ip");
        //获取浏览器信息
        String ua = request.getHeaders().getFirst("User-Agent");
        // 转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);

        //        浏览器版本
        //        Version browserVersion = userAgent.getBrowserVersion();
        // 获取浏览器信息
        Browser browser = userAgent.getBrowser();
        // 获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        // 系统名称
        String system = os.getName();
        // 浏览器名称
        String browserName = browser.getName();

        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = String.valueOf(request.getRemoteAddress());
        }
        String subIp = ip.substring(0, ip.lastIndexOf(":")).replace(":", "/");
//        String localIps = localIp.substring(0,ip.lastIndexOf(":")).replace(":","/");
        return subIp + "#" + system + "#" + browserName;
    }
}
