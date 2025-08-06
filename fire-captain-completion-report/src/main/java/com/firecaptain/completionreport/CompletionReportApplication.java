package com.firecaptain.completionreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 完了報告マイクロサービス メインアプリケーション
 * 
 * 消防司令システムにおける終了報告の管理を担当するマイクロサービスです。
 * 
 * 機能:
 * - 事案完了の記録
 * - 詳細活動報告
 * - 被害・救助状況記録
 * - 教訓・改善点抽出
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
public class CompletionReportApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(CompletionReportApplication.class, args);
    }
}
