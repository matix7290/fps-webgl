package sparkserver

interface LevelService {
    fun getLevel(): MutableList<LevelItem>
}

class Level(var size: Int, var list: MutableList<LevelItem>) : LevelService {
    override fun getLevel(): MutableList<LevelItem> {
        return list
    }

}