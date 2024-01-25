package foundation.mee.android_client.utils

import com.intuit.fuzzymatcher.component.MatchService
import com.intuit.fuzzymatcher.domain.Document
import com.intuit.fuzzymatcher.domain.Element
import com.intuit.fuzzymatcher.domain.ElementType
import com.intuit.fuzzymatcher.domain.Match
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType

object FuzzySearchHelper {

    private const val threshold = 0.0
    private val type = ElementType.NAME

    fun getFoundConnections(query: String, connections: List<MeeConnection>): List<MeeConnection> {
        val docsList = buildDocList(connections.map { it.name })
        val result = search(query, docsList)
        return result?.let {
            val resultMap = searchResultToMap(it)
            val comparator = getComparator(query, resultMap)
            connections.filter { connection ->
                connection.name in resultMap
            }.sortedWith(comparator)
        } ?: listOf()
    }

    private fun buildDocList(strings: List<String>): List<Document> {
        return strings.map { string ->
            with(
                Document.Builder(string)
            ) {
                setThreshold(threshold)
                addElement(createElement(string))
                createDocument()
            }
        }
    }

    private fun createElement(value: String): Element<*>? {
        return with(Element.Builder<String>()) {
            setValue(value)
            setType(type)
            createElement()
        }
    }

    fun search(
        query: String,
        docsList: List<Document>
    ): MutableMap<String, MutableList<Match<Document>>>? {
        val matchService = MatchService()
        return matchService.applyMatchByDocId(
            buildDocList(listOf(query)), docsList
        )
    }

    private fun searchResultToMap(result: MutableMap<String, MutableList<Match<Document>>>): Map<String, Double> {
        return result.values.flatMap { value -> value.map { it } }
            .associate { it.matchedWith.key to it.score.result }
    }

    private fun getComparator(
        query: String,
        resultMap: Map<String, Double>
    ): Comparator<MeeConnection> {
        val missingElementScore = 0.0
        val exactMatchScore = 1.0
        return Comparator { c1: MeeConnection, c2: MeeConnection ->
            val name1 = c1.name
            val name2 = c2.name
            val score1 = resultMap[name1] ?: missingElementScore
            val score2 = resultMap[name2] ?: missingElementScore
            if (score1 == exactMatchScore && score2 == exactMatchScore) {
                val matches1 = query == name1
                val matches2 = query == name2
                if (matches1 == matches2) {
                    return@Comparator 0
                } else {
                    return@Comparator if (matches2) 1 else -1
                }
            }
            return@Comparator score2.compareTo(score1)
        }
    }
}