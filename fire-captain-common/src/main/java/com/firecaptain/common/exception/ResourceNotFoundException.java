package com.firecaptain.common.exception;

/**
 * リソース未発見例外
 * 
 * 要求されたリソース（データ、ファイル、サービスなど）が見つからない場合に
 * スローされる例外です。HTTP 404エラーに対応します。
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * デフォルトコンストラクタ
     */
    public ResourceNotFoundException() {
        super("リソースが見つかりません");
    }

    /**
     * メッセージ付きコンストラクタ
     * 
     * @param message エラーメッセージ
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * メッセージと原因付きコンストラクタ
     * 
     * @param message エラーメッセージ
     * @param cause   原因となる例外
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * リソースタイプとID付きコンストラクタ
     * 
     * @param resourceType リソースタイプ（例：EmergencyReport）
     * @param id           リソースID
     */
    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s with id %d not found", resourceType, id));
    }

    /**
     * リソースタイプと識別子付きコンストラクタ
     * 
     * @param resourceType リソースタイプ（例：EmergencyReport）
     * @param identifier   識別子（例：reportNumber）
     * @param value        識別子の値
     */
    public ResourceNotFoundException(String resourceType, String identifier, String value) {
        super(String.format("%s with %s '%s' not found", resourceType, identifier, value));
    }
}
