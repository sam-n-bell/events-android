package com.example.loginlogout

//[{"created_by":1,"event_day":"08/15/2019","event_id":31,"name":"Volleyball at zilker","start_time":"13:00:00","venue_name":"Zilker Park"}
class My_Event_Model {

    var venue_name: String? = null
    var name: String? = null
    var event_day: String? = null
    var event_id: Int? = null
    var created_by: Int? = null
    var start_time: String? = null


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