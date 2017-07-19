package com.liguo.hjdemo

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

/**
 *
 * Created by Extends on 2017/7/10 10:51
 */
open class Client : WebSocketClient{
    constructor(serverURI: URI) : super(serverURI) {}

    constructor(serverUri: URI, draft: Draft) : super(serverUri, draft) {}

    constructor(serverUri: URI, draft: Draft, headers: Map<String, String>, connecttimeout: Int) : super(serverUri, draft, headers, connecttimeout) {}

    override fun onOpen(serverHandshake: ServerHandshake) {

    }

    override fun onMessage(s: String) {

    }

    override fun onClose(i: Int, s: String, b: Boolean) {

    }

    override fun onError(e: Exception) {

    }
}