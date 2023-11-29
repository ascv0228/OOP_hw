
function getCookie() {
    var cookieMap = {};
    const cookieArray = document.cookie.split('; ');
    for (const cookie of cookieArray) {
        const [cookieName, cookieValue] = cookie.split('=');
        cookieMap[cookieName] = cookieValue;
    }
    return cookieMap;
}

function isLogin() {
    const cookieMap = getCookie();
    return "userToken" in cookieMap && "permission" in cookieMap
}

function checkText(id) {
    var inputValue = $("#" + id).val();
    return (inputValue.trim() !== "");
}

function productUrl(path, uri) {
    return path + encodeURIComponent(uri);
}

function outputInputValue(ids) {
    var values = ids.map(id => id + "=" + $("#" + id).val());
    return values.join("&");
}

function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Function to fill in a text box with a specific parameter
function fillTextBoxFromUrlParameter(name, textBoxId = null) {
    textBoxId = textBoxId ? textBoxId : name;

    var paramValue = getUrlParameter(name);

    var textBox = document.getElementById(name);
    if (textBox && paramValue) {
        textBox.value = paramValue;
        return true;
    }
    return false;
}