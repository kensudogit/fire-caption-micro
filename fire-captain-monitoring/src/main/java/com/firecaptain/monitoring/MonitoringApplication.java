package com.firecaptain.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 監視マイクロサービス メインアプリケーション
 * 
 * 消防司令システムの監視・可視化を担当するマイクロサービスです。
 * 
 * 機能:
 * - システム監視
 * - メトリクス収集
 * - アラート管理
 * - ダッシュボード
 * 
 * 技術スタック:
 * - Spring Boot 3.2.0
 * - Spring Cloud Netflix Eureka（サービスディスカバリー）
 * - Spring Cloud OpenFeign（サービス間通信）
 * - Spring Cache（キャッシュ機能）
 * - Spring Async（非同期処理）
 * - Spring Scheduling（スケジューリング）
 * - Micrometer（メトリクス）
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableAsync
@EnableScheduling
public class MonitoringApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }
}
