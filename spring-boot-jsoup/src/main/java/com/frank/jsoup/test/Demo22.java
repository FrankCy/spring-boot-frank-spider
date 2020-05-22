package com.frank.jsoup.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.frank.jsoup.test.util.HttpClientUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 *
 * @author cy
 * @version $Id: Demo22.java, v 0.1 2020年05月15日 18:34 cy Exp $
 */
public class Demo22 {

    // 平台搜索地址
    private static String PLATFORM_SEARCH_URL = "https://search.jd.com/Search?keyword=";

    // 关键字
    private static String KEYWORD = "小黑瓶";

    // 规格大小（获取50ml的）
    private static String SPECIFICATIONS = "50ml";

    public static void jdSkuSpu() {

        try {
            WebClient webClient = HtmlUnitUtil.getWebClient(true, false, false);
            //String encodeKeyword = URLEncoder.encode(KEYWORD, "gb2312");
            String url = PLATFORM_SEARCH_URL + KEYWORD;
            HtmlPage htmlPage = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(3000);
            Document platformShopsListDocument = Jsoup.parse(htmlPage.asXml());

            Elements goodsElements = platformShopsListDocument.select("#J_goodsList > ul > li");
            System.out.println("抓取到商品的总数 ： " + goodsElements.size());
            for(Element element : goodsElements) {
                String dataSku = element.attr("data-sku");
                String dataSpu = element.attr("data-spu");
                if(!dataSku.equals(dataSpu)) {
                    System.out.println("SKU与SPU不同，新增SPU，再新增SKU，建立关系");
                    System.out.println("SKU:" + dataSku  + " ----------- SPU: " + dataSpu);
                } else {
                    System.out.println("SKU:" + dataSku  + " ----------- SPU: " + dataSpu);
                }

                // 拼接商品详情地址
                String goodsDetailUrl = "https://item.jd.com/"+ dataSku +".html";

                // 获取详情Document
                HtmlPage goodsDetailHtmlPage = webClient.getPage(goodsDetailUrl);
                webClient.waitForBackgroundJavaScript(3000);
                Document detailDocument = Jsoup.parse(goodsDetailHtmlPage.asXml());

                // 获取商品头信息
                Elements goodsHeader = detailDocument.select("#choose-attr-1 > div.dd > div.item");
                System.out.println("goodsHeaders size : " + goodsHeader.size());
                int goodsHeadersCount = goodsHeader.size();
                if(goodsHeadersCount > 0) {
                    // 组合套餐
                    for(Element goods : goodsHeader) {
                        String sku = goods.attr("data-sku");
                        String goodsName = goods.attr("data-value");
                        System.out.println("goodsName-------- " + goodsName);
                        System.out.println(KEYWORD);
                        System.out.println(SPECIFICATIONS);
                        if(goodsName.contains(KEYWORD) && goodsName.contains(SPECIFICATIONS)) {
                            System.out.println("sku{"+ sku + "}    goodsName{"+ goodsName+"}");
                            // 通过sku获取价格、评论信息、详情图片
                            JSONArray priceJsonArray = getPrice(sku);
                            JSONObject priceJson = priceJsonArray.getJSONObject(0);
                            String price = priceJson.getString("p");
                            System.out.println("售价：" + price);
                            JSONObject commentJson = getComment(sku);
                            JSONObject imggJson = getDetailImgs(sku, dataSpu);
                        }
                    }
                } else {
                    // 获取sku（详情中没有选项卡的）
                    String sku = detailDocument.select("div.dd > a").attr("data-sku");
                    // 单个商品
                    // 直接通过sku获取价格、评论信息、详情图片
                    // 通过sku获取价格、评论信息、详情图片
                    JSONArray priceJsonArray = getPrice(sku);
                    JSONObject priceJson = priceJsonArray.getJSONObject(0);
                    String price = priceJson.getString("p");
                    System.out.println("我是单个商品的售价：" + price);
                    JSONObject commentJson = getComment(dataSku);
                    JSONObject imggJson = getDetailImgs(dataSku, dataSpu);
                }
                //System.out.println("goodsHeaders html : " + goodsHeader.html());

                // 获取商品详情信息（代理，非Css、js）
                Elements goodsDetail = detailDocument.select("#detail > div.tab-con > div:nth-child(1) > div.p-parameter > ul.parameter2.p-parameter-list > li");
                //System.out.println("goodsDetail size : " + goodsDetail.size());
                //System.out.println("goodsDetail html : " + goodsDetail.html());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取价格
     * @param sku
     * @return
     */
    public static JSONArray getPrice(String sku) {
        // 通过HttpClientUtil获取价格
        String priceUrl = "https://p.3.cn/prices/mgets?skuIds="+sku;
        // 发送请求Get
        String result = HttpClientUtil.get(priceUrl, true, "UTF-8");
        System.out.println("get Price info ------ " + result);
        if(result == null) {
            // 重试
            return null;
        }
        JSONArray priceJsonArray = JSONArray.parseArray(result);
        return priceJsonArray;
    }

    /**
     * 获取评论信息
     * @param sku
     * @return
     */
    public static JSONObject getComment(String sku) {
        JSONObject jsonObject = new JSONObject();

        int page = 0;
        boolean flag = true;
        while (flag) {
            // 通过HttpClientUtil获取评论
            String commentUrl = "https://club.jd.com/comment/productPageComments.action?productId="+ sku +"&score=0&sortType=5&page="+ page +"&pageSize=10";
            System.out.println("评论地址 ： " + commentUrl);
            // 分页信息要写递归，并获取"comments"，如果没有即到底页
            String result = HttpClientUtil.get(commentUrl, true, "UTF-8");
            //System.out.println("\n \n \n 结果集：" + result + "\n \n \n");
            JSONObject commentJson = JSONObject.parseObject(result);
            if(commentJson == null) {
                flag = false;
                System.out.println("评论获取失败！");
                continue;
            }

            if(commentJson.containsKey("comments")) {
                System.out.println("包含评论，并且评论有值");
                // 包含评论，并且评论有值
                // 获取评论（JSONArray）
                JSONArray commentsJsonArray = commentJson.getJSONArray("comments");
                System.out.println("\n " + commentsJsonArray.toJSONString() + "\n");
                for(int i=0; i<commentsJsonArray.size(); i++) {
                    JSONObject commentsJson = commentsJsonArray.getJSONObject(i);
                    String content = commentsJson.getString("content");
                    System.out.println("评论信息 ： " + content);
                    //String creationTime = commentsJson.getString("creationTime");
                    //System.out.println("评论时间 ： " + creationTime);
                    String score = commentsJson.getString("score");
                    System.out.println("评分 ： " + score);
                    String imageCount = commentsJson.getString("imageCount");
                    System.out.println("评论上传图片数量 ： " + imageCount);
                    JSONArray imagesJsonArray = commentsJson.getJSONArray("images");
                    if(imagesJsonArray != null && imagesJsonArray.size() > 0) {
                        for(int j=0; j<imagesJsonArray.size(); j++) {
                            JSONObject imagesJson = imagesJsonArray.getJSONObject(j);
                            String status = imagesJson.getString("status");
                            if("0".equals(status)) {
                                // 判断0是有效的图
                                String imgUrl = imagesJson.getString("imgUrl");
                                System.out.println("评论图片地址 ： " + imgUrl);
                            }
                        }
                    }
                }
            } else {
                System.out.println("NO 包含评论，并且评论有值");
                // 没有评论了，但是有统计数据（评论总数，星数，默认评价数）
                JSONObject productCommentSummaryJson = commentJson.getJSONObject("productCommentSummary");
                System.out.println("skuId : " + productCommentSummaryJson.getString("skuId"));
                System.out.println("用户名 : " + productCommentSummaryJson.getString("nickname"));
                System.out.println("商品参考名 : " + productCommentSummaryJson.getString("referenceName"));
                System.out.println("总星数 : " + productCommentSummaryJson.getString("averageScore"));
                System.out.println("默认评价 : " + productCommentSummaryJson.getString("defaultGoodCount"));
                System.out.println("默认评价描述 : " + productCommentSummaryJson.getString("defaultGoodCountStr"));
                System.out.println("总评数量 : " + productCommentSummaryJson.getString("commentCount"));
                System.out.println("总评数量描述 : " + productCommentSummaryJson.getString("commentCountStr"));
                System.out.println("好评数 : " + productCommentSummaryJson.getString("goodCount"));
                System.out.println("好评度 : " + productCommentSummaryJson.getString("goodRateShow"));
                flag = false;
            }
            page++;
        }

        return jsonObject;
    }

    /**
     * 获取详情图片
     * @param skuId
     * @param spuId
     * @return
     */
    public static JSONObject getDetailImgs(String skuId, String spuId) {
        System.out.println("getDetailImgs ------------ \n " + skuId + "-----" + spuId );
        if(StringUtils.isEmpty(spuId)) {
            spuId = skuId;
        }
        // 通过HttpClientUtil获取详情图片
        String detailImgsUrl = "https://cd.jd.com/description/channel?skuId="+ skuId +"&mainSkuId="+ spuId +"&charset=utf-8&cdn=2&callback=showdesc";
        String result = HttpClientUtil.get(detailImgsUrl, true, "UTF-8");
        result = result.replace("showdesc(", "");
        result = result.substring(0, result.length() - 1);
        return JSONObject.parseObject(result);
    }

    public static void main(String[] args) {
        jdSkuSpu();
    }

}
