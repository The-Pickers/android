package gsc.ThePickers.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DateUtils {

    // 1. 날짜를 "March 15, 2025" 형식으로 포맷 (String 또는 LocalDateTime 입력 가능)
    fun formatLocalDate(input: Any): String {
        val dateTime: LocalDateTime = when (input) {
            is String -> {
                try {
                    // Z 포함일 경우 Instant → LocalDateTime 변환 (시스템 시간대 기준)
                    val instant = Instant.parse(input)
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                } catch (e: DateTimeParseException) {
                    // fallback: Z 없이 일반 ISO_LOCAL_DATE_TIME 시도
                    LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                }
            }
            is LocalDateTime -> input
            else -> throw IllegalArgumentException("지원하지 않는 타입입니다.")
        }
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
        return dateTime.format(outputFormatter)
    }

    // 2. "hh:mm:ss" → "2 hours 30 mins" 변환
    fun formatTime(timeStr: String): String {
        val parts = timeStr.split(":")
        if (parts.size != 3) return "Invalid time format"

        val hours = parts[0].toIntOrNull() ?: return "Invalid hour"
        val minutes = parts[1].toIntOrNull() ?: return "Invalid minutes"

        val hourPart = if (hours > 0) "$hours hour${if (hours > 1) "s" else ""}" else ""
        val minutePart = if (minutes > 0) "$minutes min${if (minutes > 1) "s" else ""}" else ""

        return listOf(hourPart, minutePart).filter { it.isNotEmpty() }.joinToString(" ")
            .ifEmpty { "0 min" }
    }

    // 3. 시작/끝 시각 차이를 "hh:mm:ss" 형식으로 반환
    fun formatDuration(start: LocalDateTime, end: LocalDateTime): String {
        val duration = Duration.between(start, end)

        val hours = duration.toHours()
        val minutes = (duration.toMinutes() % 60)
        val seconds = (duration.seconds % 60)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
