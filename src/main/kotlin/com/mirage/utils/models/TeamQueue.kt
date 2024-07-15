package com.mirage.utils.models

class TeamQueue(
    private val maxTeams: Int,
    private val maxPlayersPerTeam: Int,
    private val processLogic: (List<List<Player>>) -> Unit
) : IQueue {
    private val teams: MutableList<MutableList<Player>> = mutableListOf()

    override fun addPlayer(player: Player): Boolean {
        for (team in teams) {
            if (team.size < maxPlayersPerTeam && team.none { it.name == player.name }) {
                team.add(player)
                return true
            }
        }
        if (teams.size < maxTeams) {
            teams.add(mutableListOf(player))
            return true
        }
        return false
    }

    override fun removePlayer(player: Player): Boolean {
        return teams.any { it.remove(player) }
    }

    override fun addParty(party: Party): Boolean {
        val remainingMembers = party.members.filter { member ->
            teams.flatten().none { it.name == member.name }
        }.toMutableList()

        for (team in teams) {
            while (team.size < maxPlayersPerTeam && remainingMembers.isNotEmpty()) {
                team.add(remainingMembers.removeAt(0))
            }
        }

        while (remainingMembers.isNotEmpty() && teams.size < maxTeams) {
            val newTeam = mutableListOf<Player>()
            while (newTeam.size < maxPlayersPerTeam && remainingMembers.isNotEmpty()) {
                newTeam.add(remainingMembers.removeAt(0))
            }
            teams.add(newTeam)
        }

        return remainingMembers.isEmpty()
    }

    override fun removeParty(party: Party): Boolean {
        return party.members.all { removePlayer(it) }
    }

    override fun isFull(): Boolean {
        return teams.size >= maxTeams && teams.all { it.size == maxPlayersPerTeam }
    }

    override fun currentCount(): Int {
        return teams.sumOf { it.size }
    }

    override fun remainingSlots(): Int {
        return (maxTeams * maxPlayersPerTeam) - currentCount()
    }

    override fun processQueue() {
        processLogic(teams.toList())
        teams.clear()
    }
}
