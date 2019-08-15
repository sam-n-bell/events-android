package com.example.loginlogout

//[{"created_by":1,"current_num_players":1,"event_day":"08/15/2019","event_id":31,"max_players":7,"name":"Volleyball at zilker","start_time":"13:00:00","venue_name":"Zilker Park"}]
class Event_Model {

    var venue_name: String? = null
    var name: String? = null
    var event_day: String? = null
    var event_id: Int? = null
    var created_by: Int? = null
    var start_time: String? = null
    var current_num_players: Int? = null
    var max_players: Int? = null


    fun getCurrentNumPlayers(): Int? {
        return current_num_players
    }

    fun setCurrentNumPlayers(num: Int?) {
        this.current_num_players = num
    }


    fun getMaxPlayers(): Int? {
        return max_players
    }

    fun setMaxPlayers(num: Int?) {
        this.max_players = num
    }





    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getVenueNames(): String {
        return venue_name.toString()
    }

    fun setVenueNames(name: String) {
        this.venue_name = name
    }

    fun getEventDays(): String {
        return event_day.toString()
    }

    fun setEventDays(day: String) {
        this.event_day = day
    }

    fun getEventIds(): Int? {
        return event_id
    }

    fun setEventIds(id: Int) {
        this.event_id = id
    }

    fun getCreatedBys(): Int? {
        return created_by
    }

    fun setCreatedBys(id: Int) {
        this.created_by = id
    }

    fun getStartTimes(): String {
        return start_time.toString()
    }

    fun setStartTimes(time: String) {
        this.start_time = time
    }


}