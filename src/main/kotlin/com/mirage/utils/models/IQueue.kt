package com.mirage.utils.models

interface IQueue {
    fun addPlayer(player: Player): Boolean
    fun removePlayer(player: Player): Boolean
    fun addParty(party: Party): Boolean
    fun removeParty(party: Party): Boolean
    fun isFull(): Boolean
    fun currentCount(): Int
    fun remainingSlots(): Int
    fun processQueue()
}
