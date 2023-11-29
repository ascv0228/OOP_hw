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
                let words = response.split("Book Info");
                let memberString = words[0].trim();
                let bookString = words[1].trim();

                const MemberGrid = document.getElementById('MemberGrid');
                MemberGrid.innerHTML = '';

                memberJsonToHtml(MemberGrid, memberString);
                bookJsonToHtml(MemberGrid, bookString);
            } else {
                const MemberGrid = document.getElementById('MemberGrid');
                MemberGrid.innerHTML = '';

                memberJsonToHtml(MemberGrid, response);
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

