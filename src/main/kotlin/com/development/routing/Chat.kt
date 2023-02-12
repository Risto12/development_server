package com.development.routing

import com.development.components.Connection
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

const val SOCKET_ROOT_PATH = "/socket"
const val CHAT_PATH = "$SOCKET_ROOT_PATH/chat"
const val ECHO_PATH = "$SOCKET_ROOT_PATH/echo"

/***
 * Echo socket for testing
 */
fun Route.echoSocket() {
    webSocket(ECHO_PATH) {
        send("Connection established")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val receivedText = frame.readText()
            if (receivedText.equals("quit", ignoreCase = true)) {
                close(CloseReason(CloseReason.Codes.NORMAL, "Connection ended"))
            } else {
                send(Frame.Text("Echo, $receivedText!"))
            }
        }
    }
}

fun Route.chatSocket() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket(CHAT_PATH) {
        val thisConnection = Connection(this)
        connections += thisConnection
        try {
            send("You are connected! There are ${connections.count()} users here.")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val textWithUsername = "[${thisConnection.name}]: $receivedText"

                connections.forEach {
                    it.session.send(textWithUsername)
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            connections -= thisConnection
        }
    }
}