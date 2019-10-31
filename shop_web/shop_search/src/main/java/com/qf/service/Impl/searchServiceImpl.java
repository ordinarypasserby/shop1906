package com.qf.service.Impl;

import com.github.pagehelper.PageInfo;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DingYuHui
 * @Date 2019/10/15
 */
@Service
public class searchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加商品到索引库
     * @param goods
     * @return
     */
    @Override
    public boolean insert(Goods goods) {

        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.addField("id", goods.getId() + "");
        solrDocument.addField("subject", goods.getSubject());
        solrDocument.addField("info", goods.getInfo());
        solrDocument.addField("price", goods.getPrice().doubleValue());
        solrDocument.addField("save",goods.getSave());
        solrDocument.addField("images", goods.getFengmian());

        //添加到搜索库
        try {
            solrClient.add(solrDocument);
            solrClient.commit();

            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //根据关键字搜索索引库
    @Override
    public List<Goods> query(String keyword,Page page) {
        System.out.println("开始进行搜索：" + keyword);

        SolrQuery solrQuery = new SolrQuery();
        if(keyword == ""){
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery("subject:" + keyword + " || info:" + keyword);
        }

        //进行分页的设置
        //limit ?,?
        System.out.println("设置的start："+page.getStart()+"+"+"设置的rows："+page.getRows());
        solrQuery.setStart(page.getStart());
        solrQuery.setRows(page.getRows());

        //进行高亮的设置
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>");//设置高亮的前缀
        solrQuery.setHighlightSimplePost("</font>");//设置高亮的后缀
        solrQuery.addHighlightField("subject");//添加会高亮的字段
//        solrQuery.setHighlightFragsize(13);//高亮折叠的大小
//        solrQuery.setHighlightSnippets(2);//高亮折叠的次数 //..javaxxxxxxxxxxx..xxxxxJavaxxxx.


        //开始搜索
        try {
            QueryResponse response = solrClient.query(solrQuery);

            //获得高亮的结果
            /*
            Map
                1 -> Map
                      subject -> List(滚筒<>洗衣机</>)
                      info -> List(xxxxx<>xxx</>)
             */
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //结果列表
            SolrDocumentList documents = response.getResults();

            //获得分页的总条数
            long numFound = documents.getNumFound();

            List<Goods> goodsList = new ArrayList<>();
            for (SolrDocument document : documents) {
                Goods goods = new Goods();

                goods.setId(Integer.parseInt(document.getFieldValue("id") + ""));
                goods.setSubject(document.getFieldValue("subject") + "");
                goods.setPrice(BigDecimal.valueOf((double)document.getFieldValue("price")));
                goods.setSave((int)document.getFieldValue("save"));
                goods.setFengmian((String)document.getFieldValue("images"));

                //处理高亮
                if(highlighting.containsKey(goods.getId() + "")){
                    //获得当前id商品对应的高亮信息
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    //获得标题的高亮
                    if(stringListMap.containsKey("subject")){
                        //获得标题的高亮
                        String subject = stringListMap.get("subject").get(0);
                        goods.setSubject(subject);//将有高亮信息的标题放入商品对象中
                    }
                }

                goodsList.add(goods);
            }

            return goodsList;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 根据关键字搜索索引库，并分页
     * @param page
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<Goods> query(Page page, String keyword) {

        return null;
    }
}
