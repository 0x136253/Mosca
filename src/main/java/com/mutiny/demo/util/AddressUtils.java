package com.mutiny.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xpath.internal.XPathAPI;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ip地址工具类
 * @author ACGkaka
 *
 */
public class AddressUtils {
    /**
     * 获取本机的内网ip地址
     * @return
     * @throws SocketException
     */
    public static String getInnetIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    private static String getHtml(String urls) {
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(urls);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
            //System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println(inputLine);
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  inputLine.toString();
    }

    /**
     * 获取本机的外网ip地址
     * @return
     */
    public static String getV4IP(){
        String ip = "";
        String chinaz = "https://www.ip.cn/";
        return getValueByXpath("/html/body/div[1]/div[4]/div/p[1]/code/text()",getHtml(chinaz));
    }


    private static String getValueByXpath(String xPath, String html) {
        TagNode tagNode = new HtmlCleaner().clean(html);
        String value = null;
        try {
            Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPath xpath = XPathFactory.newInstance().newXPath();
            value = (String) xpath.evaluate(xPath, doc, XPathConstants.STRING);
        } catch (Exception e) {
            System.out.println("Extract value error. " + e.getMessage());
            e.printStackTrace();
        }
        return value;
    }


    private static String getAddress(String ip){
        String urlPre ="http://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=";
        String urlSur ="&co=&resource_id=6006&oe=utf8";
        String url = urlPre+ip+urlSur;
        JSONObject jsonObject = JSONObject.parseObject(getHtml(url));
        return jsonObject.getJSONArray("data").getJSONObject(0).getString("location");
    }
    /**
     * 测试方法
     * 获取本机的内网ip，外网ip和指定ip的地址
     * @param args
     */
    public static void main(String[] args) {
        AddressUtils addressUtils = new AddressUtils();
//step1.获得内网ip和外网ip，并输出到控制台
        String ip1="";
        try {
            //局域网的ip地址，比如：192.168.1.101
            ip1 = addressUtils.getInnetIp();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        System.out.println("内网ip:"+ip1);
        //用于实际判断地址的ip
        String ip2 = addressUtils.getV4IP();
        System.out.println("外网ip:"+ip2);
//step2.根据外网ip地址，得到市级地理位置
        String address = getAddress(ip2);
        System.out.println(address);
    }
}
