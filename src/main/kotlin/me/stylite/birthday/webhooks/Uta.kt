package me.stylite.birthday.webhooks

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
//import org.apache.logging.log4j.LogManager
//import org.apache.logging.log4j.Logger
import org.json.JSONObject

class Uta(webhookURL: String) {
    // private val logger: Logger = LogManager.getLogger("Uta")
    private val client: HttpClient = HttpClient.newHttpClient()
    private val ids = splitWebhook(webhookURL)
    private fun splitWebhook(webhookURL: String): Pair<String, String> {
        val split = webhookURL.split("/")
        val id = split[split.size - 2]
        val token = split[split.size - 1]
        return Pair(id, token)
    }

    fun send(content: String) {
        val json = JSONObject()
        json.put("content", content)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://discord.com/api/v10/webhooks/${ids.first}/${ids.second}"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(json.toString()))
            .build()
        val response = client.send(request, BodyHandlers.ofString())
        /* when (response.statusCode())  {
             200 -> logger.info("Executed webhook!")
             204 -> logger.info("Sent webhook!")
             401 -> logger.error("Unauthorized")
             403 -> logger.error("Forbidden")
             404 -> logger.error("Webhook not found")
             429 -> logger.error("Rate limited")
             else -> logger.error("Failed to send webhook with status code ${response.statusCode()}")
         } */
    }
}