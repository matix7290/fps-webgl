package sparkserver

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import spark.Spark.*
import spark.*
var level: Level? = null

fun main() {
    port(5000)
    staticFileLocation("/public")

    get("/") { _, res -> home(res) }
    get("/editor") { _, res -> editor(res) }
    get("/game") { _, res -> game(res) }
    post("/save") { req, _ -> save(req) }
    post("/read") { _, res -> read(res) }

    println("SparkServer startuje na porcie: 5000")

    enableCORS("*", "*", "*")
}

fun home(response: Response) {
    response.type("text/html")
    response.redirect("index.html")
}

fun editor(response: Response) {
    response.type("text/html")
    response.redirect("editor.html")
}

fun game(response: Response) {
    response.type("text/html")
    response.redirect("game.html")
}

fun save(request: Request) {
    val type = object : TypeToken<MutableList<LevelItem>>() {}.type
    val list: MutableList<LevelItem> = Gson().fromJson(request.body(), type)
    level = Level(10, list)
    println(level)
}

fun read(response: Response): String {
    response.type("application/json")
    return Gson().toJson(level?.getLevel())
}

fun enableCORS(origin: String, methods: String, headers: String) {
    options("/*") { request, response ->
        val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }
        val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }
        "OK"
    }
    before({ _, response ->
        response.header("Access-Control-Allow-Origin", origin)
        response.header("Access-Control-Request-Method", methods)
        response.header("Access-Control-Allow-Headers", headers)
        // Note: this may or may not be necessary in your particular application
        response.type("application/json")
    })
}
