function handleSubmit() {
    const array1 = ["bookToken", "operation"];
    if (!array1.every(checkText)) {
        alert("Please check input");
        return;
    }
    const cookieMap = getCookie();
    if (!"")

        var inputValue = outputInputValue(array1);

    $.ajax({
        type: "GET",
        url: productUrl('/operation/api?', inputValue),
        success: function (response) {
            let index = response.indexOf("Book Info");

            if (index !== -1) {
                // Extract string1 and string2 based on the index
                let string1 = response.substring(0, index).trim();  // "aaa bbb"
                let string2 = response.substring(index).trim();    // "ccc ddd"


                $("#responseMemberTextBox").val(string1);
                $("#responseBookTextBox").val(string2);
            } else {
                $("#responseMemberTextBox").val(response);
            }
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}

window.onload = function () {
    var success = fillTextBoxFromUrlParameter("bookToken");
    // if (success) {
    //     handleSubmit();
    // }
};

