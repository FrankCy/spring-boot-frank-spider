# IK 分析器
- - -
## 应用场景
采集的标题包含目标对象，但是通过字符串比较无法获取，我们可以通过分词器分词后再进行比较
例如：
```java
// 采集的字段
String title = "今天天气很不错！可以做什么呢？";
// 需要匹配的内容
String souceContent = "天气不错";
// 反例：通过字符串包含比较
if(title.contains(souceContent)) {
    // 这样是无法获取到的
    ...
    ...
}
// 通过分词工具会将"天气不错"改为"天气"和"不错"两个字段组成的List<String>，然后分别contains &&关系即可取到相匹配的内容
```

## 使用方式
### 引入maven
```xml
<!-- ikanalyzer 中文分词器  -->
<dependency>
    <groupId>com.janeluo</groupId>
    <artifactId>ikanalyzer</artifactId>
    <version>2012_u6</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.lucene</groupId>
            <artifactId>lucene-core</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.apache.lucene</groupId>
            <artifactId>lucene-queryparser</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.apache.lucene</groupId>
            <artifactId>lucene-analyzers-common</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--  lucene-queryparser 查询分析器模块 -->
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-queryparser</artifactId>
    <version>7.3.0</version>
</dependency>
```

### 编写工具类
```java
@Slf4j
public class IKAnalyzerSupport {

    /**
     * IK分词
     * @param target
     * @return
     */
    public static List<String> iKSegmenterToList(String target) throws Exception {
        if (StringUtils.isEmpty(target)){
            return Lists.newArrayList();
        }
        List<String> result = new ArrayList<>();
        StringReader sr = new StringReader(target);
        // 关闭智能分词 (对分词的精度影响较大)
        IKSegmenter ik = new IKSegmenter(sr, false);
        Lexeme lex;
        while((lex=ik.next())!=null) {
            String lexemeText = lex.getLexemeText();
            result.add(lexemeText);
        }

        //LOGGER.info("company:{}, iKSegmenterToList:{}", target, JSON.toJSON(result));
        return result;
    }

}
```
### 编写测试类
```java
public class IkAnalyzerDemo {

    public static void main(String[] args) {
        String str="天气不错";
        try {
            System.out.println(iKSegmenterToList(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
