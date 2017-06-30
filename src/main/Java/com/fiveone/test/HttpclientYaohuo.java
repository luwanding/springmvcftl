package com.fiveone.test;

import com.fiveone.util.HttpClient;
import com.fiveone.util.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/30.
 * url : https://yaohw.com/
 * @Descipt 自动点赞帖子
 */
public class HttpclientYaohuo {
    private static final String yaohuo_login = "https://yaohw.com/waplogin.aspx";
    private static final String charset = "UTF-8";
    private static Map<String, String> headMap = new HashMap<String, String>();
    private static Map<String, String> params = new HashMap<String, String>();
    private static HttpClient httpClient = new HttpClient();
    private static final String YaoHuoNewUrl = "https://yaohw.com/bbs/book_list.aspx?gettotal=2017&action=new";
    private static final String homeKey = "首页>论坛>所有最新贴子";
    private static final String endKey = "加载更多";
    private static final String Home_Url = "https://yaohw.com/";
    static {
        headMap.put("Host", "yaohw.com");
        headMap.put("Connection", "keep-alive");
        headMap.put("Pragma", "no-cache");
        headMap.put("Cache-Control", "no-cache");
        headMap.put("Origin", "https://yaohw.com");
        headMap.put("Upgrade-Insecure-Requests", "1");
        headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
        headMap.put("Content-Type", "application/x-www-form-urlencoded");
        headMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headMap.put("Referer", "https://yaohw.com/waplogin.aspx?backurl=wapindex.aspx?sid=-2");
        headMap.put("Accept-Encoding", "gzip, deflate, br");
        headMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        headMap.put("Referer", "https://yaohw.com/waplogin.aspx?backurl=wapindex.aspx?sid=-2");

        params.put("savesid", "0");
        params.put("action", "login");
        params.put("classid", "0");
        params.put("siteid", "1000");
        params.put("sid", "-3-0-0-0-0");
        params.put("backurl", "wapindex.aspx%3Fsid%3D-2");
        params.put("g", "%E7%99%BB+%E5%BD%95");
    }

    @Test
    public void test() throws Exception {
        try {
            boolean bl = login("18874264730", "151809");
            if(bl){
                System.out.println("ok");
                List<Map<String,Object>> list = getUrl();
                /*for (Map<String,Object> map:list
                     ) {
                    System.out.println(map.get("title")+"__"+map.get("articleurl")+"__"+map.get("replyurl"));
                }*/
                onToTop(list);
            }else{
                System.out.println("请稍后重试!!");
            }
        } catch (Exception e) {
            System.out.println("登录请求异常！");
            e.printStackTrace();
        }
    }
    /**
     * 登录请求
     * @param logname
     * @param logpass
     * @throws Exception
     */
    private boolean login(String logname, String logpass) throws Exception {
        params.put("logname", logname);
        params.put("logpass", logpass);


        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            pairList.add(pair);
        }
        HttpResult result = httpClient.doPost(pairList, charset, yaohuo_login, headMap, "", false);
        if (HttpClient.HTTP_STATUS_CODE_302 == result.getResponseCode() || HttpClient.HTTP_STATUS_CODE_301 == result.getResponseCode()) {
            result = httpClient.doGet(result.getLocation(), null, "", false);
        }
        String resume_html = result.getResponseBody();//返回的消息结果页面
        Document doc = Jsoup.parse(resume_html);
        String msg = doc.select(".tip").text();
        if (msg.equals("登录成功！")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 得到登录后最新帖子集合
     * @throws Exception
     */
    private List<Map<String,Object>> getUrl() throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        HttpGet get = new HttpGet(YaoHuoNewUrl);
        HttpResult result = httpClient.doGet(YaoHuoNewUrl,headMap,null,false);
        System.out.println("----------------");
        String new_html = result.getResponseBody();
        Document doc = Jsoup.parse(new_html);
        Elements eles = doc.select("div");
        for (Element ele:eles
             ) {
            String str = ele.text();
            if(str.equals(homeKey)){
                continue;
            }
            if(str.indexOf(endKey) > -1){
                break;
            }
            Elements A_Elements = ele.select("a");
            if(A_Elements.size() < 2){
                continue;
            }
            String title = A_Elements.get(0).text();//帖子标题
            String articleurl = replaceUrl(A_Elements.get(0).attr("href"));//帖子地址
            String replyurl = replaceUrl(A_Elements.get(1).attr("href"));//帖子回复内容地址
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("title",title);
            map.put("articleurl",articleurl);
            map.put("replyurl",replyurl);
            list.add(map);
        }
        return list;
    }
    /**
     * 自动点击帖子<顶>  链接
     * @throws Exception
     */
    private void onToTop(List<Map<String,Object>> list) throws Exception{
        for (Map<String,Object> map:
                list ) {
            String url = map.get("articleurl").toString();
            HttpResult result = httpClient.doGet(url,headMap,null,false);
            Document doc = Jsoup.parse(result.getResponseBody());
            String urls = replaceUrl(doc.select("a[href^=/bbs/book_view.aspx]").attr("href"));
            result = httpClient.doGet(urls,headMap,null,false);
            System.out.println("一条记录自动顶成功。。。。");
        }
    }
    private String replaceUrl(String url){
        if(url.indexOf("http") <= -1){
            url = Home_Url + url;
        }
        return url;
    }
}
