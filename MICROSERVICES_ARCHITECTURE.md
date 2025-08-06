# 消防司令システム マイクロサービスアーキテクチャ

## 概要

消防司令システムをマイクロサービスアーキテクチャに変換し、各処理フローを独立したサービスとして分離しました。

## アーキテクチャ構成

### 1. インフラストラクチャサービス

#### Configuration Server (fire-captain-config)
- **ポート**: 8888
- **役割**: 集中設定管理
- **機能**: 
  - 全サービスの設定を一元管理
  - 環境別設定の動的更新
  - 設定の暗号化・復号化

#### Service Discovery (fire-captain-discovery)
- **ポート**: 8761
- **役割**: サービスディスカバリー
- **機能**:
  - Eureka Server
  - サービスの自動登録・発見
  - ヘルスチェック
  - ダッシュボード

#### API Gateway (fire-captain-gateway)
- **ポート**: 8080
- **役割**: 統一エントリーポイント
- **機能**:
  - ルーティング
  - 認証・認可
  - レート制限
  - サーキットブレーカー
  - CORS設定

### 2. ビジネスサービス

#### Emergency Report Service (fire-captain-emergency-report)
- **ポート**: 8081
- **役割**: 通報受付処理
- **機能**:
  - 通報の受付・記録
  - 通報番号の自動生成
  - リアルタイムステータス追跡
  - メッセージング（RabbitMQ）

#### Dispatch Service (fire-captain-dispatch)
- **ポート**: 8082
- **役割**: 指令・部隊出動処理
- **機能**:
  - 通報からの指令生成
  - 最適部隊選定
  - 出動指令の発令
  - 到着予想時間計算

#### Scene Support Service (fire-captain-scene-support)
- **ポート**: 8083
- **役割**: 現場支援処理
- **機能**:
  - 自動支援要求判定
  - 支援タイプ決定
  - 支援の承認・出動・完了管理
  - コスト追跡

#### Completion Report Service (fire-captain-completion-report)
- **ポート**: 8084
- **役割**: 終了報告処理
- **機能**:
  - 事案完了の記録
  - 詳細活動報告
  - 被害・救助状況記録
  - 教訓・改善点抽出

#### Data Analysis Service (fire-captain-data-analysis)
- **ポート**: 8085
- **役割**: データ蓄積・分析処理
- **機能**:
  - 定期自動分析
  - パフォーマンス指標算出
  - トレンド分析・予測モデリング
  - レポート生成

#### Monitoring Service (fire-captain-monitoring)
- **ポート**: 8086
- **役割**: 監視・可視化
- **機能**:
  - システム監視
  - メトリクス収集
  - アラート管理
  - ダッシュボード

### 3. 共通ライブラリ

#### Common Library (fire-captain-common)
- **役割**: 共通コンポーネント
- **機能**:
  - DTO定義
  - 例外ハンドリング
  - 共通ユーティリティ
  - 設定クラス

## 技術スタック

### フレームワーク
- **Spring Boot 3.2.0**: マイクロサービス基盤
- **Spring Cloud 2023.0.0**: マイクロサービス機能
- **Spring Cloud Gateway**: API Gateway
- **Spring Cloud Netflix Eureka**: サービスディスカバリー
- **Spring Cloud Config**: 設定管理

### データベース・キャッシュ
- **PostgreSQL 15**: メインデータベース
- **Redis 7**: キャッシュ・セッション管理
- **RabbitMQ 3**: メッセージング

### 監視・可視化
- **Prometheus**: メトリクス収集
- **Grafana**: ダッシュボード
- **Micrometer**: メトリクス

### コンテナ・オーケストレーション
- **Docker**: コンテナ化
- **Docker Compose**: ローカル開発環境

## 通信パターン

### 1. 同期通信
- **REST API**: サービス間の直接通信
- **OpenFeign**: 宣言的HTTPクライアント
- **Circuit Breaker**: フォールトトレランス

### 2. 非同期通信
- **RabbitMQ**: イベント駆動通信
- **Message Queues**: 非同期処理
- **Event Sourcing**: イベントソーシング

### 3. サービス間通信
```
Emergency Report → Dispatch → Scene Support → Completion Report → Data Analysis
      ↓              ↓            ↓              ↓                ↓
   RabbitMQ      REST API     REST API      REST API        REST API
```

## データベース設計

### データベース分離
各マイクロサービスが独自のデータベースを持つ：

- **fire_captain_emergency_report**: 通報データ
- **fire_captain_dispatch**: 指令データ
- **fire_captain_scene_support**: 支援データ
- **fire_captain_completion_report**: 完了報告データ
- **fire_captain_data_analysis**: 分析データ

### データ整合性
- **Saga Pattern**: 分散トランザクション管理
- **Event Sourcing**: イベントベースのデータ整合性
- **CQRS**: コマンド・クエリ責任分離

## セキュリティ

### 認証・認可
- **JWT**: トークンベース認証
- **OAuth2**: 認可フレームワーク
- **Spring Security**: セキュリティ機能

### 通信セキュリティ
- **HTTPS**: 暗号化通信
- **API Key**: API認証
- **Rate Limiting**: レート制限

## デプロイメント

### 開発環境
```bash
# 全サービス起動
docker-compose up -d

# 個別サービス起動
docker-compose up -d emergency-report-service
```

### 本番環境
- **Kubernetes**: コンテナオーケストレーション
- **Helm**: パッケージ管理
- **Istio**: サービスメッシュ

## 監視・運用

### ヘルスチェック
- **Spring Boot Actuator**: ヘルスエンドポイント
- **Eureka**: サービス状態監視
- **Prometheus**: メトリクス収集

### ログ管理
- **ELK Stack**: ログ収集・分析
- **Fluentd**: ログ転送
- **Kibana**: ログ可視化

### アラート
- **Grafana**: アラート設定
- **PagerDuty**: インシデント管理
- **Slack**: 通知

## パフォーマンス最適化

### キャッシュ戦略
- **Redis**: 分散キャッシュ
- **EhCache**: ローカルキャッシュ
- **CDN**: 静的コンテンツ配信

### スケーリング
- **水平スケーリング**: サービスインスタンス増加
- **垂直スケーリング**: リソース増強
- **自動スケーリング**: 負荷に応じた自動調整

## 開発・テスト

### 開発フロー
1. **Feature Branch**: 機能ブランチ開発
2. **Unit Testing**: 単体テスト
3. **Integration Testing**: 統合テスト
4. **Contract Testing**: 契約テスト
5. **E2E Testing**: エンドツーエンドテスト

### テスト戦略
- **JUnit 5**: 単体テスト
- **TestContainers**: 統合テスト
- **WireMock**: モックサーバー
- **Cucumber**: BDDテスト

## 今後の拡張計画

### 短期（1-3ヶ月）
- フロントエンド（Angular）の統合
- リアルタイム通知機能
- モバイルアプリ対応

### 中期（3-6ヶ月）
- AI/ML機能の統合
- 予測分析機能
- 自動化機能の強化

### 長期（6ヶ月以上）
- エッジコンピューティング対応
- IoTデバイス統合
- ブロックチェーン技術の活用

このマイクロサービスアーキテクチャにより、消防司令システムは高可用性、スケーラビリティ、保守性を実現し、緊急時の迅速な対応を可能にします。
