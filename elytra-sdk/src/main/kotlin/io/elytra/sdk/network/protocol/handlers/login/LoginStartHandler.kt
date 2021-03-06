package io.elytra.sdk.network.protocol.handlers.login

import io.elytra.api.io.i18n.MessageBuilder
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionState
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.login.EncryptionRequestMessage
import io.elytra.sdk.network.protocol.message.login.LoginStartMessage
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.server.ElytraServer
import org.apache.commons.lang3.Validate

class LoginStartHandler : ElytraMessageHandler<LoginStartMessage>() {
    override fun handle(session: NetworkSession, message: LoginStartMessage) {
        Validate.validState(session.sessionState == SessionState.HELLO, "Unexpected hello packet")

        session.gameProfile = message.gameProfile

        if (!session.isActive) {
            session.onDisconnect()
            return
        }

        MessageBuilder("console.player.connecting").with(
            "player" to message.gameProfile.name,
            "ip" to session.address.address.hostAddress
        ).getOrBuild().also(Elytra.logger::info)

        if (ElytraServer.isPlayerOnline(message.gameProfile.name)) {
            session.disconnect("Already online!")
            return
        }

        // TODO Then see why the player has placed a check to verify the session and disconnect from the server after receiving the encryption request
        if (Elytra.server.debug)
            session.sessionState = SessionState.READY_TO_ACCEPT
        else {
            session.sessionState = SessionState.KEY
            session.send(EncryptionRequestMessage("", Elytra.server.keypair.public, session.verifyToken))
        }
    }
}
