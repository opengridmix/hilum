package com.hilum.otp.common.utils;

import com.hilum.otp.common.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public abstract class IPUtils {
    private static String[] PRIVATE_ADDRESS = new String[] {
         "127.0.0.1","localhost", "192.168.0.", "172.16.", "10.", "169.254."
    };
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request http请求
     * @return 客户的真实公网ip，用于识别ip防止非法故意滥用服务器资源。
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip != null && ip.length() > 0) {
            int index = ip.indexOf(',');
            if( index != -1) {
                ip = ip.substring(0, index).trim();
            }
        }

        if(ObjectUtils.isBlank(ip)) {
            throw new ApplicationException("非法ip来源");
        }

        return ip;
    }

    public static boolean isPrivateIP(HttpServletRequest request) {
        String ip = getIpAddress(request);
        return isPrivateIP(ip);
    }

    public static boolean isPrivateIP(String ip) {
        long count = Arrays.stream(PRIVATE_ADDRESS).filter(t -> ip.startsWith(t)).count();
        return count > 0;
    }
}
