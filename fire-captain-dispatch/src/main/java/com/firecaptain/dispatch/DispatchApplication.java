package com.firecaptain.dispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 指令マイクロサービス メインアプリケーション
 * 
 * 消防司令システムにおける指令・部隊出動の管理を担当するマイクロサービスです。
 * 
 * 機能:
 * - 通報からの指令生成
 * - 最適部隊選定
 * - 出動指令の発令
 * - 到着予想時間計算
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
public class DispatchApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(DispatchApplication.class, args);
    }
}
