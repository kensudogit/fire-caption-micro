package com.firecaptain.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * エラーレスポンスDTOクラス
 * 
 * マイクロサービス全体で統一されたエラーレスポンス形式を提供します。
 * クライアントに分かりやすいエラー情報を返すために使用されます。
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /** エラー発生時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /** HTTPステータスコード */
    private int status;

    /** エラータイプ */
    private String error;

    /** エラーメッセージ */
    private String message;

    /** 詳細エラー情報（バリデーションエラーなど） */
    private Map<String, String> details;

    /** リクエストパス */
    private String path;
}
