package com.firecaptain.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 設定サーバー メインアプリケーション
 * 
 * 消防司令システムのマイクロサービス全体の設定を集中管理するサーバーです。
 * 
 * 機能:
 * - 全サービスの設定を一元管理
 * - 環境別設定の動的更新
 * - 設定の暗号化・復号化
 * - 設定変更の通知
 * 
 * 技術スタック:
 * - Spring Boot 3.2.0
 * - Spring Cloud Config Server
 * - Spring Cloud Netflix Eureka（サービスディスカバリー）
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerApplication {

    /**
     * アプリケーション起動メソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
