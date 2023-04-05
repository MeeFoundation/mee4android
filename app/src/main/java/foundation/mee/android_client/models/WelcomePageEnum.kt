package foundation.mee.android_client.models

inline fun <reified T : Enum<T>> T.nextOrLast(): T {
    val values = enumValues<T>()
    val nextOrdinal = if ((ordinal + 1) >= values.size) ordinal else (ordinal + 1)
    return values[nextOrdinal]
}


inline fun <reified T : Enum<T>> T.previousOrFirst(): T {
    val values = enumValues<T>()
    val previousOrdinal = if ((ordinal - 1) < 0) ordinal else (ordinal - 1)
    return values[previousOrdinal]
}

enum class WelcomePageEnum(val pageNum: Int) {
    FIRST_PAGE(0),
    SECOND_PAGE(1),
    ;

    companion object {
        fun size(): Int {
            return WelcomePageEnum.values().size
        }

        fun firstPage(): WelcomePageEnum {
            return FIRST_PAGE
        }

        fun lastPage(): WelcomePageEnum {
            return SECOND_PAGE
        }

        private fun fromInt(value: Int) = WelcomePageEnum.values().first { it.pageNum == value }

        fun previousOrFirst(page: Int) = WelcomePageEnum.fromInt(page).previousOrFirst().pageNum

        fun nextOrLast(page: Int) = WelcomePageEnum.fromInt(page).nextOrLast().pageNum
    }

}
