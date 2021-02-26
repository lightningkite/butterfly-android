package com.lightningkite.butterfly.net

import com.lightningkite.butterfly.Codable
import com.lightningkite.butterfly.fromJsonString
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HttpClientTest {
    data class Post(val userId: Long, val id: Long, val title: String, val body: String): Codable

    @Test fun testCall(){
        HttpClient.call("https://jsonplaceholder.typicode.com/posts/")
            .readJson<List<Post>>()
            .blockingGet()
            .let { println("postsIntended: ${it[0]}") }

    }

    @Test fun testWebSocket(){
        println("Connecting...")
        var recievedFrame: Boolean = false
        var mySocket: ConnectedWebSocket? = null
        val t = Thread {
            HttpClient.webSocket("wss://echo.websocket.org").subscribe { socket ->
                mySocket = socket
                socket.read.subscribeOn(Schedulers.io()).subscribe { frame ->
                    println("Frame: $frame")
                    recievedFrame = true
                }
                socket.onNext(WebSocketFrame(text = "Hello world!"))
            }
        }
        t.start()
        Thread.sleep(3000L)
        mySocket?.onComplete()
        t.join()
        assert(recievedFrame)
    }
}
