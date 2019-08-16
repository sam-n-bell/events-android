package com.example.loginlogout

//[{"created_by":1,"current_num_players":1,"event_day":"08/15/2019","event_id":31,"max_players":7,"name":"Volleyball at zilker","start_time":"13:00:00","venue_name":"Zilker Park"}]
class Venue_Model {

    var name: String? = null
    var venue_id: Int? = null
    var address: String? = null
    var activities: String? = null


    fun getVenueIds(): Int? {
        return this.venue_id
    }

    fun setVenueIds(id: Int?) {
        this.venue_id = id
    }

    fun getVenueNames(): String {
        return name.toString()
    }

    fun setVenueNames(name: String) {
        this.name = name
    }


    override fun toString(): String {
        return this.name.toString()
    }

}