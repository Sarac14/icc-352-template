SEND.addEventListener("click", () => {

    if (popChat.classList.contains("admin")) {

        window.location.href = "adminChats.html"

    } else {
        const chatId = Date.now();
        const nombre = prompt("Escribe un nombre de usuario temporal para poder acceder al chat.")
        window.location.href = `chatArticle.html?id=${chatId}&usuario=${nombre}`;
    }
})