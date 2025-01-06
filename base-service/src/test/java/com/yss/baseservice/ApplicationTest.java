package com.yss.baseservice;

import com.alibaba.fastjson.JSON;
import com.yss.baseservice.domain.system.entity.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author yss
 * @date 2023/12/5
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    // 创建索引
    @Test
    public void testCrateIndex(){
        IndexOperations employeeIndex = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("sys_operation_log"));
        if (employeeIndex.exists()){
            log.info("索引已存在");
        }else {
            log.info("创建索引");
            employeeIndex.create();
        }
    }

    // 删除索引
    @Test
    public void testDeleteIndex(){
        IndexOperations employeeIndex = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("sys_operation_log"));
        employeeIndex.delete();
    }

    @Test
    public void testCrateIndex2(){
        IndexOperations userIndex = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("sys_operation_log"));
        userIndex.createMapping(OperationLog.class);

    }


    /**
     * 批量插入文档
     */
    @Test
    public void testInsertDocumentBatch(){
        List<OperationLog> userEntityList = new ArrayList<>();
        userEntityList.add(new OperationLog("1","模块1","1","admin",new Date(),1,"euryd123"));
        userEntityList.add(new OperationLog("2","模块2","2","admin",new Date(),1,"euryd123"));
        userEntityList.add(new OperationLog("3","模块3","1","admin",new Date(),1,"euryd123"));

        List<IndexQuery> indexQueries = new ArrayList<>();
        for (OperationLog userEntity : userEntityList) {
            // 封装id与数据
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(String.valueOf(userEntity.getLogId()));
            indexQuery.setSource(JSON.toJSONString(userEntity));

            indexQueries.add(indexQuery);
        }
        // 批量插入
        elasticsearchRestTemplate.bulkIndex(indexQueries, elasticsearchRestTemplate.getIndexCoordinatesFor(OperationLog.class));
    }

    @Test
    public void save(){
        OperationLog operationLog = new OperationLog("5", "模块5", "4", "admin", new Date(), 1, "euryd123");
        elasticsearchRestTemplate.save(operationLog,elasticsearchRestTemplate.getIndexCoordinatesFor(OperationLog.class));
    }

    /**
     * 通过id更新
     */
    @Test
    public void updateById(){
        Document document = Document.create();
        document.put("module","修改后的1");
        UpdateQuery updateQuery = UpdateQuery.builder("1").withDocument(document).build();
        elasticsearchRestTemplate.update(updateQuery, elasticsearchRestTemplate.getIndexCoordinatesFor(OperationLog.class));
    }

    /**
     * 通过id删除
     */
    @Test
    public void deleteById(){
        elasticsearchRestTemplate.delete("1", elasticsearchRestTemplate.getIndexCoordinatesFor(OperationLog.class));
    }

    /**
     * 根据条件删除
     */
    @Test
    public void deleteByQuery(){
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("module", "2");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        elasticsearchRestTemplate.delete(nativeSearchQuery,OperationLog.class, elasticsearchRestTemplate.getIndexCoordinatesFor(OperationLog.class));
    }

    /**
     * 根据id查询
     */
    @Test
    public void findById(){
        OperationLog operationLog = elasticsearchRestTemplate.get("3", OperationLog.class);
        System.out.println(operationLog.toString());
    }

    /**
     * 根据条件分页查询
     */
    @Test
    public void findByQurey(){
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("module", "模");
        //分页
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("logId")));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageRequest)
                .build();

        SearchHits<OperationLog> search = elasticsearchRestTemplate.search(nativeSearchQuery, OperationLog.class);
        for (SearchHit<OperationLog> operationLogSearchHit : search) {
            System.out.println(operationLogSearchHit.getContent());
        }
    }

    /**
     * 次数
     */
    @Test
    public void count(){
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("module", "3");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        long count = elasticsearchRestTemplate.count(nativeSearchQuery, OperationLog.class);
        System.out.println(count);
    }

    /**
     * 聚合查询
     */
    @Test
    public void aggregate(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms("term").field("businessType.keyword"));

        NativeSearchQuery searchQuery = queryBuilder.build();

        SearchHits<OperationLog> search = elasticsearchRestTemplate.search(searchQuery, OperationLog.class);
        Terms term = search.getAggregations().get("term");
        // 输出结果
        for (Terms.Bucket bucket : term.getBuckets()) {
            System.out.println("Module: " + bucket.getKeyAsString() + ", Count: " + bucket.getDocCount());
        }
    }


}
