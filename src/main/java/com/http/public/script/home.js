
function pageLoaded() {
    const cookieMap = getCookie();
    var class_login_hidden = document.getElementById("login_hidden");
    var class_logout_hidden = document.getElementById("logout_hidden");
    var class_headerLogout = document.getElementById("headerLogout");
    var class_regular_hidden = document.getElementById("regular_hidden");

    if ("userToken" in cookieMap && "permission" in cookieMap) {
        class_login_hidden.classList.add("hidden");
        class_logout_hidden.classList.remove("hidden");
        class_headerLogout.classList.remove("hidden");
        if (cookieMap["permission"] == "Administrative") {
            class_regular_hidden.classList.remove("hidden");
        } else {
            class_regular_hidden.classList.add("hidden");
        }
    } else {
        class_login_hidden.classList.remove("hidden");
        class_logout_hidden.classList.add("hidden");
        class_headerLogout.classList.add("hidden");
        class_regular_hidden.classList.add("hidden");
    }
}
function headerLogout() {
    $.ajax({
        type: "GET", // or "GET" depending on your needs
        url: '/logout/api',
        success: function (response) {
            location.reload(true);
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}


