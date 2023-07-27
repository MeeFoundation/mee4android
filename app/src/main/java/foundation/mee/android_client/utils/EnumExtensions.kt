package foundation.mee.android_client.utils

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

inline fun <reified T : Enum<T>> size(): Int = enumValues<T>().size

inline fun <reified T : Enum<T>> head(): T = enumValues<T>().first()

inline fun <reified T : Enum<T>> tail(): T = enumValues<T>().last()

inline fun <reified T: Enum<T>> fromInt(value: Int): T = enumValues<T>().first { it.ordinal == value }
