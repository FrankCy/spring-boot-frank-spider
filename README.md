# Spring Boot Frank Spider 电商爬虫

## ```此项目只为研究各个工具的优劣，并不支持商用（...商用好像没法用，代码太烂了）```
 - - -
|项目|文件|描述|
|:--|:--|:--|
|spring-boot-jsoup||Java爬虫框架【jsoup】Demo|
||Demo1|Jsoup HelloWorld|
||Demo2|Jsoup 查找DOM元素|
||Demo3|Jsoup 使用选择器语法查找DOM元素|
||Demo4|Jsoup 获取DOM元素属性值|
||Demo5|Jsoup 实现爬天猫商城列表（无HttpClient）|
||Demo6|Jsoup 代理方式访问网站（无HttpClient）|
||Demo7|基于JsoupUtil，模拟浏览器渲染后，抓取通过js加载的信息（抓取商品详情时使用）|
||Demo8|基于JsoupUtil，模拟浏览器渲染后，抓取通过js加载的信息（抓取商品详情时使用），返回Elements|
||Demo9|爬取通过js加载的店铺详情信息|
||Demo10|爬取某药商品列表|
||Demo11|爬取某猫详情搜索页，辨别实际请求地址，获取真实商品列表信息|
||Demo12|Selenium Demo，需要本地先下载chromedriver|
||Demo13|爬取丝芙兰数据|
||Demo14|爬取考la店铺列表|
||Demo15|根据商品关键字爬取考la列表 -> 获取店铺 -> 进入店铺搜索 -> 获取店铺商品 -> 进入详情|
||Demo16|模拟百度输入参数点击事件|
||Demo17|模拟考la爬详情（包含商品头、商品评论、商品详情） ChromeDriver实现|
||Demo18|模拟爬Jd（平台搜索列表，获取店铺信息，店铺内搜索结果）|
||Demo19|模拟考la爬详情 HtmlUnit实现|
||Demo20|模拟爬Jd详情（评论、图片等）|
||Demo21|模拟获取Jd详情|
||Demo22|模拟获取Jd SKU、SPU爬取；价格获取；图片获取；评论获取；|
||Demo23|模拟获取考拉颜色信息，并迭代颜色（多次执行会出现反爬，不返回所需要的信息，或者无法切换颜色选项卡）|
||Demo24|模拟获取丝芙兰商品信息|
|spring-boot-webmagic||Java爬虫框架【webmagic】Demo|

## 整理
爬取目标：京东、考拉、丝芙兰
使用工具：HtmlUnit（单线程，大部分网站通过代理可以获取，但是反爬多层JS的无法取到）、ChromeDriver（多进程，需要考虑销毁机制）等（其它的不咋好用）









