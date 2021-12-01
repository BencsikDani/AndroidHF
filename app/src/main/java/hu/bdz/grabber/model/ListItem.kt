package hu.bdz.grabber.model

data class ListItem(var id: Int, var text: String) {

    constructor(text: String) : this(0, text) { }
}