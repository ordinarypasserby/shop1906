package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @RequestMapping("/keyword")
    public String searchByKeywork(Page page,String keyword, Model model){
        System.out.println(page.getRows() + "+"+ page.getStart());

        List<Goods> goods = searchService.query(keyword,page);
        model.addAttribute("goodList",goods);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        return "searchList";
    }


    /**
     * 添加商品到索引库
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertSolr(@RequestBody Goods goods){
        return searchService.insert(goods);

    }
}
