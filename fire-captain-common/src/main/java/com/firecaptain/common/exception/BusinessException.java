package com.firecaptain.common.exception;

/**
 * ビジネス例外
 * 
 * ビジネスロジックで発生する例外を表します。
 * データの整合性違反、ビジネスルール違反、不正な操作などが
 * 原因でスローされます。HTTP 400エラーに対応します。
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
public class BusinessException extends RuntimeException {

    /**
     * デフォルトコンストラクタ
     */
    public BusinessException() {
        super("ビジネスルール違反が発生しました");
    }

    /**
     * メッセージ付きコンストラクタ
     * 
     * @param message エラーメッセージ
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * メッセージと原因付きコンストラクタ
     * 
     * @param message エラーメッセージ
     * @param cause   原因となる例外
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 操作タイプとリソース付きコンストラクタ
     * 
     * @param operation    操作タイプ（例：create, update, delete）
     * @param resourceType リソースタイプ（例：EmergencyReport）
     * @param reason       失敗理由
     */
    public BusinessException(String operation, String resourceType, String reason) {
        super(String.format("Cannot %s %s: %s", operation, resourceType, reason));
    }

    /**
     * ステータス変更例外コンストラクタ
     * 
     * @param currentStatus 現在のステータス
     * @param targetStatus  目標ステータス
     * @param reason        変更できない理由
     */
    public BusinessException(String currentStatus, String targetStatus, String reason, boolean isStatusChange) {
        super(String.format("Cannot change status from %s to %s: %s",
                currentStatus, targetStatus, reason));
    }
}
