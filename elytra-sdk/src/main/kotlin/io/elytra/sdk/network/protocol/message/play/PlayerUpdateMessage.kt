package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.utils.Updatable
import io.elytra.api.world.Position

open class PlayerUpdateMessage(
    val onGround: Boolean
) : Message, Updatable<Position> {
    override fun update(record: Position) {}
}
