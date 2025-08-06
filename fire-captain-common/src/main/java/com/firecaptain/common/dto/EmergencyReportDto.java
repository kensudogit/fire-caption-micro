package com.firecaptain.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * 緊急通報DTOクラス
 * 
 * 消防司令システムにおける緊急通報のデータ転送オブジェクトです。
 * 通報受付から完了までの全ライフサイクルを管理します。
 * 
 * @author Fire Captain System
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyReportDto {

    /** 通報ID（主キー） */
    private Long id;

    /**
     * 通報番号
     * 形式: ER-YYYYMMDDHHMMSS-XXXX
     * 例: ER-20241201143000-A1B2
     */
    @NotBlank(message = "Report number is required")
    @Pattern(regexp = "^ER-\\d{14}-[A-Z0-9]{4}$", message = "Invalid report number format")
    private String reportNumber;

    /** 通報者名（必須） */
    @NotBlank(message = "Report number is required")
    private String callerName;

    /**
     * 通報者電話番号（必須）
     * 国際形式対応（+81-90-1234-5678など）
     */
    @NotBlank(message = "Caller phone is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String callerPhone;

    /** 緊急事態の種類（必須） */
    @NotNull(message = "Emergency type is required")
    private EmergencyType emergencyType;

    /** 現場住所（必須） */
    @NotBlank(message = "Location address is required")
    private String locationAddress;

    /** 現場緯度（GPS座標） */
    private Double locationLatitude;
    /** 現場経度（GPS座標） */
    private Double locationLongitude;

    /** 通報内容の詳細説明 */
    private String description;

    /** 優先度レベル（必須） */
    @NotNull(message = "Priority level is required")
    private PriorityLevel priorityLevel;

    /** 通報ステータス（必須） */
    @NotNull(message = "Status is required")
    private ReportStatus status;

    /** 通報受付時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime receivedAt;

    /** 出動指令発令時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dispatchedAt;

    /** 現場到着時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivedAt;

    /** 事案完了時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completedAt;

    /** 予想所要時間（分） */
    private Integer estimatedDurationMinutes;
    /** 実際の所要時間（分） */
    private Integer actualDurationMinutes;

    /** レコード作成時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /** レコード更新時刻 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * 緊急事態の種類
     */
    public enum EmergencyType {
        /** 火災 */
        FIRE,
        /** 救急 */
        MEDICAL,
        /** 交通事故 */
        TRAFFIC_ACCIDENT,
        /** 救助 */
        RESCUE,
        /** 危険物 */
        HAZMAT,
        /** その他 */
        OTHER
    }

    /**
     * 優先度レベル
     */
    public enum PriorityLevel {
        /** 低 */
        LOW,
        /** 中 */
        MEDIUM,
        /** 高 */
        HIGH,
        /** 緊急 */
        CRITICAL
    }

    /**
     * 通報ステータス
     */
    public enum ReportStatus {
        /** 受付済み */
        RECEIVED,
        /** 出動指令済み */
        DISPATCHED,
        /** 出動中 */
        EN_ROUTE,
        /** 現場到着 */
        ON_SCENE,
        /** 完了 */
        COMPLETED,
        /** キャンセル */
        CANCELLED
    }
}
