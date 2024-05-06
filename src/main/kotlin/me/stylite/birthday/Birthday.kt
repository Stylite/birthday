package me.stylite.birthday

import java.io.FileInputStream
import java.util.Properties
import java.util.concurrent.*
import me.stylite.birthday.webhooks.Uta
object Birthday {

    @ExperimentalStdlibApi
    @JvmStatic
    fun main(args: Array<String>) {
        val prop = Properties()
        prop.load(FileInputStream("config.properties"))
        val webhookURL = prop.getProperty("webhook")
        val webhook = Uta(webhookURL)
        val text = prop.getProperty("text")
        val executor = Executors.newScheduledThreadPool(1)
        executor.scheduleAtFixedRate({ webhook.send(text) }, 0, 86400, TimeUnit.SECONDS)
    }
}