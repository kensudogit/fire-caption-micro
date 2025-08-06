# マイクロサービスアーキテクチャ パフォーマンス最適化

## 概要

消防司令システムのマイクロサービスアーキテクチャにおけるパフォーマンス最適化戦略と実装について説明します。

## 最適化戦略

### 1. スレッドプール最適化

#### 非同期処理用スレッドプール（asyncExecutor）
```java
/**
 * 非同期処理用スレッドプール
 * 
 * 一般的な非同期処理（メール送信、ログ処理、通知など）に使用
 * - コアプールサイズ: 10（常時稼働スレッド数）
 * - 最大プールサイズ: 50（最大スレッド数）
 * - キュー容量: 500（待機タスク数）
 */
@Bean(name = "asyncExecutor")
public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("Async-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
}
```

#### データベース処理用スレッドプール（databaseExecutor）
```java
/**
 * データベース処理用スレッドプール
 * 
 * データベース関連の重い処理（バッチ処理、大量データ処理など）に使用
 * - コアプールサイズ: 5（常時稼働スレッド数）
 * - 最大プールサイズ: 20（最大スレッド数）
 * - キュー容量: 200（待機タスク数）
 */
@Bean(name = "databaseExecutor")
public Executor databaseExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(200);
    executor.setThreadNamePrefix("DB-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
}
```

### 2. データベース最適化

#### HikariCPコネクションプール設定
```java
/**
 * データベース設定クラス
 * 
 * マイクロサービス全体のデータベース接続とパフォーマンス最適化設定を提供します。
 * - HikariCPコネクションプールの最適化
 * - Hibernateのパフォーマンス設定
 * - キャッシュ設定
 * - トランザクション管理
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = dataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        
        // パフォーマンス最適化設定
        dataSource.setMaximumPoolSize(50);
        dataSource.setMinimumIdle(10);
        dataSource.setConnectionTimeout(20000);
        dataSource.setIdleTimeout(300000);
        dataSource.setMaxLifetime(1200000);
        dataSource.setLeakDetectionThreshold(30000);
        
        // PostgreSQL最適化設定
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource.addDataSourceProperty("useLocalSessionState", "true");
        dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
        dataSource.addDataSourceProperty("cacheResultSetMetadata", "true");
        dataSource.addDataSourceProperty("cacheServerConfiguration", "true");
        dataSource.addDataSourceProperty("elideSetAutoCommits", "true");
        dataSource.addDataSourceProperty("maintainTimeStats", "false");
        
        return dataSource;
    }
}
```

#### Hibernate最適化設定
```java
Properties properties = new Properties();
properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
properties.setProperty("hibernate.show_sql", "false");
properties.setProperty("hibernate.format_sql", "false");
properties.setProperty("hibernate.hbm2ddl.auto", "validate");

// パフォーマンス最適化設定
properties.setProperty("hibernate.jdbc.batch_size", "50");
properties.setProperty("hibernate.order_inserts", "true");
properties.setProperty("hibernate.order_updates", "true");
properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
properties.setProperty("hibernate.jdbc.batch.builder", "legacy");

// キャッシュ設定
properties.setProperty("hibernate.cache.use_second_level_cache", "true");
properties.setProperty("hibernate.cache.use_query_cache", "true");
properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");
properties.setProperty("hibernate.jcache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
properties.setProperty("hibernate.jcache.uri", "classpath:ehcache.xml");

// 統計情報
properties.setProperty("hibernate.generate_statistics", "true");
properties.setProperty("hibernate.cache.use_structured_entries", "true");
```

### 3. キャッシュ戦略

#### EhCache設定
```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.ehcache.org/v3"
        xsi:schemaLocation="http://www.ehcache.org/v3 http://www.ehcache.org/schema/ehcache-core-3.0.xsd">

    <!-- 通報データキャッシュ -->
    <cache alias="emergencyReports">
        <resources>
            <heap unit="entries">500</heap>
            <offheap unit="MB">50</offheap>
        </resources>
        <expiry>
            <ttl unit="minutes">15</ttl>
        </expiry>
        <heap-store-settings>
            <max-object-size unit="kB">5</max-object-size>
        </heap-store-settings>
    </cache>

    <!-- 指令データキャッシュ -->
    <cache alias="dispatches">
        <resources>
            <heap unit="entries">300</heap>
            <offheap unit="MB">30</offheap>
        </resources>
        <expiry>
            <ttl unit="minutes">20</ttl>
        </expiry>
        <heap-store-settings>
            <max-object-size unit="kB">8</max-object-size>
        </heap-store-settings>
    </cache>

    <!-- 統計データキャッシュ -->
    <cache alias="statistics">
        <resources>
            <heap unit="entries">50</heap>
            <offheap unit="MB">10</offheap>
        </resources>
        <expiry>
            <ttl unit="minutes">10</ttl>
        </expiry>
        <heap-store-settings>
            <max-object-size unit="kB">3</max-object-size>
        </heap-store-settings>
    </cache>
</config>
```

