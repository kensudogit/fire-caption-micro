# マルチステージビルド
FROM gradle:8.5-jdk17 AS build

# 作業ディレクトリを設定
WORKDIR /app

# Gradleファイルをコピー
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# 依存関係をダウンロード
RUN gradle dependencies --no-daemon

# ソースコードをコピー
COPY src ./src

# アプリケーションをビルド
RUN gradle build --no-daemon

# 実行ステージ
FROM eclipse-temurin:17-jre

# 作業ディレクトリを設定
WORKDIR /app

# アプリケーションJARをコピー
COPY --from=build /app/build/libs/*.jar app.jar

# ヘルスチェック用のcurlをインストール
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# ポートを公開
EXPOSE 8080

# アプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]
