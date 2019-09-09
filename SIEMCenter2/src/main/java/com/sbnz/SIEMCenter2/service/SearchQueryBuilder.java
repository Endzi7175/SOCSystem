package com.sbnz.SIEMCenter2.service;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import com.sbnz.SIEMCenter2.model.LogEntry;

@Component
public class SearchQueryBuilder {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	public List<LogEntry> getAll(String queryParams[]){
		for (int i = 0; i < queryParams.length; i++){
			if (queryParams[i].equals("")){
				
				queryParams[i] = ".*";
			}
			System.out.println(queryParams[i] + "a");
		}
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
			for (String a : queryParams[0].split(" ")){
				query.must(QueryBuilders.regexpQuery("message", a));
			}
			for (String a : queryParams[1].split(" ")){
				System.out.println("usao");
				query.must(QueryBuilders.regexpQuery("category", a));
			}
			for (String a : queryParams[2].split(" ")){
				query.must(QueryBuilders.regexpQuery("informationSystemType", a));
			}
			for (String a : queryParams[3].split(" ")){
				query.must(QueryBuilders.regexpQuery("logLevel", a));
			}
			for (String a : queryParams[4].split(" ")){
				query.must(QueryBuilders.regexpQuery("ipAddress", a));
			}
			for (String a : queryParams[5].split(" ")){
				query.must(QueryBuilders.regexpQuery("userId", a));
			}
			for (String a : queryParams[6].split(" ")){
				query.must(QueryBuilders.regexpQuery("machineId", a));
			}
	
		
		PageRequest page = PageRequest.of(1, 10);
		NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
		List<LogEntry> logs = elasticsearchTemplate.queryForList(build, LogEntry.class);
		System.out.println(logs.size());
		return logs;
	}
}