### 4. サービス間通信最適化

#### OpenFeign設定
```java
@FeignClient(
    name = "fire-captain-dispatch",
    url = "${fire-captain.dispatch.url:}",
    configuration = FeignConfiguration.class
)
public interface DispatchClient {

    @PostMapping("/api/dispatches")
    DispatchDto createDispatch(@RequestBody CreateDispatchRequest request);

    @GetMapping("/api/dispatches/{dispatchNumber}")
    DispatchDto getDispatch(@PathVariable String dispatchNumber);
}
```

#### Circuit Breaker設定
```yaml
resilience4j:
  circuitbreaker:
    instances:
      emergency-report-circuit-breaker:
        sliding-window-size: 10
        minimum-number-of-calls: 5
        permitted-number-of-calls-in-half-open-state: 3
        automatic-transition-from-open-to-half-open-enabled: true
        wait-duration-in-open-state: 5s
        failure-rate-threshold: 50
        event-consumer-buffer-size: 10
```

### 5. API Gateway最適化

#### ルーティング設定
```yaml
spring:
  cloud:
    gateway:
      routes:
        # Emergency Report Service
        - id: emergency-report-service
          uri: lb://fire-captain-emergency-report
          predicates:
            - Path=/api/emergency-reports/**
          filters:
            - name: CircuitBreaker
              args:
                name: emergency-report-circuit-breaker
                fallbackUri: forward:/fallback/emergency-report
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
            - name: Retry
              args:
                retries: 3
                statuses: BAD_GATEWAY,INTERNAL_SERVER_ERROR
                methods: GET,POST,PUT,DELETE
```

#### レート制限設定
```yaml
fire-captain:
  gateway:
    rate-limiting:
      enabled: true
      default-rate: 100
      burst-capacity: 200
```

### 6. 監視・メトリクス

#### Micrometer設定
```java
@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
```

#### カスタムメトリクス
```java
@Component
public class PerformanceMonitoringService {

    private final Counter emergencyReportCounter;
    private final Timer responseTimeTimer;
    private final Gauge cacheHitRateGauge;

    public PerformanceMonitoringService(MeterRegistry meterRegistry) {
        this.emergencyReportCounter = Counter.builder("fire_captain_emergency_reports_total")
                .description("Total number of emergency reports")
                .register(meterRegistry);
        
        this.responseTimeTimer = Timer.builder("fire_captain_response_time")
                .description("Response time for emergency operations")
                .register(meterRegistry);
        
        this.cacheHitRateGauge = Gauge.builder("fire_captain_cache_hit_rate")
                .description("Cache hit rate")
                .register(meterRegistry, this, PerformanceMonitoringService::getCacheHitRate);
    }
}
```

## パフォーマンス指標

### 目標値
- **レスポンス時間**: 平均 < 200ms、95パーセンタイル < 500ms
- **スループット**: 1000 req/sec以上
- **エラー率**: < 0.1%
- **キャッシュヒット率**: > 80%
- **データベース接続プール使用率**: < 70%

### 監視項目
- **JVM メトリクス**: ヒープ使用量、GC時間、スレッド数
- **データベース メトリクス**: 接続数、クエリ実行時間、デッドロック
- **キャッシュ メトリクス**: ヒット率、サイズ、TTL
- **ネットワーク メトリクス**: レイテンシ、スループット、エラー率

## 最適化効果

### 期待される改善
1. **レスポンス時間**: 30-50%の短縮
2. **スループット**: 2-3倍の向上
3. **リソース使用効率**: 20-30%の改善
4. **可用性**: 99.9%以上の稼働率
5. **スケーラビリティ**: 水平スケーリングによる負荷分散

### 実装済み最適化
- ✅ スレッドプール最適化
- ✅ データベース接続プール最適化
- ✅ Hibernate設定最適化
- ✅ キャッシュ戦略実装
- ✅ サーキットブレーカー実装
- ✅ レート制限実装
- ✅ メトリクス収集実装

### 今後の最適化計画
- 🔄 データベースインデックス最適化
- 🔄 クエリ最適化
- 🔄 非同期処理の拡張
- 🔄 分散キャッシュの導入
- 🔄 サービスメッシュの導入

このパフォーマンス最適化により、消防司令システムのマイクロサービスアーキテクチャは高負荷環境でも安定した性能を発揮し、緊急時の迅速な対応を可能にします。
