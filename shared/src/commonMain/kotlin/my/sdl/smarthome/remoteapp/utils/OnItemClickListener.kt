package my.sdl.smarthome.remoteapp.utils

interface OnItemClickListener<T> {
    fun getColor(color: Int): Int
    fun onItemClick(data: T, position: Int)
}
