package com.springboot.cli.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.cli.entity.User;
import com.springboot.cli.service.EsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * @author ding
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class EsUserServiceImpl implements EsUserService {

    private final RestHighLevelClient restHighLevelClient;

    private static final String USER_INDEX = "user_index";

    @Override
    public void createIndex() throws IOException {
        if (this.indexExists("test_index")) {
            return;
        }
        // 2 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test_index");
        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("创建成功，创建的索引名为：{}", response.index());
    }

    @Override
    public boolean indexExists(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean indexExists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (indexExists) {
            log.warn("存在索引");
            return true;
        }
        return false;
    }

    @Override
    public void getIndex() throws IOException {
        // 1、构建 获取test_index索引的请求
        GetIndexRequest request = new GetIndexRequest("test_index");
        // 2、客户端判断该索引是否存在
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.info("该索引是否存在：{}", exists);
    }


    @Override
    public void createUser(User user) throws IOException {
        // 构建请求,创建索引
        IndexRequest request = new IndexRequest(USER_INDEX);
        request.id(String.valueOf(user.getUserId()));
        request.timeout(TimeValue.timeValueSeconds(5));
        // 保存的数据
        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 客户端发送请求,获取响应结果
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("响应结果：{}", response.toString());
    }

    @Override
    public void createUserBulk(List<User> userList) throws IOException {
        // 构建批量插入的请求
        BulkRequest request = new BulkRequest();
        //设置超时时间
        request.timeout("10s");
        // 批量插入请求设置
        userList.forEach(user -> request.add(
                new IndexRequest("user_index")
                        .id(String.valueOf(user.getUserId()))
                        .source(JSONObject.toJSONString(user), XContentType.JSON)
        ));
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        log.info("批量插入结果：{}", !response.hasFailures());
    }

    @Override
    public User getUser(long userId) throws IOException {
        // 获取id为1的文档的信息
        GetRequest request = new GetRequest(USER_INDEX, String.valueOf(userId));
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        // 如果存在，获取文档信息
        if (exists) {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            return JSON.parseObject(response.getSourceAsString(), User.class);
        }
        return null;
    }

    @Override
    public ArrayList<User> searchUser() throws IOException {
        // 构建搜索请求
        SearchRequest request = new SearchRequest(USER_INDEX);
        // 设置搜索条件，使用该构建器进行查询
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // QueryBuilders.termQuery()：精确匹配
        // QueryBuilders.matchAllQuery()：全文匹配
        // 构建精确匹配查询条件
        // TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("username.keyword", "李四");
        // MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // 模糊匹配
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("username", "张");
        builder.query(wildcardQueryBuilder);
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<User> users = new ArrayList<>();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            users.add(JSON.parseObject(hit.getSourceAsString(), User.class));
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws IOException {
        // 通过索引更新
        UpdateRequest request = new UpdateRequest(USER_INDEX, String.valueOf(user.getUserId()));
        request.doc(JSONObject.toJSONString(user), XContentType.JSON);
        // 客户端执行更新请求
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        log.info("更新状态：{}", response.status());
    }

    @Override
    public void deleteUser(long userId) throws IOException {
        DeleteRequest request = new DeleteRequest(USER_INDEX, String.valueOf(userId));
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        log.info("删除状态：{}", response.status());
    }


}
