package com.example.loginlogout

class TimeSlot_Model {

    var value: String? = null
    var label: String? = null

    fun getValues(): String {
        return value.toString()
    }

    fun setValues(name: String) {
        this.value = name
    }

    fun getLabels(): String {
        return label.toString()
    }

    fun setLabels(name: String) {
        this.label = name
    }

    override fun toString(): String {
        return this.label.toString()
    }

}