package com.sbnz.SIEMCenter2.service;

import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
				//ukoliko je prazno polje pretrazi sve
				queryParams[i] = ".*";
			}
		}
		
		BoolQueryBuilder query = QueryBuilders.boolQuery()
				.must(QueryBuilders.regexpQuery("message", queryParams[0]))
				.must(QueryBuilders.regexpQuery("category", queryParams[1]))
				.must(QueryBuilders.regexpQuery("informationSystemType", queryParams[2]))
				.must(QueryBuilders.regexpQuery("logLevel",queryParams[3]))
				.must(QueryBuilders.regexpQuery("ipAddress", queryParams[4]))
				.must(QueryBuilders.regexpQuery("userId", queryParams[5]))
				.must(QueryBuilders.regexpQuery("machineId", queryParams[6]))

				;

		NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
		List<LogEntry> logs = elasticsearchTemplate.queryForList(build, LogEntry.class);
		return logs;
	}
}
