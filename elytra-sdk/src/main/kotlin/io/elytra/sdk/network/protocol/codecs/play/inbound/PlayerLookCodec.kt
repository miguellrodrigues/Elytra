package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerLookMessage
import io.netty.buffer.ByteBuf

class PlayerLookCodec : Codec<PlayerLookMessage> {
    override fun encode(buf: ByteBuf, message: PlayerLookMessage): ByteBuf {
        buf.writeFloat(message.yaw)
        buf.writeFloat(message.pitch)
        buf.writeBoolean(message.onGround)

        return buf
    }

    override fun decode(buffer: ByteBuf): PlayerLookMessage {
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()
        val onGround = buffer.readBoolean()
        return PlayerLookMessage(yaw, pitch, onGround)
    }
}
