package com.firecaptain.scenesupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 現場支援マイクロサービス メインアプリケーション
 * 
 * 消防司令システムにおける現場支援の管理を担当するマイクロサービスです。
 * 
 * 機能:
 * - 自動支援要求判定
 * - 支援タイプ決定
 * - 支援の承認・出動・完了管理
 * - コスト追跡
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
public class SceneSupportApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(SceneSupportApplication.class, args);
    }
}
