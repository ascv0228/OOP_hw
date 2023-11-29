
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

function memberJsonToHtml(MemberContainer, jsonString) {
    var userData = JSON.parse(jsonString);
    var gridItem = document.createElement('div');
    gridItem.classList.add('grid-memberItem');
    gridItem.innerHTML = '<div class="user-info">User Information</div>'
        + '<div class="member-details">'
        + `<div>Name: ${userData.memberInfo.name}</div>`
        + `<div>Gender: ${userData.memberInfo.gender}</div>`
        + '</div>'
        + `<div>Permission: ${userData.permission}</div>`
        + `<div>User Token: ${userData.userToken}</div>`;

    var recordsContainer = document.createElement('div');
    recordsContainer.classList.add('hidden');

    // Loop through records and create record elements
    userData.records.forEach(function (record) {
        var recordElement = document.createElement('div');
        recordElement.innerHTML = '<div>BookToken: ' + record.bookToken + '</div><div>Operation: ' + record.operation + '<br>Time: ' + record.timeString + "</div>";
        recordElement.classList.add("record-details");
        recordsContainer.appendChild(recordElement);
        recordsContainer.appendChild(document.createElement('br'));
    });
    var recordsToggle = document.createElement('div');
    recordsToggle.classList.add("records-toggle");
    recordsToggle.textContent = "△ 記錄"
    gridItem.appendChild(recordsToggle);
    gridItem.appendChild(recordsContainer);
    MemberContainer.appendChild(gridItem);
    recordsToggle.addEventListener('click', function () {
        recordsContainer.classList.toggle('hidden');
        recordsToggle.textContent = recordsContainer.classList.contains('hidden') ? '▽ Records' : '△ Records';
    });
}

function handleSendRequest(url) {
    return new Promise(function (resolve, reject) {
        // Make an AJAX request (GET in this case) to the server
        $.ajax({
            type: "GET",
            url: url,
            success: function (response) {
                resolve(response); // Resolve the promise with the response
            },
            error: function (error) {
                reject(error); // Reject the promise with the error
            }
        });
    });
}