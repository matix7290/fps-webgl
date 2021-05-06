package sparkserver

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import spark.Spark.*
import spark.*
var level: Level? = null

fun main() {
    port(5000)
    staticFileLocation("/public")

    post("/save") { req, res -> save(req, res) }
    post("/read") { req, res -> read(req, res) }

    println("SparkServer startuje na porcie: 5000")

    enableCORS("*", "*", "*")
}

fun save(request: Request, response: Response) {
    val type = object : TypeToken<MutableList<LevelItem>>() {}.type
    val list: MutableList<LevelItem> = Gson().fromJson(request.body(), type)
    level = Level(10, list)
    println(level)
}

fun read(request: Request, response: Response): String {
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
    before({ request, response ->
        response.header("Access-Control-Allow-Origin", origin)
        response.header("Access-Control-Request-Method", methods)
        response.header("Access-Control-Allow-Headers", headers)
        // Note: this may or may not be necessary in your particular application
        response.type("application/json")
    })
}
