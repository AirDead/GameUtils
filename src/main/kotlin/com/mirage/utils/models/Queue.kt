package com.mirage.utils.models

open class Queue(
    private val maxPlayers: Int,
    private val processLogic: (List<Player>) -> Unit
) : IQueue {
    private val players: MutableSet<Player> = linkedSetOf()

    override fun addPlayer(player: Player): Boolean {
        return if (players.size < maxPlayers && players.add(player)) true else false
    }

    override fun removePlayer(player: Player): Boolean {
        return players.remove(player)
    }

    override fun addParty(party: Party): Boolean {
        if (party.members.any { it in players }) return false
        return if (remainingSlots() >= party.members.size) {
            players.addAll(party.members)
            true
        } else false
    }

    override fun removeParty(party: Party): Boolean {
        return players.removeAll(party.members)
    }

    override fun isFull(): Boolean {
        return players.size >= maxPlayers
    }

    override fun currentCount(): Int {
        return players.size
    }

    override fun remainingSlots(): Int {
        return maxPlayers - players.size
    }

    override fun processQueue() {
        processLogic(players.toList())
        players.clear()
    }
}
