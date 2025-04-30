package gsc.ZupStar.util

object LocationUtil {
    private val adminAreaMap = mapOf(
        "서울특별시" to "Seoul",
        "부산광역시" to "Busan",
        "대구광역시" to "Daegu",
        "인천광역시" to "Incheon",
        "광주광역시" to "Gwangju",
        "대전광역시" to "Daejeon",
        "울산광역시" to "Ulsan",
        "세종특별자치시" to "Sejong",
        "경기도" to "Gyeonggi-do",
        "강원도" to "Gangwon-do",
        "충청북도" to "Chungcheongbuk-do",
        "충청남도" to "Chungcheongnam-do",
        "전라북도" to "Jeollabuk-do",
        "전라남도" to "Jeollanam-do",
        "경상북도" to "Gyeongsangbuk-do",
        "경상남도" to "Gyeongsangnam-do",
        "제주특별자치도" to "Jeju"
    )
    // 행정구역을 도 기준 idx로 매핑
    private val adminAreaToIdx = mapOf(
        "경기도" to 1,
        "서울특별시" to 1,
        "인천광역시" to 1,

        "강원도" to 2,

        "충청남도" to 3,
        "대전광역시" to 3,
        "세종특별자치시" to 3,

        "충청북도" to 4,

        "경상남도" to 5,
        "부산광역시" to 5,
        "울산광역시" to 5,

        "경상북도" to 6,
        "대구광역시" to 6,

        "전라남도" to 7,
        "광주광역시" to 7,

        "전라북도" to 8,

        "제주특별자치도" to 9
    )
    // idx → 영어 지역명
    private val idxToEnglish = mapOf(
        1 to "Gyeonggi-do",
        2 to "Gangwon-do",
        3 to "Chungcheongnam-do",
        4 to "Chungcheongbuk-do",
        5 to "Gyeongsangnam-do",
        6 to "Gyeongsangbuk-do",
        7 to "Jeollanam-do",
        8 to "Jeollabuk-do",
        9 to "Jeju"
    )

    // 한글 → IDX 변환
    fun toIndex(korean: String?): Int? {
        return adminAreaToIdx[korean]
    }

    fun toEnglish(adminArea: String?): String {
        return adminAreaMap[adminArea] ?: "Unknown"
    }

    fun toEnglishByIndex(idx: Int?): String {
        return idxToEnglish[idx] ?: "Unknown"
    }
}