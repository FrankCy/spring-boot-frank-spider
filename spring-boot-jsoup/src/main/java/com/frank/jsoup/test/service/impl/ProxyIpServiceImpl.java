package com.frank.jsoup.test.service.impl;

import com.frank.jsoup.test.constant.CommonStatusEnum;
import com.frank.jsoup.test.quarty.ProxyIpQuery;
import com.frank.jsoup.test.service.ProxyIpService;
import com.frank.jsoup.test.util.CommonConstant;
import com.frank.jsoup.test.util.PageList;
import com.frank.jsoup.test.vo.ProxyIpVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author cy
 * @version ProxyIpServiceImpl.java, v 0.1 2020年06月23日 13:52 cy Exp $
 */
@Slf4j
@Service("proxyIpService")
public class ProxyIpServiceImpl implements ProxyIpService {

    private static final int MAX_PAGE_SIZE = 10;

    @Override
    public void checkProxyIp() {
        ProxyIpQuery proxyQuery = new ProxyIpQuery();
        int pageNo = CommonConstant.DEFAULT_QUERY_NO;
        proxyQuery.setPageNo(pageNo);

        PageList<ProxyIpVO> pageList = this.listByParam(proxyQuery, CommonConstant.DEFAULT_QUERY_PAGE_SIZE);
        while (pageList.getHasMore()) {
            List<ProxyIpVO> proxyIpVOList = pageList.getData();
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                boolean enable = this.checkEnableProxyIp(proxyIpVO);
                proxyIpVO.setGmtChecked(new Date());
                proxyIpVO.setStatusEnum(enable ? CommonStatusEnum.VALID.getCode() : CommonStatusEnum.INVALID.getCode());
                this.update(proxyIpVO);
            }
            proxyQuery.setPageNo(pageNo++);
            pageList = this.listByParam(proxyQuery, CommonConstant.DEFAULT_QUERY_PAGE_SIZE);
        }
        List<ProxyIpVO> proxyIpVOList = pageList.getData();
        if (CollectionUtils.isNotEmpty(proxyIpVOList)) {
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                boolean enable = this.checkEnableProxyIp(proxyIpVO);
                proxyIpVO.setGmtChecked(new Date());
                proxyIpVO.setStatusEnum(enable ? CommonStatusEnum.VALID.getCode() : CommonStatusEnum.INVALID.getCode());
                this.update(proxyIpVO);
            }
        }
    }

    @Override
    public void crawProxyIp() {
        for (int pageNo = 1; pageNo <= MAX_PAGE_SIZE; pageNo++) {
            List<ProxyIpVO> proxyIpVOList = this.crawlProxyIpListByPageNo(pageNo);
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                this.create(proxyIpVO);
            }
        }
    }

    /**
     * ？？？？
     *
     */
    private static List<ProxyIpVO> crawlProxyIpListByPageNo(int pageNo) {
        return null;
    }

    /**
     * ？？？？
     */
    private static PageList<ProxyIpVO> listByParam(ProxyIpQuery proxyQuery, int pageSize) {
        return null;
    }

    /**
     * ？？？？
     * @param proxyIpVO
     */
    private static boolean checkEnableProxyIp(ProxyIpVO proxyIpVO) {
        return false;
    }

    /**
     * ？？？？
     * @param proxyIpVO
     */
    private static void create(ProxyIpVO proxyIpVO) {
    }

    /**
     * ？？？？
     * @param proxyIpVO
     */
    private static void update(ProxyIpVO proxyIpVO) {
    }
}
