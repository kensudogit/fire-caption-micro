package com.firecaptain.dataanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * データ分析マイクロサービス メインアプリケーション
 * 
 * 消防司令システムにおけるデータ蓄積・分析を担当するマイクロサービスです。
 * 
 * 機能:
 * - 定期自動分析
 * - パフォーマンス指標算出
 * - トレンド分析・予測モデリング
 * - レポート生成
 * 
 * 技術スタック:
 * - Spring Boot 3.2.0
 * - Spring Cloud Netflix Eureka（サービスディスカバリー）
 * - Spring Cloud OpenFeign（サービス間通信）
 * - Spring Cache（キャッシュ機能）
 * - Spring Async（非同期処理）
 * - Spring Scheduling（スケジューリング）
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
public class DataAnalysisApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(DataAnalysisApplication.class, args);
    }
}
