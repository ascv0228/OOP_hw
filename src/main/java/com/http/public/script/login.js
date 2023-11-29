function handleSubmit() {
    const array1 = ["userToken"]
    if (!array1.every(checkText)) {
        alert("Please check input");
        return;
    }
    if (!isLogin()) {
        alert("Please Login");
        return;
    }
    var url = productUrl('/login/api?', inputValue)
    $.ajax({
        type: "GET",
        url: '/login/api?' + inputValue,
        success: function (response) {
            window.location.href = '/'
        },
        error: function (error) {
            console.error("Error:", error);
            $("#responseTextBox").val("Error:", error);
        }
    });
}
