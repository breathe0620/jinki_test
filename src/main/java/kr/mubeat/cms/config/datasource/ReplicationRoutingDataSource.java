package kr.mubeat.cms.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Created by moonkyu.lee on 2017. 7. 3..
 *
 * 1. AbstractRoutingDataSource 는 Spring 기본 클래스며, 여러개의 데이터 소스를 하나로 묶고 자동으로 분기 처리를 해주는 역할을 담당하고 있다
 * 2. TransactionSynchronizationmanager 는 @Transactional 의 readOnly 값을 읽어 처리 하려는 용도로 사용한다
 * 3. Spring 은 @Trnsactional 을 만나면
 * Transactionmanger 선별 -> DataSource 에서 Connection 획득 -> Transaction 동기화
 * 의 순서로 일을 한다
 *
 * 단 이 순서로 작동하면 문제가 있다
 * 문제 해결을 위하여 WithRoutingDataSourceConfig 의 LazyConnectionDataSourceProxy 을 사용하여
 * TransactionManager 선별 -> LazyConnectionDataSourceProxy 에서 Connection Proxy 객체 획득 -> Transaction 동기화 ->
 * 실제 쿼리 호출시에 ReplicationRoutingDataSource.getConnection() // determineCurrentLookupKey() 를 호출하여
 * 지정해둔 DataSource 를 얻어온다
 *
 *
 * 결론
 *
 * 핵심은 두가지다
 *
 * 1. DataSource 를 가져오기 위해서 AbstractRoutingDataSource 가 작동하고
 * 오버라이드 하여 determineCurrentLookupKye 를 통하여 트랜잭션의 readOnly 값을 통하여 dataSource 를 선택한다
 * 2. 그 경우 동작순서에 의하여 먼저 컨넥션을 가져오는 문제가 발생 하는데, LazyConnectionDataSourceProxy 를 통하여
 * SQL이 만들어 진 다음 컨넥션을 가져와서 동작하도록 하는 것이 목표다
 *
 *
 * 참고
 * http://egloos.zum.com/kwon37xi/v/5364167   // 설명이 잘 되어 있음
 * http://fedulov.website/2015/10/14/dynamic-datasource-routing-with-spring/   // 영문 페이지
 */
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "read" : "write";
//        logger.info("current dataSourceType : {}", dataSourceType);
        return dataSourceType;
    }
}
