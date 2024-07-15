package com.mirage.utils.manager

import com.mirage.utils.models.Party
import com.mirage.utils.models.Player
import com.mirage.utils.models.Queue

class QueueManager(private val createQueue: () -> Queue) {
    private val queues = mutableListOf(createQueue())

    fun addPlayerToQueue(player: Player): Boolean {
        val queue = queues.find { !it.isFull() } ?: createQueue().also { queues.add(it) }
        if (queue.addPlayer(player)) {
            if (queue.isFull()) processQueue(queue)
            return true
        }
        return false
    }

    fun removePlayerFromQueue(player: Player): Boolean {
        return queues.any { it.removePlayer(player) }
    }

    fun addPartyToQueue(party: Party): Boolean {
        val queue = queues.find { it.remainingSlots() >= party.members.size } ?: createQueue().also { queues.add(it) }
        if (queue.addParty(party)) {
            if (queue.isFull()) processQueue(queue)
            return true
        }
        return false
    }

    fun removePartyFromQueue(party: Party): Boolean {
        return queues.any { it.removeParty(party) }
    }

    private fun processQueue(queue: Queue) {
        queue.processQueue()
        queues.remove(queue)
        queues.add(createQueue())
    }
}
