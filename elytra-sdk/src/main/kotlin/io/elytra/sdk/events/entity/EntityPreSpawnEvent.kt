package io.elytra.sdk.events.entity

import io.elytra.api.events.ElytraEvent
import io.elytra.api.world.Position
import io.elytra.api.world.World

// TODO View this later
class EntityPreSpawnEvent(
    var networkId: Int,
    var position: Position,
    var world: World
) : ElytraEvent
