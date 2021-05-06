/**
 * websockets script
 */

window.onload = function () {

    /**
     * adres i port na którym działa serwer websocket kotlina
     */

    const ws = new WebSocket("ws://192.168.1.141:4000/mouse")

    /**
     * wysłanie danych na serwer przy podłączeniu klienta do serwera
     */

    ws.onopen = () => {
        ws.send("websocket klient podlaczyl sie do serwera")
    }

    /**
     * odebranie danych z serwera i reakcja na nie
     * czyli utworzenie nowego diva z komunikatem
     */

    ws.onmessage = (e) => {
        console.log(e, e.data)
        let div = document.createElement("div")
        div.innerHTML = "message" + e.data
        document.getElementById("count").prepend(div)
    }

    /**
     * obsługa błędów
     */

    ws.onerror = (e) => {
        console.log(e.message)
    }

    /**
     * zamknięcie połączenia
     */

    ws.onclose = (e) => {
        console.log(e.code, e.reason)
    }

    let isMoving

    window.onmousedown = function () {
        isMoving = true
    }

    window.onmouseup = function () {
        isMoving = false
    }

    function sendMessage(message) {
        if (message !== "") {
            ws.send(message)
        }
    }

    window.onmousemove = function (e) {
        if (isMoving) {
            sendMessage(`x:${e.clientX} - y:${e.clientY}`)
        }
    }

    /**
     * ZADANIE:
     * celem zadania jest poruszanie myszą po ekranie, po jej przytrzymaniu
     * i wysyłanie do serwera danych o pozycji
     * oraz zaprzestanie wysyłania przy zwolnieniu myszy.
     * serwer odsyła dane do wszystkich podłączonych klientów
     * użyj funkcji ws.send("komunikat")
     * ZADANIE 2:
     * przetestuj komunikację na dwu komputerach w sieci lokalnej
     * podając w przeglądarce i w skrypcie js adres komputera serwera: "ws://192.168.xx.xx:4000"
     */


}
